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

import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.ui.language.node.LTreeItem;

/**
 *
 * @author Luke Melaia
 */
public abstract class Category extends LTreeItem {

    private boolean ignoresDropped;
    
    public Category(boolean ignoresDropped) {
        this.ignoresDropped = ignoresDropped;
    }

    protected abstract boolean isValid(EntryBase entryBase);

    public boolean isAllowed(EntryBase entry) {
        if (isValid(entry)) {
            if (entry.isDropped()) {
                if (!ignoresDropped) {
                    return true;
                }
            } else {
                if (ignoresDropped) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Category setTextKey(String key) {
        super.setTextKey(key);
        return this;
    }

    @Override
    public Category setImage(String key) {
        super.setImage(key);
        return this;
    }

    @Override
    public Category register() {
        super.register();
        return this;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(!(obj instanceof Category)) return false;
        
        Category cat = (Category) obj;
        
        return (cat.getValue().equals(this.getValue()));
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
}
