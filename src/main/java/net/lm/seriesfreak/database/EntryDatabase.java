/*
 * Copyright (C) 2015 Luke Melaia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.lm.seriesfreak.database;

import net.lm.seriesfreak.database.implementation.LoadType;
import net.lm.seriesfreak.database.implementation.TypeLoader;
import net.lm.seriesfreak.database.implementation.ProgressMonitor;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.lm.seriesfreak.Application;
import net.lm.seriesfreak.ExitCode;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.database.implementation.DeserializeException;
import net.lm.seriesfreak.database.implementation.SerializeException;
import net.lm.seriesfreak.ui.language.node.LTextItem;
import net.lm.seriesfreak.util.ChangeListenerList;
import net.lm.seriesfreak.util.ChangeListenerTemplate;

/**
 *
 * @author Luke Melaia
 */
public class EntryDatabase implements IDatabase, ChangeListenerTemplate<EntryBase[], EntryListener> {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private static LTextItem loading = new LTextItem("loading_entry", null).register();
    
    private static LTextItem saving = new LTextItem("saving_entry", null).register();
    
    private final List<Class<? extends EntryBase>> entryTypes = new ArrayList<>();

    private final List<EntryBase> entries = new ArrayList<>(32);

    private final ChangeListenerList<EntryBase[]> listeners = new ChangeListenerList<>();

    private EntryBase selected;

    public EntryDatabase() {
        try {
            //@unstable: possibility of breaking if packages change
            TypeLoader loader = new TypeLoader("net.lm.seriesfreak.database.data.entries", LoadType.class);

            for (Class<? extends EntryBase> propertyClass : loader.getClasses(EntryBase.class)) {
                entryTypes.add(propertyClass);
            }
        } catch (IOException ex) {
            log.fatal("Failed to load entry types", ex);
            Application.exit(ExitCode.TYPE_LOADING_FAILURE);
        }
    }

    private int entriesLoaded = 0;

    @Override
    public void deserialize(JsonObject map) {
        if (map == null) {
            this.entries.clear();
            this.notifyListeners();
            return;
        }

        this.entries.clear();

        for (Class<? extends EntryBase> c : entryTypes) {
            try {
                EntryBase entry = c.newInstance();
                JsonObject entryMap = map.getAsJsonObject(entry.getTypeName());
                if (entryMap != null) {
                    for (Map.Entry<String, JsonElement> entryDataMap : entryMap.entrySet()) {
                        JsonObject entryData = entryDataMap.getValue().getAsJsonObject();

                        try {
                            entry = c.newInstance();
                            ProgressMonitor.setProgress(new ProgressMonitor.ProgressObject(entryMap.entrySet().size(),
                                    entriesLoaded, loading.getText() + entryDataMap.getKey()));

                            try {
                                //The old way to create an entry object from serialized data.
                                //entry.deserialize(entryData, entries.toArray(new EntryBase[this.entries.size()]));
                                entry.getClass().getMethod("deserialize", JsonObject.class, EntryBase[].class).invoke(entry, entryData, entries.toArray(new EntryBase[this.entries.size()]));
                            } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
                                log.error("Failed to get the deserialize() method from an entry type.", ex);
                                //Throw a runtime exception that will propagate up the stack and safely crash the application.
                                throw new DeserializeException("deserialize() cannot be found for entry class type: " + entry.getClass(), ex);
                            }

                            entry.setName(entryDataMap.getKey());
                            if (!entries.add(entry)) {
                                log.error("Failed to create entry: " + entryDataMap.getKey() + " - already exists");
                            }
                        } catch (InstantiationException | IllegalAccessException ex) {
                            log.error("Failed to create entry: " + entryDataMap.getKey(), ex);
                        }
                        entriesLoaded++;
                    }
                } else {
                    log.warn("Missing entry database: " + entry.getTypeName());
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                log.error("Failed to create entry class: " + c.getName(), ex);
            }
        }

        entriesLoaded = 0;
        notifyListeners(entries.toArray(new EntryBase[entries.size()]));
    }

    private int entriesSaved = 0;

    @Override
    public JsonObject serialize() {
        JsonObject map = new JsonObject();

        Map<String, JsonObject> entryMaps = new HashMap<>();

        for (EntryBase entryBase : entries) {
            ProgressMonitor.setProgress(new ProgressMonitor.ProgressObject(entries.size(), entriesSaved, saving.getText() + entryBase.getName()));
            String dataName = entryBase.getTypeName();
            JsonObject current;
            if (!entryMaps.containsKey(dataName)) {
                JsonObject cur = new JsonObject();
                current = cur;
                entryMaps.put(dataName, current);
            } else {
                current = entryMaps.get(dataName);
            }

            try {
                current.add(entryBase.getName(),
                        //The old way to get the serialized entry object.
                        //entryBase.serialize()
                        (JsonObject) entryBase.getClass().getMethod("serialize").invoke(entryBase)
                );
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                log.error("Failed to get the serialize() method from an entry type.", ex);
                //Throw a runtime exception that will propagate up the stack and safely crash the application.
                throw new SerializeException("serialize() cannot be found for entry class type: " + entryBase.getClass(), ex);
            }
            entriesSaved++;
        }

        for (String mapName : entryMaps.keySet()) {
            map.add(mapName, entryMaps.get(mapName));
        }

        entriesSaved = 0;
        return map;
    }

    @Override
    public String getName() {
        return "Entry";
    }

    public boolean addEntry(EntryBase entry) {
        if (entry == null) {
            return false;
        }

        if (!entries.contains(entry)) {
            entries.add(entry);
            notifyListeners();
            return true;
        }
        
        return false;
    }
    
    public boolean contains(EntryBase entry) {
        return entries.contains(entry);
    }

    public void forceUpdate() {
        this.notifyListeners();
    }

    public boolean removeEntry(EntryBase entry) {
        if (entry == null) {
            return false;
        }

        if (entries.contains(entry)) {
            entries.remove(entry);
            notifyListeners();
            return true;
        }
        
        return false;
    }

    private void notifyListeners() {
        notifyListeners(entries.toArray(new EntryBase[entries.size()]));
    }

    public EntryBase[] getEntries() {
        EntryBase[] ents = new EntryBase[entries.size()];
        return entries.toArray(ents);
    }

    @Override
    public void addListener(EntryListener listener) {
        listeners.addListener(listener);
    }

    @Override
    public void removeListener(EntryListener listener) {
        listeners.removeListener(listener);
    }

    private void notifyListeners(EntryBase[] object) {
        listeners.notifyListeners(object);
    }

    @Override
    public void clear() {
        entries.clear();
        this.selected = null;
        notifyListeners();
    }

    public void setSelected(EntryBase entry) {
        this.selected = entry;
    }

    public EntryBase getSelected() {
        return selected;
    }
}
