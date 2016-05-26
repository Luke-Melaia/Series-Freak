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
public class RewatchEntryBuilder {
    
    private String name = null;

    private EntryBase original = null;

    private int episode = 0;

    public RewatchEntryBuilder setName(String name) throws IllegalArgumentException {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Name empty or null");
        }

        this.name = name;
        return this;
    }

    public RewatchEntryBuilder setOriginalEntry(EntryBase original) throws NullPointerException {
        if (original == null) {
            throw new NullPointerException("Original entry is null");
        }

        this.original = original;
        return this;
    }

    public RewatchEntryBuilder setEpisode(int episode) {
        this.episode = episode;
        return this;
    }

    public RewatchEntry create() throws NullPointerException {
        if (name == null) {
            throw new NullPointerException("Name has not been set");
        }

        if (original == null) {
            throw new NullPointerException("Original entry has not been set");
        }

        RewatchEntry rewatchEntry = new RewatchEntry();

        rewatchEntry.setName(name)
                .setEpisode(episode)
                .setOriginalEntry(original);
        
        if(episode <= original.getEpisodes()){
            rewatchEntry.finish(0);
        }
        
        return rewatchEntry;
    }
}
