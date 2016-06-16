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
import net.lm.seriesfreak.ui.window.main.MainWindow;

/**
 * 
 * @author Luke Melaia
 */
class OptionsMenu extends LMenu{
    
    private LMenuItem appearance = new LMenuItem().setImage("gear").setTextKey("appearance").register();
    
    private LMenuItem options = new LMenuItem().setImage("gear").setTextKey("options").register();
    
    private MainWindow mainWindow;
    
    public OptionsMenu(MainWindow mainWindow){
        super();
        this.mainWindow = mainWindow;
        
        this.setTextKey("options");
        addItems();
    }
    
    private void addItems(){
        this.getItems().addAll(appearance, options);
    }
}
