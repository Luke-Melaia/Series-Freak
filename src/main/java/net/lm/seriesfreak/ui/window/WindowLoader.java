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
package net.lm.seriesfreak.ui.window;

import javafx.scene.Scene;

/**
 * Allows windows to lazily loaded. 
 * 
 * @author Luke Melaia
 * @param T the window to load.
 */
public class WindowLoader<T extends Window> {

    /**
     * The window.
     */
    private final T win;

    /**
     * Default constructor.
     * 
     * @param win the window to load.
     */
    public WindowLoader(T win) {
        this.win = win;
    }

    /**
     * Loads the window.
     */
    private void load() {
        win.initProperties();
        
        if (win instanceof FxmlWindow){
            FxmlWindow window = (FxmlWindow) win;
            window.loadFXML();
            window.setScene(new Scene(window.root, window.width, window.height));
        }
        
        win.initComponents();
        win.addComponents();
        win.setLoaded();
    }

    /**
     * Loads the window if not already loaded and returns the window.
     * 
     * @return the window.
     */
    public synchronized T get() {
        if (!win.isLoaded()) {
            load();
        }

        return win;
    }
}
