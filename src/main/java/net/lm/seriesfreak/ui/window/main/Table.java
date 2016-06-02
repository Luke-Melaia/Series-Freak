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

import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.lm.seriesfreak.database.PreferencesDatabase;
import net.lm.seriesfreak.database.data.categories.UserCategory;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.database.data.entries.OpenException;
import net.lm.seriesfreak.database.data.entries.TableEntry;
import net.lm.seriesfreak.database.data.properties.StringArrayProperty;
import net.lm.seriesfreak.ui.language.alert.LAlert;
import net.lm.seriesfreak.ui.language.alert.LChoiceDialog;
import net.lm.seriesfreak.ui.language.node.LMenu;
import net.lm.seriesfreak.ui.language.node.LMenuItem;
import net.lm.seriesfreak.ui.language.node.LTableColumn;
import net.lm.seriesfreak.ui.node.TextualProgressBarTableCell;
import net.lm.seriesfreak.util.Comparators;
import net.lm.seriesfreak.util.RatingHelper;

/**
 * @author Luke Melaia
 */
class Table extends TableView<TableEntry> {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private ChoiceDialog<Double> optionalRatingsDialog = new LChoiceDialog<>(8.0, RatingHelper.RATINGS_USABLE_AS_COLLECTION).setKey("ratings").register();

    private Alert noFilesAlert = new LAlert(Alert.AlertType.INFORMATION).setKey("no_files").register();

    private Alert missingFilesAlert = new LAlert(Alert.AlertType.INFORMATION).setKey("missing_files").register();

    private Alert unknownAlert = new LAlert(Alert.AlertType.ERROR).setKey("unknown_reason").register();

    private TableColumn name = (TableColumn) new LTableColumn().setTextKey("name").register();

    private TableColumn episode = (TableColumn) new LTableColumn().setTextKey("episode").register();

    private TableColumn type = (TableColumn) new LTableColumn().setTextKey("type").register();

    private TableColumn date = (TableColumn) new LTableColumn().setTextKey("date").register();

    private TableColumn rating = (TableColumn) new LTableColumn().setTextKey("rating").register();

    private ContextMenu menu = new ContextMenu();

    private LMenu categoires = new LMenu().setTextKey("categories").setImage("flag_green").register();

    private LMenu addToCategory = new LMenu().setTextKey("category_add").setImage("category_add").register();

    private LMenu removeFromCategory = new LMenu().setTextKey("category_remove").setImage("category_remove").register();

    private MainWindow mainWindow;

    public Table(MainWindow mainWindow) {
        super();

        this.mainWindow = mainWindow;

        this.setEditable(false);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        addItems();
        initMenu();
        initColumns();
        initTable();
        register();
    }

    private void initMenu() {
        LMenuItem edit = new LMenuItem().setTextKey("edit").setImage("edit").register();
        edit.setOnAction(event -> this.mainWindow.getTopToolBar().getEdit().fire());
        this.menu.getItems().add(edit);

        LMenuItem remove = new LMenuItem().setTextKey("remove").setImage("cross").register();
        remove.setOnAction(event -> this.mainWindow.getTopToolBar().getRemove().fire());
        this.menu.getItems().add(remove);

        LMenuItem complete = new LMenuItem().setTextKey("complete").setImage("tick_green").register();
        complete.setOnAction(event -> this.mainWindow.getTopToolBar().getComplete().fire());
        this.menu.getItems().add(complete);

        LMenuItem increase = new LMenuItem().setTextKey("increase").setImage("up_pointer").register();
        increase.setOnAction(event -> this.mainWindow.getTopToolBar().getIncrease().fire());
        this.menu.getItems().add(increase);

        LMenuItem decrease = new LMenuItem().setTextKey("decrease").setImage("down_pointer").register();
        decrease.setOnAction(event -> this.mainWindow.getTopToolBar().getDecrease().fire());
        this.menu.getItems().add(decrease);

        LMenuItem favorite = new LMenuItem().setTextKey("favorite").setImage("star").register();
        favorite.setOnAction(event -> this.mainWindow.getTopToolBar().getFavorite().fire());
        this.menu.getItems().add(favorite);

        LMenuItem rewatch = new LMenuItem().setTextKey("rewatch").setImage("copy").register();
        rewatch.setOnAction(event -> this.mainWindow.getTopToolBar().getRewatch().fire());
        this.menu.getItems().add(rewatch);

        LMenuItem drop = new LMenuItem().setTextKey("drop").setImage("trash_can").register();
        drop.setOnAction(event -> this.mainWindow.getTopToolBar().getDrop().fire());
        this.menu.getItems().add(drop);

        this.categoires.getItems().addAll(addToCategory, removeFromCategory);
        this.menu.getItems().add(categoires);
    }

    private void initColumns() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setComparator(Comparators.ALPHANUM_COMPARATOR);
        name.setSortable(true);

        episode.setCellValueFactory(new PropertyValueFactory<>("progress"));
        episode.setCellFactory(TextualProgressBarTableCell.forTableColumn());
        episode.setSortable(true);
        episode.setComparator(Comparators.PROGRESS_SORT_COMPARATOR);

        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        type.setSortable(true);

        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        date.setSortable(true);
        date.setComparator(Comparators.DATE_SORT_COMPARATOR);

        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        rating.setSortable(true);
        rating.setComparator(Comparators.RATING_SORT_COMPARATOR);
    }

    private void initTable() {
        this.setRowFactory(param -> {
            final TableRow row = new TableRow();

            row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(menu));

            return row;
        });

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            onClick(event);

            if (event.isSecondaryButtonDown()) {
                //onContextMenuRequested(event) doesn't work for some reason so...
                this.addToCategory.getItems().clear();
                this.removeFromCategory.getItems().clear();

                EntryBase selected = this.mainWindow.getEntryDatabase().getSelected();

                for (UserCategory category : this.mainWindow.getCategoriesImpl().getAddable(selected)) {
                    MenuItem item = new MenuItem(category.getValue());
                    item.setOnAction(event1 -> {
                        category.addEntry(selected);
                    });
                    addToCategory.getItems().add(item);
                }

                for (UserCategory category : this.mainWindow.getCategoriesImpl().getRamovable(selected)) {
                    MenuItem item = new MenuItem(category.getValue());
                    item.setOnAction(event1 -> {
                        category.removeEntry(selected);
                        this.mainWindow.getEntryDatabase().forceUpdate();
                    });
                    removeFromCategory.getItems().add(item);
                }
            }
        });

        this.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                case DOWN:
                    select();
                    break;
            }
        });
    }

    private void onClick(MouseEvent event) {
        select();

        if (event.getClickCount() >= 2) {
            try {
                this.mainWindow.getEntryDatabase().getSelected().open();
            } catch (OpenException ex) {
                switch (ex.getReason()) {
                    case NO_FILES:
                        noFilesAlert.showAndWait();
                        break;

                    case MISSING_FILES:
                        missingFilesAlert.showAndWait();
                        break;

                    case UNKNOWN:
                        unknownAlert.showAndWait();
                        log.error("Failed to open episode file", ex);
                        break;

                    case RATING_REQUIRED:
                        Optional<Double> optionalRatings = optionalRatingsDialog.showAndWait();

                        optionalRatings.ifPresent(OptionalRating -> {
                            this.mainWindow.getEntryDatabase().getSelected().finish(OptionalRating).validate();
                        });

                        break;
                }
            } finally {
                this.refresh();
            }
        }
    }

    private void select() {
        int select = this.getSelectionModel().getSelectedIndex();
        if (select < 0) {
            return;
        }
        mainWindow.getEntryDatabase().setSelected(this.getItems().get(select).getEntry());
        this.mainWindow.getBottomToolBar().setEntry(this.getItems().get(select).getEntry());
    }

    private void register() {
        mainWindow.getEntryDatabase().addListener((EntryBase[] entries) -> {
            this.getItems().clear();

            for (EntryBase entry : mainWindow.getEntryDatabase().getEntries()) {
                if (this.mainWindow.getCategoriesImpl().isValid(entry)) {
                    if (this.mainWindow.getTopToolBar().getSearchText().equals("")) {
                        this.getItems().add(new TableEntry(entry));

                    } else {
                        if (entry.getName().toLowerCase().contains(this.mainWindow.getTopToolBar().getSearchText().toLowerCase())) {
                            this.getItems().add(new TableEntry(entry));

                        }
                    }
                }
            }
            sort();
        });

        mainWindow.getPreferencesDatabase().addStateListener((PreferencesDatabase.State state) -> {
            switch (state) {
                case SERIALIZING:

                    saveSortOrder();
                    break;
                case DESERIALIZING:

                    loadSortOrder();
                    break;
            }
        });
    }

    private void loadSortOrder() {
        StringArrayProperty sortOrder = (StringArrayProperty) mainWindow.getPreferencesDatabase().getProperty("Sort Order");
        StringArrayProperty sortType = (StringArrayProperty) mainWindow.getPreferencesDatabase().getProperty("Sort Type");

        if (sortOrder == null) {
            this.getSortOrder().addAll(rating, name);
            this.sort();
            return;
        }

        if (sortType == null) {
            this.name.setSortType(TableColumn.SortType.ASCENDING);
            this.rating.setSortType(TableColumn.SortType.ASCENDING);
            return;
        }

        this.getSortOrder().clear();

        int index = 0;

        for (String columnName : sortOrder.get()) {
            for (TableColumn<TableEntry, ?> tableColumn : this.getColumns()) {
                if (tableColumn.toString().equals(columnName)) {
                    tableColumn.setSortType(TableColumn.SortType.valueOf(sortType.get().get(index)));
                    index++;
                    this.getSortOrder().add(tableColumn);
                }
            }
        }
    }

    private void saveSortOrder() {
        StringArrayProperty sortOrder = (StringArrayProperty) mainWindow.getPreferencesDatabase().getProperty("Sort Order");
        StringArrayProperty sortType = (StringArrayProperty) mainWindow.getPreferencesDatabase().getProperty("Sort Type");

        if (sortOrder == null) {
            sortOrder = new StringArrayProperty();
            mainWindow.getPreferencesDatabase().addProperty("Sort Order", sortOrder);
        }

        if (sortType == null) {
            sortType = new StringArrayProperty();
            mainWindow.getPreferencesDatabase().addProperty("Sort Type", sortType);
        }

        sortOrder.get().clear();
        sortType.get().clear();

        for (TableColumn<TableEntry, ?> column : this.getSortOrder()) {
            sortOrder.get().add(column.toString());
            sortType.get().add(column.getSortType().name());
        }
    }

    private void addItems() {
        this.getColumns().addAll(name, episode, type, date, rating);
    }

}
