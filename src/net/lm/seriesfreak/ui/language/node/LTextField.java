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

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import net.lm.seriesfreak.ui.language.Language;

public class LTextField extends TextField implements LanguageComponent<LTextField>{

    private static final String PREFIX = "textfield.";

    private Language current = null;
    
    private String textKey = null;
    
    private String tooltipKey = null;
    
    private String promptKey = null;
    
    public LTextField() {
        super();
    }

    @Override
    public void onChanged(Language language) {
        this.current = language;
        this.setTextKey(textKey);
        this.setTooltipKey(tooltipKey);
        this.setPromptKey(promptKey);
    }

    @Override
    public LTextField setTextKey(String key) {
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
    public LTextField setTooltipKey(String key) {
        this.tooltipKey = key;

        if (current == null) {
            return this;
        }
        
        if (key == null) {
                this.setTooltip(null);
            } else {
                this.setTooltip(new Tooltip(current.getProperty(PREFIX + key + ".tooltip")));
            }

        return this;
    }

    @Override
    public LTextField setPromptKey(String key) {
        this.promptKey = key;
        
        if (current == null) {
            return this;
        }
        
        if (key == null) {
                this.setPromptText("");
            } else {
                this.setPromptText(current.getProperty(PREFIX + key + ".prompt"));
            }

        return this;
    }

}
