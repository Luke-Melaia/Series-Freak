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

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import net.lm.seriesfreak.ui.language.Language;

/**
 * @author Luke Melaia
 */
public class LLabel extends Label implements LanguageComponent<LLabel> {

    private static final String PREFIX = "label.";

    private Language current = null;

    private String textKey = null;

    private String tooltipKey = null;
    
    public LLabel() {
        super();
    }

    @Override
    public void onChanged(Language language) {
        this.current = language;
        this.setTextKey(textKey);
        this.setTooltipKey(tooltipKey);
    }

    @Override
    public LLabel setTextKey(String text) {
        textKey = text;

        if (current != null) {
            if (text == null) {
                this.setText("");
            } else {
                this.setText(current.getProperty(PREFIX + text + ".text"));
            }
        }
        
        return this;
    }

    @Override
    public LLabel setTooltipKey(String text) {
        this.tooltipKey = text;

        if (current != null) {
            if (text == null) {
                this.setTooltip(null);
            } else {
                this.setTooltip(new Tooltip(current.getProperty(PREFIX + text + ".tooltip")));
            }
        }
        
        return this;
    }

    @Override
    public LLabel setPromptKey(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
