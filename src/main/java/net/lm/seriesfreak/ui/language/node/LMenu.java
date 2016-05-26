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

import javafx.scene.control.Menu;
import net.lm.seriesfreak.ui.language.Language;

/**
 * @author Luke Melaia
 */
public class LMenu extends Menu implements LanguageComponent<LMenu> {

    private static final String PREFIX = "menu.";

    private Language current = null;

    private String textKey = null;

    public LMenu() {
        super();
    }
    
    @Override
    public void onChanged(Language language) {
        current = language;
        this.setTextKey(textKey);
    }

    @Override
    public LMenu setTextKey(String key) {
        this.textKey = key;

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
    public LMenu setTooltipKey(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LMenu setPromptKey(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
