/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.lm.seriesfreak.ui.window.main;

import net.lm.seriesfreak.ui.controllers.MainWindowController;
import net.lm.seriesfreak.ui.window.FxmlWindow;

/**
 * The main window for series freak.
 * 
 * @author Luke Melaia
 */
public class FxmlMainWindow extends FxmlWindow<MainWindowController>{

    /**
     * Default constructor.
     */
    public FxmlMainWindow() {
        super("Series Freak", "MainWindow", 650, 500);//TODO: Get title from config file - maybe.
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void initProperties() {
        
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void initComponents() {

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void addComponents() {
        
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void destroy() {

    }

}
