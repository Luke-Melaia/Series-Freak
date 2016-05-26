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

import java.util.Objects;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.database.data.entries.Type;

/**
 *
 * @author Luke Melaia
 */
public class EntryMetadata {

    private final String name;

    private final Type type;

    public EntryMetadata(EntryBase entry) {
        this.name = entry.getName();
        this.type = entry.getType();
    }

    public EntryMetadata(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof EntryMetadata)) {
            return false;
        }

        EntryMetadata other = (EntryMetadata) object;

        if (other.getName().equals(this.getName())) {
            if (other.getType().equals(this.getType())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.getName());
        hash = 97 * hash + Objects.hashCode(this.getType());
        return hash;
    }
}
