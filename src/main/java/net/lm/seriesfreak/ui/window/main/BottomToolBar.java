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

import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import net.lm.seriesfreak.database.EntryListener;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.database.data.entries.RewatchEntry;
import net.lm.seriesfreak.database.data.entries.TableEntry;
import net.lm.seriesfreak.ui.language.node.LTextItem;
import net.lm.seriesfreak.ui.node.TextualProgressBar;
import net.lm.seriesfreak.ui.window.main.MainWindow;

/**
 * 
 * @author Luke Melaia
 */
class BottomToolBar extends ToolBar {
    
    private static LTextItem favoriteText = new LTextItem("favorite", null).register();
    
    private static LTextItem droppedText = new LTextItem("dropped", null).register();
    
    private static LTextItem rewatch = new LTextItem("rewatch", null).register();
    
    private static LTextItem normal = new LTextItem("normal", null).register();
    
    private TextualProgressBar progressBar = new TextualProgressBar();

    private EntryBase selected;
    
    private MainWindow mainWindow;
    
    public BottomToolBar(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;
        this.mainWindow.getEntryDatabase().addListener(entryListener);
        addItems();
    }

    private void addItems() {
        progressBar.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(progressBar, Priority.ALWAYS);
        
        this.getItems().add(progressBar);
    }
    
    public void setEntry(EntryBase entry){
        this.selected = entry;
        setProperties();
    }
    
    private void setProperties(){
        if(selected == null){
            progressBar.setProgress(-1);
            progressBar.setText("");
            return;
        }
        
        String text = "";
        TableEntry tableEntry = new TableEntry(selected);
        
        text += ((selected instanceof RewatchEntry) ? rewatch.getText() : normal.getText());
        text += ": " + selected.getName();
        text += " | " + tableEntry.getProgress().getText();
        text += " | " + tableEntry.getType();
        text += " | " + tableEntry.getDate();
        text += " | " + tableEntry.getRating();
        text += (selected.isFavorite()) ? " | " + favoriteText.getText() : "";
        text += (selected.isDropped()) ? " | " + droppedText.getText() : "";
        
        this.progressBar.setText(text);
        this.progressBar.setProgress(tableEntry.getProgress().getProgress());
    }
    
    private EntryListener entryListener = (EntryBase[] paramater) -> {
        this.setEntry(this.mainWindow.getEntryDatabase().getSelected());
    };
}
