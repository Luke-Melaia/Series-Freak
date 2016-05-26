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
import java.util.Arrays;
import java.util.Objects;
import net.lm.seriesfreak.util.ChangeListener;

/**
 * 
 * @author Luke Melaia
 */
public abstract class EntryBase {

    protected String name;

    protected int episode = -1;

    public String getName() {
        return this.name;
    }

    public EntryBase setName(String name) {
        this.name = name;
        return this;
    }

    public int getEpisode() {
        return this.episode;
    }

    public EntryBase setEpisode(int episode) {
        this.episode = episode;
        return this;
    }

    public abstract int getEpisodes();

    public abstract EntryBase setEpisodes(int episodes);

    public abstract Type getType();

    public abstract EntryBase setType(Type type);

    public abstract String getDate();

    public abstract EntryBase setDate(String date);

    public abstract double getRating();

    public abstract EntryBase setRating(double rating);

    public abstract String getRootFile();

    public abstract EntryBase setRootFile(String rootFile);

    public abstract String[] getFiles();

    public abstract EntryBase setFiles(String... files);

    public abstract boolean isDropped();

    public abstract EntryBase setDropped(boolean dropped);

    public abstract boolean isFavorite();

    public abstract EntryBase setFavorite(boolean favourite);

    public abstract boolean isFinished();

    public abstract EntryBase finish(double rating);

    protected abstract EntryBase deserialize(JsonObject entryData, EntryBase[] entries);

    protected abstract JsonObject serialize();

    public abstract EntryBase addListener(ChangeListener<EntryBase> listener);

    public abstract EntryBase removeListener(ChangeListener<EntryBase> listener);

    public abstract EntryBase open() throws OpenException;

    public abstract EntryBase openRoot() throws OpenException;

    public abstract String getTypeName();

    @Deprecated
    public abstract EntryBase check() throws IllegalInputException;

    public abstract EntryBase validate();

    public void copyFrom(EntryBase entryBase) {
        entryBase.validate();

        this.setName(entryBase.getName());
        this.setEpisode(entryBase.getEpisode());
        this.setEpisodes(entryBase.getEpisodes());
        this.setType(entryBase.getType());
        this.setDate(entryBase.getDate());
        this.setRating(entryBase.getRating());
        this.setRootFile(entryBase.getRootFile());
        if (entryBase.getFiles() != null) {
            this.setFiles(Arrays.copyOf(entryBase.getFiles(), entryBase.getFiles().length));
        }

        this.validate();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof EntryBase)) {
            return false;
        }

        EntryBase other = (EntryBase) obj;

        return other.getName().toLowerCase().equals(this.getName().toLowerCase());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(getName().toLowerCase());
        return hash;
    }

    @Override
    public abstract String toString();
}
