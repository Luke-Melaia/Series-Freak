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

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import net.lm.seriesfreak.ui.language.Language;

/**
 * 
 * @author Luke Melaia
 */
public class LButton extends Button implements LanguageComponent<LButton> {

    private static final String PREFIX = "button.";

    private Language current = null;

    private String textKey = null;

    private String tooltipKey = null;

    public LButton() {
        super();
    }
    
    @Override
    public void onChanged(Language language) {
        this.current = language;
        this.setTextKey(textKey);
        this.setTooltipKey(tooltipKey);
    }

    @Override
    public LButton setTextKey(String key) {
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
    public LButton setTooltipKey(String key) {
        this.tooltipKey = key;

        if (current != null) {
            if (key == null) {
                this.setTooltip(null);
            } else {
                this.setTooltip(new Tooltip(current.getProperty(PREFIX + key + ".tooltip")));
            }
        }

        return this;
    }

    @Override
    public LButton setPromptKey(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
