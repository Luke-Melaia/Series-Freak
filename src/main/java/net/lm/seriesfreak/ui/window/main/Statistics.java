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

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.lm.seriesfreak.database.EntryListener;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.database.data.entries.RewatchEntry;
import net.lm.seriesfreak.database.data.entries.Type;
import net.lm.seriesfreak.ui.language.node.LLabel;
import net.lm.seriesfreak.ui.node.FormattedValueLabel;
import net.lm.seriesfreak.ui.window.main.MainWindow;

/**
 *
 * @author Luke Melaia
 */
class Statistics extends ScrollPane implements EntryListener {

    private VBox names = new VBox(10);

    private VBox values = new VBox(10);

    private Label seriesWatched = new LLabel().setTextKey("series_watched").register();

    private StatisticLabel seriesWatchedValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry.getType() != Type.SERIES) {
                return 0;
            }

            if (entry instanceof RewatchEntry) {
                return 0;
            }

            if (!entry.isFinished()) {
                return 0;
            }

            return 1;
        }
    };

    private Label seriesRewatched = new LLabel().setTextKey("series_rewatched").register();

    private StatisticLabel seriesRewatchedValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry.getType() != Type.SERIES) {
                return 0;
            }

            if (!(entry instanceof RewatchEntry)) {
                return 0;
            }

            if (!entry.isFinished()) {
                return 0;
            }

            return 1;
        }
    };

    private Label seriesWatching = new LLabel().setTextKey("series_watching").register();

    private StatisticLabel seriesWatchingValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry.getType() != Type.SERIES) {
                return 0;
            }

            if (entry instanceof RewatchEntry) {
                return 0;
            }

            if (entry.isFinished()) {
                return 0;
            }

            return 1;
        }
    };

    private Label seriesRewatching = new LLabel().setTextKey("series_rewatching").register();

    private StatisticLabel seriesRewatchingValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry.getType() != Type.SERIES) {
                return 0;
            }

            if (!(entry instanceof RewatchEntry)) {
                return 0;
            }

            if (entry.isFinished()) {
                return 0;
            }

            return 1;
        }
    };

    private Label totalSeries = new LLabel().setTextKey("series_total").register();

    private StatisticLabel totalSeriesValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry.getType() != Type.SERIES) {
                return 0;
            }

            return 1;
        }
    };

    private Label episodesWatched = new LLabel().setTextKey("episodes_watched").register();

    private StatisticLabel episodesWatchedValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry instanceof RewatchEntry) {
                return 0;
            }

            if (entry.getType() != Type.SERIES) {
                return 0;
            }

            return entry.getEpisode();
        }
    };

    private Label episodesRewatched = new LLabel().setTextKey("episodes_rewatched").register();

    private StatisticLabel episodesRewatchedValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (!(entry instanceof RewatchEntry)) {
                return 0;
            }

            if (entry.getType() != Type.SERIES) {
                return 0;
            }

            return entry.getEpisode();
        }
    };

    private Label episodesWatching = new LLabel().setTextKey("episodes_watching").register();

    private StatisticLabel episodesWatchingValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry instanceof RewatchEntry) {
                return 0;
            }

            if (entry.getType() != Type.SERIES) {
                return 0;
            }

            return entry.getEpisodes() - entry.getEpisode();
        }
    };

    private Label episodesRewatching = new LLabel().setTextKey("episodes_rewatching").register();

    private StatisticLabel episodesRewatchingValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (!(entry instanceof RewatchEntry)) {
                return 0;
            }

            if (entry.getType() != Type.SERIES) {
                return 0;
            }

            return entry.getEpisodes() - entry.getEpisode();
        }

    };

    private Label totalEpisodes = new LLabel().setTextKey("episodes_total").register();

    private StatisticLabel totalEpisodesValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry.getType() != Type.SERIES) {
                return 0;
            }

            return entry.getEpisodes();
        }
    };

    private Label moviesWatched = new LLabel().setTextKey("movies_watched").register();

    private StatisticLabel moviesWatchedValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry instanceof RewatchEntry) {
                return 0;
            }

            if (entry.getType() != Type.MOVIE) {
                return 0;
            }

            return entry.getEpisode();
        }
    };

    private Label moviesRewatched = new LLabel().setTextKey("movies_rewatched").register();

    private StatisticLabel moviesRewatchedValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (!(entry instanceof RewatchEntry)) {
                return 0;
            }

            if (entry.getType() != Type.MOVIE) {
                return 0;
            }

            return entry.getEpisode();
        }
    };

    private Label moviesWatching = new LLabel().setTextKey("movies_watching").register();

    private StatisticLabel moviesWatchingValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry instanceof RewatchEntry) {
                return 0;
            }

            if (entry.getType() != Type.MOVIE) {
                return 0;
            }

            return entry.getEpisodes() - entry.getEpisode();
        }
    };

    private Label moviesRewatching = new LLabel().setTextKey("movies_rewatching").register();

    private StatisticLabel moviesRewatchingValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (!(entry instanceof RewatchEntry)) {
                return 0;
            }

            if (entry.getType() != Type.MOVIE) {
                return 0;
            }

            return entry.getEpisodes() - entry.getEpisode();
        }
    };

    private Label totalMovies = new LLabel().setTextKey("movies_total").register();

    private StatisticLabel totalMoviesValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry.getType() != Type.MOVIE) {
                return 0;
            }

            return entry.getEpisodes();
        }
    };

    private Label booksRead = new LLabel().setTextKey("books_read").register();

    private StatisticLabel booksReadValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry instanceof RewatchEntry) {
                return 0;
            }

            if (entry.getType() != Type.BOOK) {
                return 0;
            }

            if(!entry.isFinished()){
                return 0;
            }
            
            return 1;
        }
    };

    private Label booksReread = new LLabel().setTextKey("books_reread").register();

    private StatisticLabel booksRereadValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (!(entry instanceof RewatchEntry)) {
                return 0;
            }

            if (entry.getType() != Type.BOOK) {
                return 0;
            }
            
            if(!entry.isFinished()){
                return 0;
            }
            
            return 1;
        }
    };

    private Label booksReading = new LLabel().setTextKey("books_reading").register();

    private StatisticLabel booksReadingValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry instanceof RewatchEntry) {
                return 0;
            }

            if (entry.getType() != Type.BOOK) {
                return 0;
            }
            
            if(entry.isFinished()){
                return 0;
            }

            return 1;
        }
    };

    private Label booksRereading = new LLabel().setTextKey("books_rereading").register();

    private StatisticLabel booksRereadingValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (!(entry instanceof RewatchEntry)) {
                return 0;
            }

            if (entry.getType() != Type.BOOK) {
                return 0;
            }
            
            if(entry.isFinished()){
                return 0;
            }

            return 1;
        }
    };

    private Label totalBooks = new LLabel().setTextKey("books_total").register();

    private StatisticLabel totalBooksValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry.getType() != Type.BOOK) {
                return 0;
            }

            return 1;
        }
    };

    private Label chaptersRead = new LLabel().setTextKey("chapters_read").register();

    private StatisticLabel chaptersReadValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry instanceof RewatchEntry) {
                return 0;
            }

            if (entry.getType() != Type.BOOK) {
                return 0;
            }

            return entry.getEpisode();
        }
    };

    private Label chaptersReread = new LLabel().setTextKey("chapters_reread").register();

    private StatisticLabel chaptersRereadValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (!(entry instanceof RewatchEntry)) {
                return 0;
            }

            if (entry.getType() != Type.BOOK) {
                return 0;
            }

            return entry.getEpisode();
        }
    };

    private Label chaptersReading = new LLabel().setTextKey("chapters_reading").register();

    private StatisticLabel chaptersReadingValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry instanceof RewatchEntry) {
                return 0;
            }

            if (entry.getType() != Type.BOOK) {
                return 0;
            }

            return entry.getEpisodes() - entry.getEpisode();
        }
    };

    private Label chaptersRereading = new LLabel().setTextKey("chapters_rereading").register();

    private StatisticLabel chaptersRereadingValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (!(entry instanceof RewatchEntry)) {
                return 0;
            }

            if (entry.getType() != Type.BOOK) {
                return 0;
            }

            return entry.getEpisodes() - entry.getEpisode();
        }
    };

    private Label totalChapters = new LLabel().setTextKey("chapters_total").register();

    private StatisticLabel totalChaptersValue = new StatisticLabel(0) {

        @Override
        public int increaseBy(EntryBase entry) {
            if (entry.getType() != Type.BOOK) {
                return 0;
            }

            return entry.getEpisodes();
        }
    };

    private MainWindow mainWindow;

    public Statistics(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;
        addItems();
        register();
    }

    private void register() {
        mainWindow.getEntryDatabase().addListener(this);
    }

    private void addItems() {

        names.getChildren().addAll(seriesWatched, seriesRewatched, seriesWatching, seriesRewatching,
                totalSeries, new Label(), episodesWatched, episodesRewatched, episodesWatching,
                episodesRewatching, totalEpisodes, new Label(), moviesWatched, moviesRewatched,
                moviesWatching, moviesRewatching, totalMovies, new Label(), booksRead,
                booksReread, booksReading, booksRereading, totalBooks, new Label(),
                chaptersRead, chaptersReread, chaptersReading,
                chaptersRereading, totalChapters
        );

        values.getChildren().addAll(seriesWatchedValue, seriesRewatchedValue, seriesWatchingValue, seriesRewatchingValue,
                totalSeriesValue, new Label(), episodesWatchedValue, episodesRewatchedValue, episodesWatchingValue,
                episodesRewatchingValue, totalEpisodesValue, new Label(), moviesWatchedValue, moviesRewatchedValue,
                moviesWatchingValue, moviesRewatchingValue, totalMoviesValue, new Label(), booksReadValue,
                booksRereadValue, booksReadingValue, booksRereadingValue, totalBooksValue, new Label(),
                chaptersReadValue, chaptersRereadValue, chaptersReadingValue,
                chaptersRereadingValue, totalChaptersValue
        );

        HBox list = new HBox(15);
        list.getChildren().addAll(names, values);

        this.setContent(list);
    }

    @Override
    public void onChanged(EntryBase[] paramater) {
        seriesWatchedValue.reset();
        seriesRewatchedValue.reset();
        seriesWatchingValue.reset();
        seriesRewatchingValue.reset();
        totalSeriesValue.reset();
        episodesWatchedValue.reset();
        episodesRewatchedValue.reset();
        episodesWatchingValue.reset();
        episodesRewatchingValue.reset();
        totalEpisodesValue.reset();
        moviesWatchedValue.reset();
        moviesRewatchedValue.reset();
        moviesWatchingValue.reset();
        moviesRewatchingValue.reset();
        totalMoviesValue.reset();
        booksReadValue.reset();
        booksRereadValue.reset();
        booksReadingValue.reset();
        booksRereadingValue.reset();
        totalBooksValue.reset();
        chaptersReadValue.reset();
        chaptersRereadValue.reset();
        chaptersReadingValue.reset();
        chaptersRereadingValue.reset();
        totalChaptersValue.reset();

        for (EntryBase base : paramater) {
            seriesWatchedValue.update(base);
            seriesRewatchedValue.update(base);
            seriesWatchingValue.update(base);
            seriesRewatchingValue.update(base);
            totalSeriesValue.update(base);
            episodesWatchedValue.update(base);
            episodesRewatchedValue.update(base);
            episodesWatchingValue.update(base);
            episodesRewatchingValue.update(base);
            totalEpisodesValue.update(base);
            moviesWatchedValue.update(base);
            moviesRewatchedValue.update(base);
            moviesWatchingValue.update(base);
            moviesRewatchingValue.update(base);
            totalMoviesValue.update(base);
            booksReadValue.update(base);
            booksRereadValue.update(base);
            booksReadingValue.update(base);
            booksRereadingValue.update(base);
            totalBooksValue.update(base);
            chaptersReadValue.update(base);
            chaptersRereadValue.update(base);
            chaptersReadingValue.update(base);
            chaptersRereadingValue.update(base);
            totalChaptersValue.update(base);
        }
    }

    private abstract class StatisticLabel extends FormattedValueLabel {

        public StatisticLabel(int value) {
            super(value);
        }

        public void reset() {
            this.setValue(0);
        }

        public void update(EntryBase entry) {
            this.setValue(this.getValue() + increaseBy(entry));
        }

        public abstract int increaseBy(EntryBase entry);
    }
}
