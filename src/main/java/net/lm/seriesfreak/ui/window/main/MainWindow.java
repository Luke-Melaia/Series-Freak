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

import java.io.IOException;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import net.lm.seriesfreak.Application;
import net.lm.seriesfreak.ExitCode;
import static net.lm.seriesfreak.Preferences.getBoolean;
import static net.lm.seriesfreak.Preferences.getDouble;
import static net.lm.seriesfreak.Preferences.set;
import net.lm.seriesfreak.database.CategoryDatabase;
import net.lm.seriesfreak.database.EntryDatabase;
import net.lm.seriesfreak.database.PreferencesDatabase;
import net.lm.seriesfreak.database.implementation.DatabaseHandler;
import net.lm.seriesfreak.database.implementation.InaccessibleFileException;
import net.lm.seriesfreak.ui.language.alert.LAlert;
import net.lm.seriesfreak.ui.window.update.UpdateWindow;
import net.lm.seriesfreak.ui.window.Window;
import net.lm.seriesfreak.ui.window.WindowLoader;
import net.lm.seriesfreak.util.ImageHelper;

/**
 *
 *
 * @author Luke Melaia
 */
public final class MainWindow extends Window {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private static final double WIDTH = 775, HEIGHT = 480;

    private static final double MIN_WIDTH = 610, MIN_HEIGHT = 300;

    private static Alert iSaving = new LAlert(Alert.AlertType.WARNING).setKey("unknown_io_saving").register();
    
    private static Alert uSaving = new LAlert(Alert.AlertType.WARNING).setKey("unknown_io_saving").register();
    
    private static Alert notLoaded = new LAlert(Alert.AlertType.WARNING).setKey("not_loaded").register();
    
    private StackPane stage;

    private MenuBar menuBar;

    private TopToolBar topToolBar;

    private BottomToolBar bottomToolBar;

    private Table table;

    private Statistics stats;

    private Categories categories;

    private WindowLoader<UpdateWindow> updateWindow;

    private DatabaseHandler database;

    private EntryDatabase entryDatabase;

    private CategoryDatabase categoryDatabase;

    private PreferencesDatabase preferencesDatabase;

    public MainWindow(WindowLoader<UpdateWindow> updateWin, DatabaseHandler database) {
        super();
        log.trace("Initializing main window");

        this.updateWindow = updateWin;
        this.database = database;
        this.entryDatabase = (EntryDatabase) database.getMap(EntryDatabase.class);
        this.preferencesDatabase = (PreferencesDatabase) database.getMap(PreferencesDatabase.class);
        this.categoryDatabase = (CategoryDatabase) database.getMap(CategoryDatabase.class);

        this.setMinWidth(MIN_WIDTH);
        this.setMinHeight(MIN_HEIGHT);
        this.getIcons().add(ImageHelper.getApplicationIcon());

        this.setOnCloseRequest(event -> {
            this.destroy();
        });
    }

    @Override
    public void initProperties() {
        this.setMaximized(getBoolean("main.window.maximized", false));
        this.setWidth(getDouble("main.window.width", WIDTH));
        this.setHeight(getDouble("main.window.height", HEIGHT));

        double x = getDouble("main.window.x", Integer.MIN_VALUE);
        double y = getDouble("main.window.y", Integer.MIN_VALUE);

        if (x == Integer.MIN_VALUE && y == Integer.MIN_VALUE) {
            this.centerOnScreen();
        } else {
            this.setX(x);
            this.setY(y);
        }
    }

    @Override
    public void initComponents() {
        stage = new StackPane();
        menuBar = new MenuBar(this);
        topToolBar = new TopToolBar(this);
        bottomToolBar = new BottomToolBar(this);
        table = new Table(this);
        stats = new Statistics(this);
        categories = new Categories(this);
    }

    @Override
    public void addComponents() {
        this.setScene(new Scene(stage, WIDTH, HEIGHT));
        this.getScene().getStylesheets().add("css/MainWindow.css");

        final VBox outerLayout = new VBox(0);
        VBox.setVgrow(outerLayout, Priority.ALWAYS);

        final HBox innerLayout = new HBox(10);
        VBox.setVgrow(innerLayout, Priority.ALWAYS);

        outerLayout.getChildren().add(menuBar);
        outerLayout.getChildren().add(topToolBar);
        outerLayout.getChildren().add(innerLayout);
        outerLayout.getChildren().add(bottomToolBar);

        innerLayout.getChildren().add(table);
        HBox.setHgrow(table, Priority.ALWAYS);

        SplitPane sideComps = new SplitPane();
        sideComps.setMaxWidth(187);
        sideComps.setOrientation(Orientation.VERTICAL);

        sideComps.getItems().addAll(categories, stats);

        innerLayout.getChildren().add(sideComps);

        stage.getChildren().add(outerLayout);
    }

    @Override
    public final void destroy() {
        set("main.window.maximized", this.isMaximized());
        set("main.window.x", this.getX());
        set("main.window.y", this.getY());

        if (this.isMaximized()) {
            this.setMaximized(false);
        }

        set("main.window.width", this.getWidth());
        set("main.window.height", this.getHeight());

        if (this.getDatabase().getCurrectFile() != null) {
            try {
                this.database.save();
                set("save_file", getDatabase().getCurrectFile().getAbsolutePath());
            } catch (InaccessibleFileException ex) {
                iSaving.showAndWait();
            } catch (IOException ex) {
                uSaving.showAndWait();
            } catch (IllegalStateException ex) {
                notLoaded.showAndWait();
            }
        }
        this.hide();
        Application.exit(ExitCode.SUCCESS);
    }

    protected MenuBar getMenuBarImpl() {
        return menuBar;
    }

    protected TopToolBar getTopToolBar() {
        return topToolBar;
    }

    protected BottomToolBar getBottomToolBar() {
        return bottomToolBar;
    }

    protected Table getTableImpl() {
        return table;
    }

    protected Statistics getStatisticsImpl() {
        return stats;
    }

    protected Categories getCategoriesImpl() {
        return categories;
    }

    protected WindowLoader<UpdateWindow> getUpdateWindow() {
        return updateWindow;
    }

    protected DatabaseHandler getDatabase() {
        return database;
    }

    protected EntryDatabase getEntryDatabase() {
        return entryDatabase;
    }

    protected PreferencesDatabase getPreferencesDatabase() {
        return preferencesDatabase;
    }

    public CategoryDatabase getCategoryDatabase() {
        return categoryDatabase;
    }

}
