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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import net.lm.seriesfreak.Application;
import net.lm.seriesfreak.ExitCode;
import net.lm.seriesfreak.database.implementation.CorruptFileException;
import net.lm.seriesfreak.database.implementation.InaccessibleFileException;
import net.lm.seriesfreak.ui.language.alert.LAlert;
import net.lm.seriesfreak.ui.language.node.LMenu;
import net.lm.seriesfreak.ui.language.node.LMenuItem;

/**
 *
 * @author Luke Melaia
 */
class FileMenu extends LMenu {

    private static final FileChooser.ExtensionFilter SERIES_FREAK_SAVE = new FileChooser.ExtensionFilter("Series Freak Save (*.sfs)", "*.sfs");

    private static Alert corrupt = new LAlert(Alert.AlertType.WARNING).setKey("corrupt").register();
    
    private static Alert iSaving = new LAlert(Alert.AlertType.WARNING).setKey("unknown_io_saving").register();
    
    private static Alert iLoading = new LAlert(Alert.AlertType.WARNING).setKey("inaccessible_loading").register();
    
    private static Alert uSaving = new LAlert(Alert.AlertType.WARNING).setKey("unknown_io_saving").register();
    
    private static Alert uLoading = new LAlert(Alert.AlertType.WARNING).setKey("unknown_io_loading").register();
    
    private static Alert notLoaded = new LAlert(Alert.AlertType.WARNING).setKey("not_loaded").register();
    
    private LMenuItem newMenu = new LMenuItem().setTextKey("new").setImage("new").register();

    private LMenuItem openMenu = new LMenuItem().setTextKey("open").setImage("open").register();

    private LMenuItem saveMenu = new LMenuItem().setTextKey("save").setImage("save").register();

    private LMenuItem saveAsMenu = new LMenuItem().setTextKey("save_as").setImage("save").register();

    private LMenuItem exitMenu = new LMenuItem().setTextKey("exit").setImage("exit").register();

    private LAlert confirm = new LAlert(Alert.AlertType.CONFIRMATION).register();

    private FileChooser fileDialog = new FileChooser();

    private MainWindow mainWindow;

    public FileMenu(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;
        fileDialog.getExtensionFilters().add(SERIES_FREAK_SAVE);
        this.setTextKey("file");
        initItems();
    }

    private void initItems() {
        newMenu.setOnAction(newEvent);
        openMenu.setOnAction(openEvent);
        saveMenu.setOnAction(saveEvent);
        saveAsMenu.setOnAction(saveAsEvent);
        exitMenu.setOnAction(exitEvent);

        this.getItems().addAll(newMenu, openMenu, saveMenu, saveAsMenu, exitMenu);
    }

    private EventHandler<ActionEvent> newEvent = (ActionEvent e) -> {
        confirm.setKey("new");
        Optional<ButtonType> op = confirm.showAndWait();

        if (op.isPresent()) {
            if (op.get() == ButtonType.OK) {
                mainWindow.getDatabase().clear();
            }
        }
    };
    
    private EventHandler<ActionEvent> openEvent = (ActionEvent e) -> {
        try {
                File saveFile = fileDialog.showOpenDialog(mainWindow);
                if (saveFile != null) {
                    mainWindow.getDatabase().open(saveFile.getAbsolutePath());
                }
                System.gc();
            } catch (CorruptFileException ex) {
                corrupt.showAndWait();
            } catch (InaccessibleFileException ex) {
                iLoading.showAndWait();
            } catch (IOException ex) {
                uLoading.showAndWait();
            }
    };
    
    private EventHandler<ActionEvent> saveEvent = (ActionEvent e) -> {
        try {
                mainWindow.getDatabase().save();
                System.gc();
            } catch (InaccessibleFileException ex) {
                iSaving.showAndWait();
            } catch (IOException ex) {
                uSaving.showAndWait();
            } catch (IllegalStateException ex) {
                notLoaded.showAndWait();
            }
    };
    
    private EventHandler<ActionEvent> saveAsEvent = (ActionEvent e) -> {
        try {
                File saveFile = fileDialog.showSaveDialog(mainWindow);
                if (saveFile != null) {
                    mainWindow.getDatabase().save(saveFile.getAbsolutePath());
                }
                System.gc();
            } catch (InaccessibleFileException ex) {
                iSaving.showAndWait();
            } catch (IOException ex) {
                uSaving.showAndWait();
            }
    };
    
    private EventHandler<ActionEvent> exitEvent = (ActionEvent e) -> {
        Application.exit(ExitCode.SUCCESS);
    };
    
    public void fireSave(){
        this.saveMenu.fire();
    }
}
