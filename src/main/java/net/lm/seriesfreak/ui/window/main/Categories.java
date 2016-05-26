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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.lm.seriesfreak.database.data.categories.Category;
import net.lm.seriesfreak.database.data.categories.UserCategory;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.database.data.entries.RewatchEntry;
import net.lm.seriesfreak.database.data.entries.Type;
import net.lm.seriesfreak.ui.language.LanguageRegistry;
import net.lm.seriesfreak.ui.language.alert.LAlert;
import net.lm.seriesfreak.ui.language.alert.LTextInputDialog;
import net.lm.seriesfreak.ui.language.node.LButton;
import net.lm.seriesfreak.ui.language.node.LMenuItem;
import net.lm.seriesfreak.ui.language.node.LTextItem;
import net.lm.seriesfreak.ui.language.node.LTreeItem;
import net.lm.seriesfreak.util.ChangeListener;

/**
 *
 * @author Luke Melaia
 */
class Categories extends VBox implements ChangeListener<UserCategory[]> {

    private static Alert emptyName = new LAlert(Alert.AlertType.INFORMATION).setKey("category_name_empty").register();
    
    private static Alert nameExists = new LAlert(Alert.AlertType.INFORMATION).setKey("category_name_exists").register();
    
    private static Alert cantEdit = new LAlert(Alert.AlertType.INFORMATION).setKey("cant_edit_cat").register();
    
    private static Alert cantRemove = new LAlert(Alert.AlertType.INFORMATION).setKey("cant_remove_cat").register();
    
    private static Alert removeCat = new LAlert(Alert.AlertType.CONFIRMATION).setKey("category_remove").register();
    
    
    
    private TreeView<String> categoriesView = new TreeView<>();

    private ToolBar toolBar = new ToolBar();

    private Button add = new LButton().setImage("plus").register();

    private Button edit = new LButton().setImage("edit").register();

    private Button remove = new LButton().setImage("cross").register();

    private LTreeItem categories = new LTreeItem().setTextKey("categories").setImage("flag_black").register();

    private LTreeItem defaultCategories = new LTreeItem().setTextKey("categories_default").setImage("flag_black").register();

    private LTreeItem userCategories = new LTreeItem().setTextKey("categories_user").setImage("flag_black").register();

    private CategoryUpdateWindow updateWindow = new CategoryUpdateWindow();

    private ContextMenu menu = new ContextMenu();

    private MenuItem addItem = new LMenuItem().setTextKey("add").setImage("plus").register();

    private MenuItem editItem = new LMenuItem().setTextKey("edit").setImage("edit").register();

    private MenuItem removeItem = new LMenuItem().setTextKey("remove").setImage("cross").register();

    private MainWindow mainWindow;

    public Categories(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;
        init();
        addItems();
    }

    @Override
    public void onChanged(UserCategory[] paramater) {
        this.userCategories.getChildren().clear();
        this.userCategories.getChildren().addAll(Arrays.asList(paramater));
        this.categoriesView.refresh();
    }
    
    public boolean isValid(EntryBase entry){
        try {
            return ((Category) this.categoriesView.getSelectionModel().getSelectedItem()).isAllowed(entry);
        } catch (NullPointerException | ClassCastException e) {
            this.categoriesView.getSelectionModel().select(all);
            return this.all.isAllowed(entry);
        }
    }
    
    public UserCategory[] getAddable(EntryBase base){
        if(base instanceof RewatchEntry){
            base = ((RewatchEntry)base).getOriginalEntry();
        }
        
        List<UserCategory> list = new ArrayList<>();
        
        for(TreeItem<String> item : this.userCategories.getChildren()){
            UserCategory category = (UserCategory)item;
            
            if(!category.containsEntry(base)){
                list.add(category);
            }
        }
        
        return list.toArray(new UserCategory[list.size()]);
    }
    
    public UserCategory[] getRamovable(EntryBase base){
        if(base instanceof RewatchEntry){
            base = ((RewatchEntry)base).getOriginalEntry();
        }
        
        List<UserCategory> list = new ArrayList<>();
        
        for(TreeItem<String> item : this.userCategories.getChildren()){
            UserCategory category = (UserCategory)item;
            
            if(category.containsEntry(base)){
                list.add(category);
            }
        }
        
        return list.toArray(new UserCategory[list.size()]);
    }
    
    private void init() {
        this.categoriesView.setRoot(categories);

        categories.setExpanded(true);
        defaultCategories.setExpanded(true);
        userCategories.setExpanded(true);

        this.categoriesView.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            this.notifyChange();
        });

        this.categoriesView.addEventHandler(KeyEvent.ANY, event -> {
            switch (event.getCode()) {
                case UP:
                case DOWN:
                case KP_UP:
                case KP_DOWN:
                    notifyChange();
            }
        });

        this.mainWindow.getCategoryDatabase().addListener(this);

        this.add.setOnAction(event -> {
            addCategory();
        });

        this.edit.setOnAction(event -> {
            editCategory();
        });

        this.remove.setOnAction(event -> {
            removeCategory();
        });

        this.addItem.setOnAction(event -> addCategory());
        this.editItem.setOnAction(event -> editCategory());
        this.removeItem.setOnAction(event -> removeCategory());

        this.categoriesView.setContextMenu(menu);
    }

    private void addCategory() {
        Optional<String> oName = this.updateWindow.add();

        if (!oName.isPresent()) {
            return;
        }

        String name = oName.get();

        if (name.equals("")) {
            emptyName.showAndWait();
            addCategory();
            return;
        }

        UserCategory category = new UserCategory(name, true);

        boolean added = mainWindow.getCategoryDatabase().addCategory(category);

        if (!added) {
            nameExists.showAndWait();
            addCategory();
        }
    }

    private void editCategory() {
        TreeItem<String> selectedCat = this.categoriesView.getSelectionModel().getSelectedItem();

        if (!(selectedCat instanceof UserCategory)) {
            cantEdit.showAndWait();
            return;
        }

        UserCategory selected = (UserCategory) selectedCat;

        Optional<String> oName = this.updateWindow.edit(selected.getValue());

        if (!oName.isPresent()) {
            return;
        }

        String name = oName.get();

        if (name.equals("")) {
            emptyName.showAndWait();
            editCategory();
            return;
        }

        if (selected.getValue().equals(name)) {
            return;
        }

        UserCategory category = new UserCategory(name, true);

        if (mainWindow.getCategoryDatabase().contains(category)) {
            nameExists.showAndWait();
            editCategory();
            return;
        }

        selected.setValue(name);
    }

    private void removeCategory() {
        Category selectedCat = (Category) this.categoriesView.getSelectionModel().getSelectedItem();

        if (!(selectedCat instanceof UserCategory)) {
            cantRemove.showAndWait();
            return;
        }

        Optional<ButtonType> optional = removeCat.showAndWait();

        if (!optional.isPresent() || optional.get() != ButtonType.OK) {
            return;
        }

        UserCategory selected = (UserCategory) selectedCat;
        this.mainWindow.getCategoryDatabase().removeEntry(selected);
        this.notifyChange();
    }

    private void notifyChange() {
        this.mainWindow.getEntryDatabase().forceUpdate();
    }

    private void addItems() {
        HBox buttons = new HBox(20, add, edit, remove);
        buttons.setPadding(new Insets(0, 0, 0, 15));
        this.toolBar.getItems().addAll(buttons);

        categories.getChildren().addAll(defaultCategories, userCategories);

        defaultCategories.getChildren().addAll(favourites, all, watching, finished, series, books, movies, originals, rewatches, rated_10,
                rated_9, rated_8, rated_7, rated_6, rated_5, rated_4, rated_3, rated_2, rated_1, dropped
        );

        this.menu.getItems().addAll(addItem, editItem, removeItem);

        this.getChildren().addAll(toolBar, categoriesView);
    }

    private Category favourites = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return entryBase.isFavorite();
        }

    }.setTextKey("favorites").setImage("flag_yellow").register();

    private Category all = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return true;
        }

    }.setTextKey("all").setImage("flag_blue").register();

    private Category watching = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return !entryBase.isFinished();
        }

    }.setTextKey("watching").setImage("flag_blue").register();

    private Category finished = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return entryBase.isFinished();
        }

    }.setTextKey("finished").setImage("flag_blue").register();

    private Category series = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return entryBase.getType() == Type.SERIES;
        }

    }.setTextKey("series").setImage("flag_blue").register();

    private Category books = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return entryBase.getType() == Type.BOOK;
        }

    }.setTextKey("books").setImage("flag_blue").register();

    private Category movies = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return entryBase.getType() == Type.MOVIE;
        }

    }.setTextKey("movies").setImage("flag_blue").register();

    private Category originals = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return !(entryBase instanceof RewatchEntry);
        }

    }.setTextKey("originals").setImage("flag_blue").register();

    private Category rewatches = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return entryBase instanceof RewatchEntry;
        }

    }.setTextKey("rewatches").setImage("flag_blue").register();

    private Category rated_10 = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return isInRage(10, entryBase.getRating());
        }

    }.setTextKey("rated10").setImage("flag_blue").register();

    private Category rated_9 = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return isInRage(9, entryBase.getRating());
        }

    }.setTextKey("rated9").setImage("flag_blue").register();

    private Category rated_8 = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return isInRage(8, entryBase.getRating());
        }

    }.setTextKey("rated8").setImage("flag_blue").register();

    private Category rated_7 = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return isInRage(7, entryBase.getRating());
        }

    }.setTextKey("rated7").setImage("flag_blue").register();

    private Category rated_6 = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return isInRage(6, entryBase.getRating());
        }

    }.setTextKey("rated6").setImage("flag_blue").register();

    private Category rated_5 = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return isInRage(5, entryBase.getRating());
        }

    }.setTextKey("rated5").setImage("flag_blue").register();

    private Category rated_4 = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return isInRage(4, entryBase.getRating());
        }

    }.setTextKey("rated4").setImage("flag_blue").register();

    private Category rated_3 = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return isInRage(3, entryBase.getRating());
        }

    }.setTextKey("rated3").setImage("flag_blue").register();

    private Category rated_2 = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return isInRage(2, entryBase.getRating());
        }

    }.setTextKey("rated2").setImage("flag_blue").register();

    private Category rated_1 = new Category(true) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return isInRage(1, entryBase.getRating());
        }

    }.setTextKey("rated1").setImage("flag_blue").register();

    private Category dropped = new Category(false) {

        @Override
        public boolean isValid(EntryBase entryBase) {
            return true;
        }

    }.setTextKey("dropped").setImage("flag_blue").register();

    private static boolean isInRage(int min, double val) {
        return ((int) val) == min;
    }

    private class CategoryUpdateWindow {

        private LTextInputDialog dialog;

        private LTextItem addTitle = new LTextItem("add_cat", null).register();

        private LTextItem editTitle = new LTextItem("edit_cat", null).register();

        public CategoryUpdateWindow() {

        }

        public Optional<String> add() {
            dialog = new LTextInputDialog().setKey("category_add").register();
            this.dialog.setTitle(addTitle.getText());
            LanguageRegistry.unregisterListener(dialog);
            return this.dialog.showAndWait();
        }

        public Optional<String> edit(String name) {
            dialog = new LTextInputDialog(name).setKey("category_add").register();
            this.dialog.setTitle(editTitle.getText());
            LanguageRegistry.unregisterListener(dialog);
            return this.dialog.showAndWait();
        }

    }
}
