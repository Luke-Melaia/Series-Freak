/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.lm.seriesfreak.ui.window.main;

import net.lm.seriesfreak.database.EntryDatabase;
import net.lm.seriesfreak.database.PreferencesDatabase;
import net.lm.seriesfreak.database.implementation.DatabaseHandler;
import net.lm.seriesfreak.ui.window.FxmlWindow;
import net.lm.seriesfreak.ui.window.WindowLoader;
import net.lm.seriesfreak.ui.window.update.UpdateWindow;

/**
 * The main window for series freak.
 * 
 * @author Luke Melaia
 */
public class FxmlMainWindow extends FxmlWindow<MainWindowController>{

    /**
     * The window loader for the update window.
     */
    private WindowLoader<UpdateWindow> updateWindowLoader;
    
    /**
     * The database handler.
     */
    private DatabaseHandler database;
    
    /**
     * The database where the entries are stored.
     */
    private EntryDatabase entryDatabase;
    
    private PreferencesDatabase preferencesDatabase;
    
    private Categories categories;
    
    /**
     * Default constructor.
     */
    public FxmlMainWindow(WindowLoader<UpdateWindow> updateWindowLoader, DatabaseHandler database) {
        super("Series Freak", "MainWindow", 650, 500);//TODO: Get title from config file.
        this.updateWindowLoader = updateWindowLoader;
        this.database = database;
        this.entryDatabase = (EntryDatabase) database.getMap(EntryDatabase.class);
        this.preferencesDatabase = (PreferencesDatabase) database.getMap(PreferencesDatabase.class);
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

    /**
     * @return the updateWindowLoader
     */
    public WindowLoader<UpdateWindow> getUpdateWindowLoader() {
        return updateWindowLoader;
    }

    /**
     * @return the database
     */
    public DatabaseHandler getDatabase() {
        return database;
    }

    /**
     * @return the entryDatabase
     */
    public EntryDatabase getEntryDatabase() {
        return entryDatabase;
    }
    
    public PreferencesDatabase getPreferencesDatabase() {
        return preferencesDatabase;
    }

}
