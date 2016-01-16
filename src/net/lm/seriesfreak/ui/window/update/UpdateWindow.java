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
package net.lm.seriesfreak.ui.window.update;

import java.io.File;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import static net.lm.seriesfreak.Preferences.getDouble;
import static net.lm.seriesfreak.Preferences.set;
import net.lm.seriesfreak.database.implementation.DatabaseHandler;
import net.lm.seriesfreak.database.EntryDatabase;
import net.lm.seriesfreak.database.data.entries.Entry;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.database.data.entries.IllegalInputException;
import net.lm.seriesfreak.ui.language.LanguageRegistry;
import net.lm.seriesfreak.ui.language.alert.LAlert;
import net.lm.seriesfreak.ui.language.node.LButton;
import net.lm.seriesfreak.ui.window.Window;
import net.lm.seriesfreak.util.ImageHelper;

/**
 *
 *
 * @author Luke Melaia
 */
public class UpdateWindow extends Window {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private static final double WIDTH = 400, HEIGHT = 400;

    private GeneralTab generalTab;

    private EpisodesTab episodesTab;

    private EntryDatabase entryDatabase;

    private TabPane tabs;

    private StackPane root;

    private Button apply;

    private Button createFromFile;

    private Button cancel;

    private boolean edit = false;

    private final DirectoryChooser directoryChooser = new DirectoryChooser();

    public UpdateWindow(DatabaseHandler database) {
        super();

        log.trace("Initializing update window");

        this.entryDatabase = (EntryDatabase) database.getMap(EntryDatabase.class);

        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.getIcons().add(ImageHelper.getApplicationIcon());

        this.setOnCloseRequest(event -> {
            this.destroy();
        });
    }

    @Override
    public void initProperties() {
        double x = getDouble("update.window.x", Double.MIN_VALUE);
        double y = getDouble("update.window.y", Double.MIN_VALUE);

        if (x == Double.MIN_VALUE && y == Double.MIN_VALUE) {
            this.centerOnScreen();
        } else {
            this.setX(x);
            this.setY(y);
        }
    }

    @Override
    public void addComponents() {
        this.setScene(new Scene(root, WIDTH, HEIGHT));

        VBox innerLayout = new VBox(10);

        final HBox buttonRow = new HBox(10);

        Region buttonSpacing = new Region();
        HBox.setHgrow(buttonSpacing, Priority.ALWAYS);

        buttonRow.getChildren().addAll(createFromFile, buttonSpacing, apply, cancel);

        tabs.getTabs().addAll(generalTab, episodesTab);

        VBox.setVgrow(tabs, Priority.ALWAYS);

        innerLayout.getChildren().addAll(tabs, buttonRow);

        root.setPadding(new Insets(10, 10, 10, 10));
        root.getChildren().add(innerLayout);
    }

    public void add() {
        this.edit = false;
        this.setTitle(
                LanguageRegistry.getInstance().getCurrentLanguage().getProperty("window.update.add.name")
        );
        this.setHeight(generalTab.HEIGHT);
        this.setWidth(generalTab.WIDTH);
        this.showAndWait();
    }

    public void edit() {
        EntryBase entryBase = entryDatabase.getSelected();
        if (entryBase != null) {
            populateFields(entryBase);
        } else {
            new LAlert(Alert.AlertType.INFORMATION).setKey("no_entry").register().showAndWait();
            return;
        }

        this.edit = true;
        this.setTitle(
                LanguageRegistry.getInstance().getCurrentLanguage().getProperty("window.update.edit.name")
        );
        this.setHeight(generalTab.HEIGHT);
        this.setWidth(generalTab.WIDTH);
        this.showAndWait();
    }

    @Override
    public void initComponents() {
        root = new StackPane();

        tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        generalTab = (GeneralTab) new GeneralTab().register();
        episodesTab = (EpisodesTab) new EpisodesTab(this).register();

        generalTab.setOnSelectionChanged(event -> {
            this.setHeight(generalTab.HEIGHT);
            this.setWidth(generalTab.WIDTH);
        });

        episodesTab.setOnSelectionChanged(event -> {
            this.setHeight(episodesTab.HEIGHT);
            this.setWidth(episodesTab.WIDTH);
        });

        apply = new LButton().setImage("tick_blue").setTooltipKey("info.apply").register();
        createFromFile = new LButton().setImage("open").setTooltipKey("info.root").register();
        cancel = new LButton().setImage("exit").setTooltipKey("info.cancel").register();

        apply.setOnAction(event -> apply());
        cancel.setOnAction(event -> this.destroy());
        createFromFile.setOnAction(event -> {
            clearData();
            File dir = directoryChooser.showDialog(this);
            if (dir != null) {
                try {
                    this.generalTab.populateFromDirectory(dir);
                    this.episodesTab.populateFromDirectory(dir);
                } catch (IOException ex) {
                    log.error("Failed to walk file tree: " + dir.getAbsolutePath(), ex);
                    new LAlert(Alert.AlertType.ERROR).setKey("file_walking").register().showAndWait();
                }
            }
        });
    }

    @Override
    public final void destroy() {
        set("update.window.x", this.getX());
        set("update.window.y", this.getY());
        clearData();
        this.hide();
    }

    private void apply() {
        if (edit) {
            editEntry();
        } else {
            addEntry();
        }
    }

    private EntryBase getEntryFromFields() {
        try {
            Entry entry = new Entry()
                    .setName(this.generalTab.getName())
                    .setEpisode(this.generalTab.getEpisode())
                    .setEpisodes(this.generalTab.getEpisodes())
                    .setDate("")//The validate() method in the entry class takes care of this for us.
                    .setRating(this.generalTab.getRating())
                    .setRootFile(episodesTab.getRoot())
                    .setFiles(episodesTab.getFiles())
                    .setType(this.generalTab.getType())
                    .check()
                    .validate();
            return entry;
        } catch (IllegalInputException ex) {
            
            switch (ex.getException()) {
                case NO_NAME:
                    //The GeneralTab already checks the name so this case will never execute.
                    break;
                case EPISODE_TOO_LOW:
                    new LAlert(Alert.AlertType.INFORMATION).setKey("episode_too_small").register().showAndWait();
                    break;
                case EPISODE_TOO_HIGH:
                    new LAlert(Alert.AlertType.INFORMATION).setKey("episode_too_large").register().showAndWait();
                    break;
                case EPISODES_TOO_LOW:
                    new LAlert(Alert.AlertType.INFORMATION).setKey("episodes_too_large").register().showAndWait();
                    break;
                case NO_RATING:
                    new LAlert(Alert.AlertType.INFORMATION).setKey("no_rating").register().showAndWait();
                    break;
            }
        } catch (InvalidFieldDataException ex) {
            //The alert has already been given to the user so we don't need to do anything here.
        }
        return null;
    }

    private void addEntry() {
        EntryBase entry = getEntryFromFields();
        if (entry == null) {
            return;
        }

        if (entryDatabase.addEntry(entry)) {
            this.destroy();
        } else {
            new LAlert(Alert.AlertType.INFORMATION).setKey("entry_already_exists").register().showAndWait();
        }
    }

    private void editEntry() {
        EntryBase entry = getEntryFromFields();
        if (entry == null) {
            return;
        }

        EntryBase selected = entryDatabase.getSelected();
        
        selected.copyFrom(entry);
        this.entryDatabase.forceUpdate();
        this.destroy();

    }

    private void populateFields(EntryBase b) {
        this.generalTab.populateFields(b);
        this.episodesTab.populateFields(b);
    }

    private void clearData() {
        generalTab.clear();
        episodesTab.clear();
        this.tabs.getSelectionModel().select(0);
        this.setHeight(generalTab.HEIGHT);
        this.setWidth(generalTab.WIDTH);
    }
}
