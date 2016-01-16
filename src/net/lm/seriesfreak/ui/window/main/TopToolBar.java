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

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import net.lm.seriesfreak.database.Printer;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.database.data.entries.RewatchEntry;
import net.lm.seriesfreak.ui.language.alert.LAlert;
import net.lm.seriesfreak.ui.language.alert.LChoiceDialog;
import net.lm.seriesfreak.ui.language.node.LButton;
import net.lm.seriesfreak.ui.language.node.LTextItem;
import net.lm.seriesfreak.ui.node.SearchBar;
import net.lm.seriesfreak.util.RandomUtils;
import net.lm.seriesfreak.util.RatingHelper;

/**
 *
 * @author Luke Melaia
 */
class TopToolBar extends ToolBar {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private Button add = new LButton().setImage("plus").setTooltipKey("add").register();

    public Button edit = new LButton().setImage(("edit")).setTooltipKey("edit").register();

    public Button remove = new LButton().setImage(("cross")).setTooltipKey("remove").register();

    public Button complete = new LButton().setImage(("tick_green")).setTooltipKey("complete").register();

    public Button increase = new LButton().setImage(("up_pointer")).setTooltipKey("increase").register();

    public Button decrease = new LButton().setImage(("down_pointer")).setTooltipKey("decrease").register();

    public Button favorite = new LButton().setImage(("star")).setTooltipKey("favorite").register();

    public Button rewatch = new LButton().setImage(("copy")).setTooltipKey("rewatch").register();

    public Button drop = new LButton().setImage(("trash_can")).setTooltipKey("drop").register();

    private Button print = new LButton().setImage(("printer")).setTooltipKey("print").register();

    private String text;

    private SearchBar searchBar = new SearchBar((String value) -> {
        this.text = value;
        this.mainWindow.getEntryDatabase().forceUpdate();
    });

    private MainWindow mainWindow;

    public TopToolBar(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;
        initItems();
        addItems();
    }

    public Button getEdit() {
        return edit;
    }

    public Button getRemove() {
        return remove;
    }

    public Button getComplete() {
        return complete;
    }

    public Button getIncrease() {
        return increase;
    }

    public Button getDecrease() {
        return decrease;
    }

    public Button getFavorite() {
        return favorite;
    }

    public Button getRewatch() {
        return rewatch;
    }

    public Button getDrop() {
        return drop;
    }

    public String getSearchText() {
        if (this.text == null) {
            return "";
        }
        return this.text;
    }

    private void initItems() {
        add.setOnAction(addEvent);
        edit.setOnAction(editEvent);
        remove.setOnAction(removeEvent);
        complete.setOnAction(completeEvent);
        increase.setOnAction(increaseEvent);
        decrease.setOnAction(decreaseEvent);
        favorite.setOnAction(faovriteEvent);
        rewatch.setOnAction(rewatchEvent);
        drop.setOnAction(dropEvent);
        print.setOnAction(printEvent);
    }

    private void addItems() {

        this.getItems().addAll(add, edit, remove, new Separator(), complete, increase,
                decrease, favorite, new Separator(), rewatch, drop, new Separator(), print
        );

        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);

        searchBar.setPadding(new Insets(1.5, 0, 0, 0));

        this.getItems().addAll(r, searchBar);
    }

    private final EventHandler<ActionEvent> addEvent = (ActionEvent event) -> {
        mainWindow.getUpdateWindow().get().add();
    };

    private final EventHandler<ActionEvent> editEvent = (ActionEvent event) -> {
        if (mainWindow.getEntryDatabase().getSelected() == null) {
            showSelectedAlert();
            return;
        }

        mainWindow.getUpdateWindow().get().edit();
    };

    private final EventHandler<ActionEvent> removeEvent = (ActionEvent event) -> {
        if (mainWindow.getEntryDatabase().getSelected() == null) {
            showSelectedAlert();
            return;
        }

        Optional<ButtonType> result = new LAlert(Alert.AlertType.CONFIRMATION).setKey("remove").register().showAndWait();

        if (!result.isPresent() || result.get() != ButtonType.OK) {
            return;
        }

        EntryBase remove1 = mainWindow.getEntryDatabase().getSelected();

        for (EntryBase entry : mainWindow.getEntryDatabase().getEntries()) {
            if (entry instanceof RewatchEntry) {
                if (((RewatchEntry) entry).getOriginalEntry().equals(remove1)) {
                    mainWindow.getEntryDatabase().removeEntry(entry);
                }
            }
        }
        mainWindow.getEntryDatabase().setSelected(null);
        mainWindow.getEntryDatabase().removeEntry(remove1);
    };

    private final EventHandler<ActionEvent> completeEvent = (ActionEvent event) -> {
        if (mainWindow.getEntryDatabase().getSelected() == null) {
            showSelectedAlert();
            return;
        }

        EntryBase selected = this.mainWindow.getEntryDatabase().getSelected();

        if (selected.isFinished()) {
            return;
        }

        Optional<ButtonType> result = new LAlert(Alert.AlertType.CONFIRMATION).setKey("complete").register().showAndWait();

        if (!result.isPresent() || result.get() != ButtonType.OK) {
            return;
        }

        if (selected instanceof RewatchEntry) {
            selected.finish(0);
            selected.validate();
        } else {
            if (selected.getEpisodes() == -1 && selected.getEpisode() == 0) {
                new LAlert(Alert.AlertType.INFORMATION).setKey("cant_finish").register().showAndWait();
                return;
            }
            Optional<Double> optionalRatings = new LChoiceDialog<>(8.0, RatingHelper.RATINGS_USABLE_AS_COLLECTION).setKey("ratings").register().showAndWait();

            optionalRatings.ifPresent(OptionalRating -> {
                selected.finish(OptionalRating);
            });
        }
        mainWindow.getEntryDatabase().forceUpdate();
    };

    private final EventHandler<ActionEvent> increaseEvent = (ActionEvent event) -> {
        if (mainWindow.getEntryDatabase().getSelected() == null) {
            showSelectedAlert();
            return;
        }

        EntryBase selected = this.mainWindow.getEntryDatabase().getSelected();

        if (selected.isFinished()) {
            return;
        }

        if (selected.getEpisode() == selected.getEpisodes() - 1) {
            if (!(selected instanceof RewatchEntry)) {
                Optional<Double> optionalRatings = new LChoiceDialog<>(8.0, RatingHelper.RATINGS_USABLE_AS_COLLECTION).setKey("ratings").register().showAndWait();

                optionalRatings.ifPresent(OptionalRating -> {
                    selected.finish(OptionalRating);
                });
            } else {
                selected.finish(0);
            }
        } else {
            selected.setEpisode(selected.getEpisode() + 1);
        }
        mainWindow.getEntryDatabase().forceUpdate();
    };

    private final EventHandler<ActionEvent> decreaseEvent = (ActionEvent event) -> {
        if (mainWindow.getEntryDatabase().getSelected() == null) {
            showSelectedAlert();
            return;
        }

        EntryBase selected = this.mainWindow.getEntryDatabase().getSelected();

        if (selected.isFinished()) {
            return;
        }

        if (selected.getEpisode() != 0) {
            selected.setEpisode(selected.getEpisode() - 1);
        }

        mainWindow.getEntryDatabase().forceUpdate();
    };

    private final EventHandler<ActionEvent> faovriteEvent = (ActionEvent event) -> {
        if (mainWindow.getEntryDatabase().getSelected() == null) {
            showSelectedAlert();
            return;
        }

        Optional<ButtonType> result = new LAlert(Alert.AlertType.CONFIRMATION).setKey("favorite").register().showAndWait();

        if (!result.isPresent() || result.get() != ButtonType.OK) {
            return;
        }

        if (mainWindow.getEntryDatabase().getSelected().isFavorite()) {
            mainWindow.getEntryDatabase().getSelected().setFavorite(false);
        } else {
            mainWindow.getEntryDatabase().getSelected().setFavorite(true);
        }
        mainWindow.getEntryDatabase().forceUpdate();
    };

    
    
    private final EventHandler<ActionEvent> rewatchEvent = (ActionEvent event) -> {
        if (mainWindow.getEntryDatabase().getSelected() == null) {
            showSelectedAlert();
            return;
        }

        Optional<ButtonType> result = new LAlert(Alert.AlertType.CONFIRMATION).setKey("rewatch").register().showAndWait();

        if (!result.isPresent() || result.get() != ButtonType.OK) {
            return;
        }

        EntryBase selected = mainWindow.getEntryDatabase().getSelected();
        RewatchEntry rewatch1 = new RewatchEntry();

        if (selected instanceof RewatchEntry) {
            selected = ((RewatchEntry) selected).getOriginalEntry();
        }

        int number = 1;

        for (EntryBase base : mainWindow.getEntryDatabase().getEntries()) {

            if (base instanceof RewatchEntry) {
                if (((RewatchEntry) base).getOriginalEntry().equals(selected)) {
                    number++;
                }
            }
        }

        rewatch1.setEpisode(0);

        rewatch1.setOriginalEntry(selected);

        rewatch1.setName(
                selected.getName()
                + " "
                + RandomUtils.getOrdinalIndicator(number)
                + " "
                + new LTextItem("rewatch", null).register().getText()
        );

        mainWindow.getEntryDatabase().addEntry(rewatch1);
    };

    private final EventHandler<ActionEvent> dropEvent = (ActionEvent event) -> {
        if (mainWindow.getEntryDatabase().getSelected() == null) {
            showSelectedAlert();
            return;
        }

        Optional<ButtonType> result = new LAlert(Alert.AlertType.CONFIRMATION).setKey("drop").register().showAndWait();

        if (!result.isPresent() || result.get() != ButtonType.OK) {
            return;
        }

        if (mainWindow.getEntryDatabase().getSelected().isDropped()) {
            mainWindow.getEntryDatabase().getSelected().setDropped(false);
        } else {
            mainWindow.getEntryDatabase().getSelected().setDropped(true);
        }
        mainWindow.getEntryDatabase().forceUpdate();
    };

    private final EventHandler<ActionEvent> printEvent = (ActionEvent event) -> {
        EntryBase[] entries = new EntryBase[this.mainWindow.getTableImpl().getItems().size()];

        for (int i = 0; i < entries.length; i++) {
            entries[i] = this.mainWindow.getTableImpl().getItems().get(i).getEntry();
        }

        Printer printer = new Printer(entries, new File(System.getProperty("user.dir") + "/Entries.html"), System.getProperty("user.name"));

        try {
            printer.print();
        } catch (IOException ex) {
            log.error("Failed to print entries", ex);
            new LAlert(Alert.AlertType.ERROR).setKey("cant_print").register().showAndWait();
        }
    };

    private void showSelectedAlert() {
        new LAlert(Alert.AlertType.INFORMATION).setKey("no_selection").register().showAndWait();
    }

}
