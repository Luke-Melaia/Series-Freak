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

import javafx.stage.Stage;
import net.lm.seriesfreak.util.ImageHelper;

/**
 *
 * @author Luke Melaia
 */
public abstract class Window extends Stage {

    private boolean loaded = false;

    protected Window() {
        super();
        this.getIcons().add(ImageHelper.getApplicationIcon());
    }

    public abstract void initProperties();

    public abstract void initComponents();

    public abstract void addComponents();

    public abstract void destroy();

    public final void setLoaded() {
        this.loaded = true;
    }

    public final boolean isLoaded() {
        return this.loaded;
    }

    /**
     * Loads a window as thought a {@link WindowLoader } had.
     *
     * @param window the window to load.
     * @throws IllegalStateException if the window is already loaded.
     */
    public static void loadWindow(Window window) {
        if (window.isLoaded()) {
            throw new IllegalStateException("Window: " + window.getClass().getName() + " has already been loaded.");
        }

        window.initProperties();
        window.initComponents();
        window.addComponents();
        window.setLoaded();
    }
}
