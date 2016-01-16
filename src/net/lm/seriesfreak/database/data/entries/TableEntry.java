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
package net.lm.seriesfreak.database.data.entries;

import net.lm.seriesfreak.ui.language.node.LTextItem;
import net.lm.seriesfreak.ui.node.TextualProgress;

/**
 *
 * @author Luke Melaia
 */
public class TableEntry {

    private static final LTextItem series = new LTextItem("series", null).register();

    private static final LTextItem movie = new LTextItem("movie", null).register();

    private static final LTextItem book = new LTextItem("book", null).register();

    private static final LTextItem incomplete = new LTextItem("incomplete", null).register();

    private final EntryBase entryBase;

    public TableEntry(EntryBase entryBase) {
        this.entryBase = entryBase;
    }

    public String getName() {
        return this.entryBase.getName();
    }

    public TextualProgress getProgress() {
        String text = this.entryBase.getEpisode() + " / " + ((this.entryBase.getEpisodes() == -1) ? "?" : String.valueOf(this.entryBase.getEpisodes()));
        double progress = this.entryBase.getEpisodes() != -1 ? (double) this.entryBase.getEpisode() / this.entryBase.getEpisodes() : -1;
        return new TextualProgress(progress, text);
    }

    public String getType() {
        switch (entryBase.getType()) {
            case SERIES:
                return series.getText();
            case MOVIE:
                return movie.getText();
            default:
                return book.getText();
        }
    }

    public String getDate() {
        if(entryBase instanceof RewatchEntry){
            if(!entryBase.isFinished()){
                return incomplete.getText();
            }
        }
        
        return (entryBase.getDate().equals("") ? incomplete.getText() : entryBase.getDate());
    }

    public String getRating() {
        if(entryBase instanceof RewatchEntry){
            if(!entryBase.isFinished()){
                return incomplete.getText();
            }
        }
        
        return (entryBase.getRating() == 0.0) ? incomplete.getText() : entryBase.getRating() + "";
    }

    public EntryBase getEntry() {
        return this.entryBase;
    }
}
