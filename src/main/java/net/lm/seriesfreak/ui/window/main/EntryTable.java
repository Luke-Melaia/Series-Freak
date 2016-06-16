/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.lm.seriesfreak.ui.window.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.lm.seriesfreak.database.PreferencesDatabase;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.database.data.entries.TableEntry;
import net.lm.seriesfreak.database.data.properties.StringArrayProperty;

/**
 * 
 * @author Luke Melaia
 */
public class EntryTable extends TableView<TableEntry>{

    @SuppressWarnings("LeakingThisInConstructor")
    public EntryTable(){
        super();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EntryTable.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        
        try{
            loader.load();
        } catch (Exception ex){
            throw new RuntimeException("Failed to load entry table", ex);
        }
    }
    
    public void init(FxmlMainWindow window){
//        this.initEntryDatabaseConnection(window);
    }
    
//    private void initEntryDatabaseConnection(FxmlMainWindow window){
//        window.getEntryDatabase().addListener((EntryBase[] entries) -> {
//            this.getItems().clear();
//
//            for (EntryBase entry : window.getEntryDatabase().getEntries()) {
//                if (window.getCategoriesImpl().isValid(entry)) {
//                    if (window.getTopToolBar().getSearchText().equals("")) {
//                        this.getItems().add(new TableEntry(entry));
//
//                    } else {
//                        if (entry.getName().toLowerCase().contains(window.getTopToolBar().getSearchText().toLowerCase())) {
//                            this.getItems().add(new TableEntry(entry));
//
//                        }
//                    }
//                }
//            }
//            sort();
//        });
//
//        window.getPreferencesDatabase().addStateListener((PreferencesDatabase.State state) -> {
//            switch (state) {
//                case SERIALIZING:
//                    saveSortOrder(window);
//                    break;
//                case DESERIALIZING:
//                    loadSortOrder(window);
//                    break;
//            }
//        });
//    }
//    
//    private void loadSortOrder(FxmlMainWindow window) {
//        StringArrayProperty sortOrder = (StringArrayProperty) window.getPreferencesDatabase().getProperty("Sort Order");
//        StringArrayProperty sortType = (StringArrayProperty) window.getPreferencesDatabase().getProperty("Sort Type");
//
//        if (sortOrder == null) {
//            this.getSortOrder().addAll(rating, name);
//            this.sort();
//            return;
//        }
//
//        if (sortType == null) {
//            this.name.setSortType(TableColumn.SortType.ASCENDING);
//            this.rating.setSortType(TableColumn.SortType.ASCENDING);
//            return;
//        }
//
//        this.getSortOrder().clear();
//
//        int index = 0;
//
//        for (String columnName : sortOrder.get()) {
//            for (TableColumn<TableEntry, ?> tableColumn : this.getColumns()) {
//                if (tableColumn.toString().equals(columnName)) {
//                    tableColumn.setSortType(TableColumn.SortType.valueOf(sortType.get().get(index)));
//                    index++;
//                    this.getSortOrder().add(tableColumn);
//                }
//            }
//        }
//    }
//
//    private void saveSortOrder(FxmlMainWindow window) {
//        StringArrayProperty sortOrder = (StringArrayProperty) window.getPreferencesDatabase().getProperty("Sort Order");
//        StringArrayProperty sortType = (StringArrayProperty) window.getPreferencesDatabase().getProperty("Sort Type");
//
//        if (sortOrder == null) {
//            sortOrder = new StringArrayProperty();
//            window.getPreferencesDatabase().addProperty("Sort Order", sortOrder);
//        }
//
//        if (sortType == null) {
//            sortType = new StringArrayProperty();
//            window.getPreferencesDatabase().addProperty("Sort Type", sortType);
//        }
//
//        sortOrder.get().clear();
//        sortType.get().clear();
//
//        for (TableColumn<TableEntry, ?> column : this.getSortOrder()) {
//            sortOrder.get().add(column.toString());
//            sortType.get().add(column.getSortType().name());
//        }
//    }
    
}
