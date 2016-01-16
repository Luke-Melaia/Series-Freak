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
package net.lm.seriesfreak.database.implementation;

import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.lm.seriesfreak.database.IDatabase;
import static net.lm.seriesfreak.database.implementation.ProgressMonitor.setProgress;
import static net.lm.seriesfreak.database.implementation.ProgressMonitor.setBusy;
import net.lm.seriesfreak.ui.language.node.LTextField;
import net.lm.seriesfreak.ui.language.node.LTextItem;

/**
 *
 * @author Luke Melaia
 */
public class DatabaseHandler {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();
    
    private static LTextItem savingFile = new LTextItem("saving_file", null).register();
    
    private static LTextItem notBusy = new LTextItem("not_busy", null).register();
    
    private static LTextItem savingNewFile = new LTextItem("saving_file_n", null).register();
    
    private static LTextItem openingFile = new LTextItem("opening_file", null).register();
    
    private static LTextItem saving = new LTextItem("saving_map", null).register();
    
    private static LTextItem loading = new LTextItem("loading_map", null).register();
    
    private JsonObject data = new JsonObject();

    private DatabaseLoader loader;

    private Map<Class<? extends IDatabase>, IDatabase> maps = new HashMap<>(5);

    private File currectFile;

    public DatabaseHandler(int fileVersion) {
        this.loader = new DatabaseLoader(fileVersion, true);
    }

    public void save(String filename) throws InaccessibleFileException, IOException {
        log.info("Attempting to save to file: " + filename + "...");
        setProgress(new ProgressMonitor.ProgressObject(0, 0, savingFile.getText() + filename));
        setBusy(true);
        saveMaps();
        loader.setData(data);
        loader.save(new File(filename));
        log.info("Save successful");
        setProgress(new ProgressMonitor.ProgressObject(0, 0, notBusy.getText()));
        setBusy(false);
        this.currectFile = loader.getCurrentFile();
    }

    public void save() throws InaccessibleFileException, IOException {
        log.info("Attempting to save...");
        setProgress(new ProgressMonitor.ProgressObject(0, 0, savingNewFile.getText()));
        setBusy(true);
        saveMaps();
        loader.setData(data);
        loader.save();
        log.info("Save successful");
        setProgress(new ProgressMonitor.ProgressObject(0, 0, notBusy.getText()));
        setBusy(false);
    }

    public void open(String filename) throws CorruptFileException, InaccessibleFileException, IOException {
        log.info("Attempting to load from file: " + filename);
        loader.open(new File(filename));
        this.data = loader.getData();
        setProgress(new ProgressMonitor.ProgressObject(0, 0, openingFile.getText() + filename));
        setBusy(true);
        loadMaps();
        log.info("Load successful");
        setProgress(new ProgressMonitor.ProgressObject(0, 0, notBusy.getText()));
        setBusy(false);
        this.currectFile = loader.getCurrentFile();
    }
    
    public IDatabase getMap(Class<? extends IDatabase> map) throws IllegalArgumentException {
        if (!maps.containsKey(map)) {
            return getInitializedMap(map);
        } else {
            return maps.get(map);
        }
    }
    
    public void clear() {
        for (IDatabase map : maps.values()) {
            map.clear();
        }
    }
    
    private IDatabase getInitializedMap(Class<? extends IDatabase> map) throws IllegalArgumentException {
        try {
            IDatabase dm = map.newInstance();
            if (data != null) {
                initializeMap(dm);
            }
            maps.put(map, dm);
            return dm;
        } catch (InstantiationException | IllegalAccessException ex) {
            log.error("The data map class: " + map.getName() + " does not have a non-paramatised constructor or is inaccessable", ex);
            throw new IllegalArgumentException("Data map: " + map.getName() + "is invalled");
        }
    }

    private void initializeMap(IDatabase map) {
        JsonObject dataObject = data.getAsJsonObject(map.getName());

        if (dataObject == null) {
            log.warn("The data map: " + map.getName() + " is non-existant within the save file");
            map.deserialize(null);
            return;
        }

        map.deserialize(data);
    }

    private int mapsLoaded = 1;

    private void loadMaps() {
        for (IDatabase map : maps.values()) {
            JsonObject dataObject = data.getAsJsonObject(map.getName());

            if (dataObject == null) {
                log.warn("The data map: " + map.getName() + "is non-existant within the save file");
            }

            setProgress(new ProgressMonitor.ProgressObject(maps.size(), mapsLoaded, loading.getText() + map.getName()));
            map.deserialize(dataObject);
            mapsLoaded++;
        }

        mapsLoaded = 0;
    }

    private int mapsSaved = 0;

    private void saveMaps() {
        for (IDatabase map : maps.values()) {
            setProgress(new ProgressMonitor.ProgressObject(maps.size(), mapsSaved, saving.getText() + map.getName()));
            JsonObject dataObject = map.serialize();
            data.add(map.getName(), dataObject);
            mapsSaved++;
        }

        mapsSaved = 0;
    }

    public File getCurrectFile() {
        return currectFile;
    }

}
