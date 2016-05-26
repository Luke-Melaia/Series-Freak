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

/**
 * 
 * @author Luke Melaia
 */
public class EntryBuilder {

    private String name = null;

    private int episode = 0;

    private int episodes = 0;

    private Type type = Type.SERIES;

    private String date = "";

    private double rating = 0.0;

    private String rootFile = null;

    private String[] files = null;

    private boolean dropped = false;

    private boolean favorite = false;

    public EntryBuilder setName(String name) throws IllegalArgumentException {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Name empty or null");
        }

        this.name = name;
        return this;
    }

    public EntryBuilder setEpisode(int episode) throws IllegalArgumentException {
        if (episode < 0) {
            throw new IllegalArgumentException("Episode < 0");
        }

        this.episode = episode;
        return this;
    }

    public EntryBuilder setEpisodes(int episodes) throws IllegalArgumentException {
        if (episodes < 0) {
            throw new IllegalArgumentException("Episodes < 0");
        }

        this.episodes = episodes;
        return this;
    }
    
    public EntryBuilder setType(Type type) throws NullPointerException {
        if (type == null) {
            throw new NullPointerException("Type == null");
        }

        this.type = type;
        return this;
    }

    public EntryBuilder setDate(String date) {
        if (date == null) {
            date = "";
        }

        this.date = date;
        return this;
    }
    
    public EntryBuilder setRating(double rating) {
        this.rating = rating;
        return this;
    }
    
    public EntryBuilder setRoot(String rootFile) {
        if (rootFile != null && rootFile.equals("")) {
            rootFile = null;
        }

        this.rootFile = rootFile;
        return this;
    }

    public EntryBuilder setFiles(String... files) {
        if (files.length == 0) {
            files = null;
        }

        this.files = files;
        return this;
    }

    public EntryBuilder setDropped(boolean dropped) {
        this.dropped = dropped;
        return this;
    }

    public EntryBuilder setFavorite(boolean favorite) {
        this.favorite = favorite;
        return this;
    }

    public Entry create() {
        if (name == null) {
            throw new NullPointerException("Name has not been set");
        }
        
        if(episode >= episodes){
            episode = episodes;
            rating = 8.0;
        }

        Entry entry = new Entry();

        entry.setName(name)
                .setEpisode(episode)
                .setEpisodes(episodes)
                .setType(type)
                .setDate(date)
                .setRating(rating)
                .setRootFile(rootFile)
                .setFiles(files)
                .setDropped(dropped)
                .setFavorite(favorite);

        entry.validate();

        return entry;
    }
}
