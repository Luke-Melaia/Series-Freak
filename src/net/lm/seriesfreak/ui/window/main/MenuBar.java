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

/**
 *
 * @author Luke Melaia
 */
class MenuBar extends javafx.scene.control.MenuBar {

    private FileMenu file;

    private LMenu options;

    private HelpMenu help;

    public MenuBar(MainWindow mainWindow) {
        super();

        file = (FileMenu) new FileMenu(mainWindow).register();
        options = new OptionsMenu(mainWindow).register();
        help = (HelpMenu) new HelpMenu(mainWindow).register();

        this.getMenus().addAll(file, help);
    }

    public FileMenu getFile() {
        return file;
    }

    public HelpMenu getHelp() {
        return help;
    }

}
