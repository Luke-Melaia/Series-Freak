/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lm.seriesfreak.ui.window.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javax.swing.JOptionPane;
import net.lm.seriesfreak.database.data.categories.UserCategory;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.database.data.entries.RewatchEntry;
import net.lm.seriesfreak.ui.language.node.LTextItem;
import net.lm.seriesfreak.util.ChangeListener;
import net.lm.seriesfreak.util.ImageHelper;

/**
 *
 * @author Luke Melaia
 */
public class BottomToolBar extends ToolBar {

    private MainWindow mainWindow;

    public BottomToolBar(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        this.getItems().add(new CategorySelectorButton());
    }

    private class CategorySelectorButton extends HBox implements ChangeListener<EntryBase>, EventHandler<ActionEvent> {

        private ComboBox<String> categorySelect = new ComboBox<>();

        private ImageView addToCatImage = ImageHelper.getImage("category_add");

        private ImageView removeFromCatImage = ImageHelper.getImage("category_remove");

        private HBox buttonGraphic = new HBox(5, categorySelect);

        private Button actionButton = new Button("", buttonGraphic);

        private LTextItem createCategoryText = new LTextItem("create_category", null).register();

        private LTextItem selectEntryText = new LTextItem("select_entry", null).register();

        private UserCategory[] currentCategories;

        private EntryBase currentEntry;

        @SuppressWarnings("LeakingThisInConstructor")
        public CategorySelectorButton() {
            this.getChildren().add(actionButton);

            HBox.setMargin(addToCatImage, new Insets(5, 0, 0, 0));
            HBox.setMargin(removeFromCatImage, new Insets(5, 0, 0, 0));
            disable(true);

            mainWindow.getTableImpl().addNewSelectionListener(this);

            mainWindow.getEntryDatabase().addListener((EntryBase[] paramater) -> {
                //This is a hack that updates the category box/button
                //when an update in the entry database happens. This
                //prevents the button from showing the wrong icon,
                //which can happen if the entry is added to/removed from
                //a category using the context menu instead of the buttton.
                //This isn't prefect but it works.
                this.onChanged(null);
            });

            this.categorySelect.setOnAction(this);
            this.actionButton.setOnAction(event -> {
                onAction();
            });
        }

        private void onAction() {

            //If the user is required to create a category first, allow the user
            //to create a category from the botton itself.
            if (actionButton.getText() != null
                    && actionButton.getText().equals(this.createCategoryText.getText())) {
                mainWindow.getCategoriesImpl().fireAddCategoryAction();
            }

            if (currentEntry == null) {
                return;
            }

            UserCategory currentCat;
            try {
                currentCat = currentCategories[this.categorySelect
                        .getSelectionModel().getSelectedIndex()];
            } catch (ArrayIndexOutOfBoundsException e) {
                return;
            }

            boolean remove = currentCat.containsEntry(currentEntry);

            if (remove) {
                currentCat.removeEntry(currentEntry);
                mainWindow.getEntryDatabase().forceUpdate();
            } else {
                currentCat.addEntry(currentEntry);
                mainWindow.getEntryDatabase().forceUpdate();
            }
        }

        private void setImage(ImageView image) {
            if (this.buttonGraphic.getChildren().size() == 1) {
                this.buttonGraphic.getChildren().add(image);
            }

            this.buttonGraphic.getChildren().set(1, image);
        }

        /**
         *
         * @param selectEntry should the "select entry" text be displayed?
         * "select category" will be displayed if false.
         */
        private void disable(boolean selectEntry) {
            this.actionButton.setGraphic(null);

            if (selectEntry) {
                this.actionButton.setDisable(true);
            } else {
                this.actionButton.setDisable(false);
            }

            this.actionButton.setText((selectEntry) ? this.selectEntryText.getText()
                    : this.createCategoryText.getText()
            );
        }

        private void enable() {
            this.actionButton.setGraphic(buttonGraphic);
            this.actionButton.setText(null);
            this.actionButton.setDisable(false);
        }

        @Override
        public final void onChanged(EntryBase paramater) {
            if (paramater == null) {
                this.currentEntry = null;
                disable(true);
                return;
            }

            if (paramater instanceof RewatchEntry) {
                paramater = ((RewatchEntry) paramater).getOriginalEntry();
            }

            this.currentEntry = paramater;
            this.categorySelect.getItems().clear();
            UserCategory[] categories = mainWindow.getCategoriesImpl().getCategories();
            this.currentCategories = categories;

            if (categories.length == 0) {
                disable(false);
            } else {
                for (UserCategory category : categories) {
                    this.categorySelect.getItems().add(category.getValue());
                }

                this.categorySelect.getSelectionModel().select(categories[0].getValue());

                boolean containsEntry = currentCategories[this.categorySelect
                        .getSelectionModel().getSelectedIndex()]
                        .containsEntry(currentEntry);

                if (containsEntry) {
                    setImage(removeFromCatImage);
                } else {
                    setImage(addToCatImage);
                }

                enable();
            }
        }

        @Override
        public void handle(ActionEvent event) {
            event.consume();
            boolean containsEntry = false;
            try {
                containsEntry = currentCategories[this.categorySelect
                        .getSelectionModel().getSelectedIndex()]
                        .containsEntry(currentEntry);
            } catch (ArrayIndexOutOfBoundsException e) {
                //NO-OP
            }

            if (containsEntry) {
                setImage(removeFromCatImage);
            } else {
                setImage(addToCatImage);
            }
        }
    }
}
