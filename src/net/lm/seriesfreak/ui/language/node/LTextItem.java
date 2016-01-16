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

import javax.swing.event.ChangeListener;
import net.lm.seriesfreak.ui.language.Language;
import net.lm.seriesfreak.ui.language.LanguageListener;
import net.lm.seriesfreak.ui.language.LanguageRegistry;

/**
 * @author Luke Melaia
 */
public class LTextItem implements LanguageListener {

    private static final String PREFIX = "textitem.";
    private static final String SUFFIX = ".text";

    private ChangeListener changeListener;

    private String key;

    private String text;

    public LTextItem(String key, ChangeListener listener) {
        this.key = PREFIX + key + SUFFIX;
        this.changeListener = listener;
    }

    @Override
    public void onChanged(Language language) {
        this.text = language.getProperty(key);
        
        if (changeListener != null) {
            changeListener.stateChanged(null);
        }
    }

    public void setKey(String key) {
        this.key = PREFIX + key + SUFFIX;
        onChanged(LanguageRegistry.getInstance().getCurrentLanguage());
    }

    public String getText() {
        return this.text;
    }

    public LTextItem register() {
        LanguageRegistry.registerListener(this);
        return this;
    }
    
    @Override
    public String toString(){
        return "LTextItem: " + this.getText();
    }
}
