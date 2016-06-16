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

/**
 * Base class for FXML controllers.
 *
 * @author Luke Melaia
 * @param T the type of window for this controller.
 */
public abstract class Controller<T extends Stage> {

    /**
     * The window using this controller.
     */
    protected T window;

    /**
     * Sets the {@link #window}.
     *
     * @param window
     * @return {@code this}
     */
    public Controller setWindow(T window) {
        this.window = window;
        return this;
    }

    /**
     * Called when the controller has been properly initialized.
     */
    public abstract void onInit();
}
