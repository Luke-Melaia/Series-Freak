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
package net.lm.seriesfreak.database.data.entries;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.awt.Desktop;
import java.io.File;
import java.util.Arrays;
import net.lm.seriesfreak.database.implementation.LoadType;
import net.lm.seriesfreak.util.ChangeListener;
import net.lm.seriesfreak.util.ChangeListenerList;
import net.lm.seriesfreak.util.RandomUtils;

/**
 *
 * @author Luke Melaia
 */
@LoadType
public class Entry extends EntryBase {

    private int episodes = -1;

    private Type type = Type.SERIES;

    private String date = "";

    private double rating = 0;

    private String rootFile;

    private String[] files;

    private boolean dropped;

    private boolean favourite;

    private ChangeListenerList<EntryBase> listenerList = new ChangeListenerList<>(5);

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Entry setName(String name) {
        this.name = name;
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public int getEpisode() {
        return episode;
    }
    
    @Override
    public Entry setEpisode(int episode) {
        this.episode = episode;
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public int getEpisodes() {
        return episodes;
    }
    
    @Override
    public Entry setEpisodes(int episodes) {
        this.episodes = episodes;
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public Type getType() {
        return type;
    }
    
    @Override
    public Entry setType(Type type) {
        this.type = type;
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public Entry setDate(String date) {
        this.date = date;
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public double getRating() {
        return rating;
    }
    
    @Override
    public Entry setRating(double rating) {
        this.rating = rating;
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public String getRootFile() {
        return rootFile;
    }

    @Override
    public Entry setRootFile(String rootFile) {
        this.rootFile = rootFile;
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public String[] getFiles() {
        return files;
    }

    @Override
    public Entry setFiles(String[] files) {
        this.files = files;
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public boolean isDropped() {
        return dropped;
    }

    @Override
    public Entry setDropped(boolean dropped) {
        this.dropped = dropped;
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public boolean isFavorite() {
        return favourite;
    }

    @Override
    public Entry setFavorite(boolean favourite) {
        this.favourite = favourite;
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public boolean isFinished() {
        return this.episode >= this.episodes && this.getEpisodes() != -1;
    }

    @Override
    public EntryBase finish(double rating) {
        if (this.getEpisodes() == -1) {
            this.episodes = episode;
        } else {
            this.episode = episodes;
        }
        this.rating = rating;
        this.setDate(RandomUtils.getCurrentDate());
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public EntryBase deserialize(JsonObject entryData, EntryBase[] entries) throws IllegalArgumentException {
        try {
            try {
                setEpisode(entryData.get("Episode").getAsInt());
            } catch (NullPointerException npe) {
                throw new NullPointerException("Episode not found");
            }
            try {
                setEpisodes(entryData.get("Episodes").getAsInt());
            } catch (NullPointerException npe) {
                throw new NullPointerException("Episodes not found");
            }
            try {
                setType(Type.valueOf(entryData.get("Type").getAsString()));
            } catch (NullPointerException npe) {
                throw new NullPointerException("Type not found");
            }
            try {
                setDate(entryData.get("Date").getAsString());
            } catch (NullPointerException npe) {
                throw new NullPointerException("Date not found");
            }
            try {
                setRating(entryData.get("Rating").getAsDouble());
            } catch (NullPointerException npe) {
                throw new NullPointerException("Rating not found");
            }
            try {
                setRootFile(entryData.get("RootFile").getAsString());
            } catch (NullPointerException npe) {
                this.setRootFile(null);
            }
            try {
                setDropped(entryData.get("Dropped").getAsBoolean());
            } catch (NullPointerException npe) {
                throw new NullPointerException("Dropped not found");
            }
            try {
                setFavorite(entryData.get("Favourite").getAsBoolean());
            } catch (NullPointerException npe) {
                throw new NullPointerException("Favourite not found");
            }

            try {
                JsonArray jsonFiles = entryData.get("Files").getAsJsonArray();
                String[] newFiles = new String[jsonFiles.size()];

                for (int i = 0; i < newFiles.length; i++) {
                    newFiles[i] = jsonFiles.get(i).getAsString();
                }
                this.setFiles(newFiles);
            } catch (NullPointerException npe) {
                setFiles(null);
            }

        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed to create entry - corrupt entry data", ex);
        }

        return this;
    }

    @Override
    public JsonObject serialize() {
        JsonObject data = new JsonObject();

        data.addProperty("Episode", getEpisode());
        data.addProperty("Episodes", getEpisodes());
        data.addProperty("Type", getType().name());
        data.addProperty("Date", getDate());
        data.addProperty("Rating", getRating());
        data.addProperty("RootFile", getRootFile());
        data.addProperty("Dropped", isDropped());
        data.addProperty("Favourite", isFavorite());

        JsonArray jsonFiles = new JsonArray();
        if (files != null) {
            for (String file : files) {
                jsonFiles.add(new JsonPrimitive(file));

            }
        }
        data.add("Files", jsonFiles);

        return data;
    }

    @Override
    public String getTypeName() {
        return "Entries";
    }

    @Deprecated
    @Override
    public Entry check() throws IllegalInputException {
        if (name.equals("")) {
            throw new IllegalInputException("No Name", InputException.NO_NAME);
        }

        if (episodes < -1) {
            throw new IllegalInputException("episodes < -1", InputException.EPISODES_TOO_LOW);
        }

        if (episode < 0) {
            throw new IllegalInputException("episode < 0", InputException.EPISODE_TOO_LOW);
        }

        if (episode > episodes && episodes != -1) {
            throw new IllegalInputException("episode > episodes", InputException.EPISODE_TOO_HIGH);
        }

        if (episode == episodes && rating == 0.0) {
            throw new IllegalInputException("No rating", InputException.NO_RATING);
        }

        return this;
    }

    @Override
    public Entry validate() {
        if (rating != 0.0) {
            this.episode = episodes;
        }

        if (this.isFinished() && this.getDate().equals("")) {
            this.setDate(RandomUtils.getCurrentDate());
        }

        return this;
    }

    @Override
    public Entry addListener(ChangeListener<EntryBase> listener) {
        listenerList.addListener(listener);
        return this;
    }
    
    @Override
    public Entry removeListener(ChangeListener<EntryBase> listener) {
        listenerList.removeListener(listener);
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s [name = %s, episode = %s, episodes = %s, type = %s, date = %s, rating = %s, root = %s, files = %s, dropped = %s, favourite = %s, number of listeners = %s]",
                this.getClass().getName(),
                this.getName(),
                this.getEpisode(),
                this.getEpisodes(),
                this.getType().name(),
                this.getDate(),
                this.getRating(),
                this.getRootFile(),
                Arrays.toString(getFiles()),
                this.isDropped(),
                this.isFavorite(),
                this.listenerList.size()
        );
    }
    
    @Override
    public Entry open() throws OpenException {

        if (this.getFiles() == null && (this.getRootFile() == null || this.getRootFile().equals(""))) {
            throw new OpenException("No files to open", Reason.NO_FILES);
        }

        try {
            int nextOpenableFileIndex = this.getEpisode();
            File nextFile = new File(this.getFiles()[nextOpenableFileIndex]);

            if (nextOpenableFileIndex == this.getEpisodes() + 1 && this.getEpisodes() != -1) {
                throw new IndexOutOfBoundsException();
            }

            if (!nextFile.exists()) {
                openRoot();
                return this;
            }

            try {
                Desktop.getDesktop().open(nextFile);
            } catch (Exception ex) {
                openRoot();
            }

        } catch (IndexOutOfBoundsException | NullPointerException ex) {
            openRoot();
            return this;
        }

        if (this.getEpisode() != this.getEpisodes() - 1) {
            this.setEpisode(this.getEpisode() + 1);
        } else {
            throw new OpenException("Needs a rating", Reason.RATING_REQUIRED);
        }

        return this;
    }

    @Override
    public Entry openRoot() throws OpenException {

        if (this.getRootFile() == null || this.getRootFile().equals("")) {
            throw new OpenException("No root file to open", Reason.NO_FILES);
        }

        File root = new File(this.getRootFile());

        if (!root.exists()) {
            throw new OpenException("Root doesn't exist", Reason.MISSING_FILES);
        }

        try {
            Desktop.getDesktop().open(root);
        } catch (Exception ex) {
            throw new OpenException("Failed to open root", Reason.UNKNOWN, ex);
        }

        return this;
    }
}
