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
package net.lm.seriesfreak.database.data.categories;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.database.data.entries.RewatchEntry;

/**
 *
 * @author Luke Melaia
 */
public class UserCategory extends Category {

    private List<EntryMetadata> entries = new ArrayList<>();

    public UserCategory(String name, boolean ignoresDropped) {
        super(ignoresDropped);
        this.setImage("flag_green");
        this.setValue(name);
    }

    @Override
    protected boolean isValid(EntryBase entryBase) {
        EntryBase entry;

        if (entryBase instanceof RewatchEntry) {
            entry = ((RewatchEntry) entryBase).getOriginalEntry();
        } else {
            entry = entryBase;
        }

        return this.containsEntry(entry);
    }

    public boolean containsEntry(EntryBase entry) {
        return entries.contains(new EntryMetadata(entry));
    }

    public void addEntry(EntryBase entry) {
        if (entry instanceof RewatchEntry) {
            entry = ((RewatchEntry) entry).getOriginalEntry();
        }

        if (!entries.contains(new EntryMetadata(entry))) {
            this.entries.add(new EntryMetadata(entry));
        }
    }

    public EntryMetadata[] getEntryMetadatas() {
        return this.entries.toArray(new EntryMetadata[this.entries.size()]);
    }

    public void removeEntry(EntryBase entry) {
        if (entry instanceof RewatchEntry) {
            entry = ((RewatchEntry) entry).getOriginalEntry();
        }

        this.entries.remove(new EntryMetadata(entry));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof UserCategory)) {
            return false;
        }

        return this.getValue().toLowerCase().equals(((UserCategory) obj).getValue().toLowerCase());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.getValue());
        return hash;
    }
}
