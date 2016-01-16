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

import com.google.gson.JsonObject;
import java.awt.Desktop;
import java.io.File;
import net.lm.seriesfreak.database.implementation.LoadType;
import net.lm.seriesfreak.util.ChangeListener;
import net.lm.seriesfreak.util.ChangeListenerList;

/**
 *
 * @author Luke Melaia
 */
@LoadType
public class RewatchEntry extends EntryBase {

    private EntryBase originalEntry;

    private final ChangeListenerList<EntryBase> listenerList = new ChangeListenerList<>(5);

    private final ChangeListener<EntryBase> episodesChangeListener = (EntryBase) -> {
        if (originalEntry.getEpisodes() != -1 && this.episode > originalEntry.getEpisodes()) {
            this.episode = originalEntry.getEpisodes();
        }
    };

    @Override
    public String getName() {
        return name;
    }

    @Override
    public RewatchEntry setName(String name) {
        this.name = name;
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public int getEpisode() {
        return episode;
    }

    @Override
    public RewatchEntry setEpisode(int episode) {
        this.episode = episode;
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public int getEpisodes() {
        return this.originalEntry.getEpisodes();
    }

    @Override
    public RewatchEntry setEpisodes(int episodes) {
        this.originalEntry.setEpisodes(episodes);
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public Type getType() {
        return this.originalEntry.getType();
    }

    @Override
    public RewatchEntry setType(Type type) {
        this.originalEntry.setType(type);
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public String getDate() {
        return this.originalEntry.getDate();
    }

    @Override
    public RewatchEntry setDate(String date) {
        this.originalEntry.setDate(date);
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public double getRating() {
        return this.originalEntry.getRating();
    }

    @Override
    public RewatchEntry setRating(double rating) {
        this.originalEntry.setRating(rating);
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public String getRootFile() {
        return this.originalEntry.getRootFile();
    }

    @Override
    public RewatchEntry setRootFile(String rootFile) {
        this.originalEntry.setRootFile(rootFile);
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public String[] getFiles() {
        return this.originalEntry.getFiles();
    }

    @Override
    public RewatchEntry setFiles(String[] files) {
        this.originalEntry.setFiles(files);
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public boolean isDropped() {
        return this.originalEntry.isDropped();
    }
    
    @Override
    public RewatchEntry setDropped(boolean dropped) {
        this.originalEntry.setDropped(dropped);
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public boolean isFavorite() {
        return this.originalEntry.isFavorite();
    }

    @Override
    public RewatchEntry setFavorite(boolean favourite) {
        this.originalEntry.setFavorite(favourite);
        listenerList.notifyListeners(this);
        return this;
    }

    public EntryBase getOriginalEntry() {
        return originalEntry;
    }

    public RewatchEntry setOriginalEntry(EntryBase originalEntry) {
        this.originalEntry = originalEntry;
        this.originalEntry.addListener(episodesChangeListener);
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public boolean isFinished() {
        return this.episode >= this.getEpisodes();
    }

    @Override
    public EntryBase finish(double rating) {
        if (this.getEpisodes() == -1) {
            this.setEpisode(episode);
        } else {
            this.setEpisode(getEpisodes());
        }
        listenerList.notifyListeners(this);
        return this;
    }

    @Override
    public EntryBase deserialize(JsonObject entryData, EntryBase[] entries) throws IllegalArgumentException {

        try {
            try {
                this.setEpisode(entryData.get("Episode").getAsInt());
            } catch (NullPointerException npe) {
                throw new NullPointerException("Episode not found");
            }

            for (EntryBase entryBase : entries) {
                if (entryBase.getName().equals(entryData.get("OriginalEntry").getAsString())) {
                    this.setOriginalEntry(entryBase);
                }
            }

            if (this.getOriginalEntry() == null) {
                throw new NullPointerException("Original entry not found");
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
        data.addProperty("OriginalEntry", getOriginalEntry().getName());

        return data;
    }

    @Override
    public String getTypeName() {
        return "RewatchEntries";
    }

    @Deprecated
    @Override
    public RewatchEntry check() throws IllegalInputException {
        if (this.originalEntry == null) {
            throw new NullPointerException("No original entry");
        }

        if (name.equals("")) {
            throw new IllegalInputException("No Name", InputException.NO_NAME);
        }

        if (episode < 0) {
            throw new IllegalInputException("episode < 0", InputException.EPISODE_TOO_LOW);
        }

        if (episode > originalEntry.getEpisodes()) {
            throw new IllegalInputException("episode > originalEntry.episodes", InputException.EPISODE_TOO_HIGH);
        }

        return this;
    }

    @Override
    public RewatchEntry validate() {
        return this;
    }

    @Override
    public EntryBase addListener(ChangeListener<EntryBase> listener) {
        listenerList.addListener(listener);
        return this;
    }

    @Override
    public EntryBase removeListener(ChangeListener<EntryBase> listener) {
        listenerList.removeListener(listener);
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s [name = %s, episode = %s, original entry = %s, number of listeners = %s]",
                this.getClass().getCanonicalName(),
                this.getName(),
                this.getEpisode(),
                this.getOriginalEntry(),
                this.listenerList.size()
        );
    }

    @Override
    public RewatchEntry open() throws OpenException {

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

        if (this.getEpisode() != this.getEpisodes()) {
            this.setEpisode(this.getEpisode() + 1);
        }

        return this;
    }

    @Override
    public RewatchEntry openRoot() throws OpenException {

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
