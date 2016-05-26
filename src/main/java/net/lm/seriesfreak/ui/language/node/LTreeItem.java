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


package net.lm.seriesfreak.ui.language.node;

import javafx.scene.control.TreeItem;
import net.lm.seriesfreak.ui.language.Language;

public class LTreeItem extends TreeItem<String> implements LanguageComponent<LTreeItem>{
    
    private static final String PREFIX = "treeitem.";
    
    private Language current;
    
    private String textKey;
    
    public LTreeItem(){
        super();
    }

    @Override
    public void onChanged(Language language) {
        this.current = language;
        this.setTextKey(textKey);
    }

    @Override
    public LTreeItem setTextKey(String key) {
        textKey = key;

        if (current == null) {
            return this;
        }

        if (key == null) {
            this.setValue("");
        } else {
            this.setValue(current.getProperty(PREFIX + key + ".text"));
        }

        return this;
    }
    

    @Override
    public LTreeItem setTooltipKey(String key) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public LTreeItem setPromptKey(String key) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
