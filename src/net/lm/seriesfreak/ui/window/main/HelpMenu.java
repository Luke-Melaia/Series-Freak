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
package net.lm.seriesfreak.ui.window.main;

import net.lm.seriesfreak.ui.language.node.LMenu;
import net.lm.seriesfreak.ui.language.node.LMenuItem;
import net.lm.seriesfreak.ui.window.WindowLoader;
import net.lm.seriesfreak.ui.window.about.AboutWindow;
import net.lm.seriesfreak.ui.window.libraries.LibrariesWindow;

/**
 *
 * @author Luke Melaia
 */
class HelpMenu extends LMenu {

    private WindowLoader<LibrariesWindow> librariesWindowLoader = new WindowLoader<>(new LibrariesWindow());

    private WindowLoader<AboutWindow> aboutWindowLoader = new WindowLoader<>(new AboutWindow());

    //private LMenuItem help = new LMenuItem().setTextKey("help").setImage("question").register();
    private LMenuItem libraries = new LMenuItem().setImage("folder_color").setTextKey("libs").register();

    private LMenuItem about = new LMenuItem().setImage("information").setTextKey("about").register();

    private MainWindow mainWindow;

    public HelpMenu(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;
        this.setTextKey("help");
        addItems();
    }

    private void addItems() {
        libraries.setOnAction(event -> librariesWindowLoader.get().showAndWait());

        about.setOnAction(event -> aboutWindowLoader.get().showAndWait());

        this.getItems().addAll(libraries, about);
    }
}
