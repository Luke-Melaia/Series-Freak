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

import javafx.scene.control.TableColumn;
import net.lm.seriesfreak.ui.language.Language;

public class LTableColumn<S, T> extends TableColumn<S, T> implements LanguageComponent<LTableColumn<S, T>>{
    
    private static final String PREFIX = "tablecolumn.";
    
    private Language current = null;
    
    private String textKey = null;
    
    public LTableColumn() {
        super();
    }

    @Override
    public void onChanged(Language language) {
        this.current = language;
        this.setTextKey(textKey);
    }

    @Override
    public LTableColumn<S, T> setTextKey(String key) {
        textKey = key;

        if (current == null) {
            return this;
        }

        if (key == null) {
            this.setText("");
        } else {
            this.setText(current.getProperty(PREFIX + key + ".text"));
        }

        return this;
    }

    @Override
    public LTableColumn<S, T> setTooltipKey(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LTableColumn<S, T> setPromptKey(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String toString(){
        return this.textKey;
    }
}
