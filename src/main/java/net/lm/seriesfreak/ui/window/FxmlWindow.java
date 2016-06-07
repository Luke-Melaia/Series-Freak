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

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * A window class designed to initialize the gui from an FXML files.
 *
 * @author Luke Melaia
 * @param C the controller class for this window.
 */
public abstract class FxmlWindow<C extends Controller> extends Window {

    /**
     * Path to the FXML files
     */
    private static final String FXML_FILES = "/fxml/";

    /**
     * Path to the FXML file for this window.
     */
    private String fxmlClass;

    /**
     * Width/height of the window. Only used when constructing the window.
     */
    protected double width, height;

    /**
     * The controller class.
     */
    protected C controller;

    /**
     * Root pane object.
     */
    protected Pane root;

    /**
     * Constructs a new FXML window from an FXML file.
     *
     * @param title the window title
     * @param fxmlClassName name of the FXML file without file ending.
     * @param width the width the window should be.
     * @param height the hight the window should be.
     */
    public FxmlWindow(String title, String fxmlClassName, double width, double height) {
        super();
        this.setTitle(title);
        this.width = width;
        this.height = height;
        this.fxmlClass = FXML_FILES + fxmlClassName + ".fxml";
    }

    /**
     * Loads the content from the FXML file into the {@link #root } object.
     *
     * <b>This isn't called from the constructor.</b>
     *
     * @throws WindowLoadException if the FXML can't be read from.
     */
    protected void loadFXML() {
        URL location = getClass().getResource(fxmlClass);
        FXMLLoader loader = new FXMLLoader(location);

        try {
            root = (Pane) loader.load();
        } catch (IOException exception) {
            throw new WindowLoadException("Couldn't load " + getClass().getName(), exception);
        }

        this.controller = (C) ((Controller) loader.getController()).setWindow(this);
    }

    /**
     * Loads a window as thought a {@link WindowLoader } had.
     * 
     * @param window the window to load.
     * @throws WindowLoadException if the FXML file cannot be read.
     * @throws IllegalStateException if the window is already loaded.
     */
    public static void loadWindow(FxmlWindow window){
        if(window.isLoaded()){
            throw new IllegalStateException("Window: " + window.getClass().getName() + " has already been loaded.");
        }
        
        window.initProperties();
        window.loadFXML();
        window.setScene(new Scene(window.root, window.width, window.height));
        window.initComponents();
        window.addComponents();
        window.setLoaded();
    }
    
    /**
     * Thrown when an FXML window fails to load.
     */
    static class WindowLoadException extends RuntimeException {

        public WindowLoadException(String title, Throwable cause) {
            super(title, cause);
        }
    }
}
