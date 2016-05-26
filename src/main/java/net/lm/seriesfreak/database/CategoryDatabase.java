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

import net.lm.seriesfreak.database.implementation.ProgressMonitor;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.lm.seriesfreak.database.data.categories.EntryMetadata;
import net.lm.seriesfreak.database.data.categories.UserCategory;
import net.lm.seriesfreak.database.data.entries.Entry;
import net.lm.seriesfreak.database.data.entries.Type;
import net.lm.seriesfreak.ui.language.node.LTextItem;
import net.lm.seriesfreak.util.ChangeListener;
import net.lm.seriesfreak.util.ChangeListenerList;
import net.lm.seriesfreak.util.ChangeListenerTemplate;

/**
 * 
 * @author Luke Melaia
 */
public class CategoryDatabase implements IDatabase, ChangeListenerTemplate<UserCategory[], ChangeListener<UserCategory[]>> {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private static LTextItem loading = new LTextItem("loading_category", null).register();
    
    private static LTextItem saving = new LTextItem("saving_category", null).register();
    
    private List<UserCategory> sortItems = new ArrayList<>();

    private final ChangeListenerList<UserCategory[]> listeners = new ChangeListenerList<>();

    private int categoriesLoaded = 0;

    @Override
    public void deserialize(JsonObject map) {
        if (map == null) {
            this.sortItems.clear();
            this.notifyListeners();
            return;
        }

        this.sortItems.clear();

        for (Map.Entry<String, JsonElement> entry : map.entrySet()) {
            ProgressMonitor.setProgress(new ProgressMonitor.ProgressObject(map.entrySet().size(), categoriesLoaded, loading.getText() + entry.getKey()));

            UserCategory item = new UserCategory(entry.getKey(), true);

            JsonArray names = entry.getValue().getAsJsonObject().getAsJsonArray("nameValues");
            JsonArray types = entry.getValue().getAsJsonObject().getAsJsonArray("typeValues");

            for (int i = 0; i < names.size(); i++) {
                Entry dummyEntry = new Entry();

                dummyEntry.setName(names.get(i).getAsString());
                dummyEntry.setType(Type.valueOf(types.get(i).getAsString()));
                
                item.addEntry(dummyEntry);
            }

            this.sortItems.add(item);
            categoriesLoaded++;
        }

        categoriesLoaded = 0;
        notifyListeners();
    }

    private int categoriesSaved = 0;

    @Override
    public JsonObject serialize() {
        JsonObject map = new JsonObject();

        for (UserCategory item : sortItems) {
            ProgressMonitor.setProgress(new ProgressMonitor.ProgressObject(sortItems.size(), categoriesSaved, saving.getText() + item.getValue()));
            JsonObject itemObject = new JsonObject();

            JsonArray names = new JsonArray();
            JsonArray types = new JsonArray();

            for (EntryMetadata metadata : item.getEntryMetadatas()) {
                names.add(new JsonPrimitive(metadata.getName()));
                types.add(new JsonPrimitive(metadata.getType().name()));
            }

            itemObject.add("nameValues", names);
            itemObject.add("typeValues", types);

            map.add(item.getValue(), itemObject);
            categoriesSaved++;
        }

        categoriesSaved = 0;
        return map;
    }

    @Override
    public String getName() {
        return "Category";
    }

    public boolean addCategory(UserCategory category) {
        if (category == null) {
            return false;
        }

        if (!sortItems.contains(category)) {
            sortItems.add(category);
            notifyListeners();
            return true;
        }
        
        return false;
    }

    public boolean removeEntry(UserCategory category) {
        if (category == null) {
            return false;
        }

        if (sortItems.contains(category)) {
            sortItems.remove(category);
            notifyListeners();
            return true;
        }
        
        return false;
    }

    @Override
    public void clear() {
        sortItems.clear();
        notifyListeners();
    }

    public boolean contains(UserCategory category) {
        return this.sortItems.contains(category);
    }

    @Override
    public void addListener(ChangeListener<UserCategory[]> listener) {
        listeners.addListener(listener);
    }

    @Override
    public void removeListener(ChangeListener<UserCategory[]> listener) {
        listeners.removeListener(listener);
    }

    private void notifyListeners() {
        this.listeners.notifyListeners(this.sortItems.toArray(new UserCategory[sortItems.size()]));
    }
}
