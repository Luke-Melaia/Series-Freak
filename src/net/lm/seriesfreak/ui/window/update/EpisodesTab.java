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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.ui.language.alert.LAlert;
import net.lm.seriesfreak.ui.language.node.LButton;
import net.lm.seriesfreak.ui.language.node.LLabel;
import net.lm.seriesfreak.ui.language.node.LTab;
import net.lm.seriesfreak.ui.language.node.LTextField;
import net.lm.seriesfreak.ui.node.FileListView;
import net.lm.seriesfreak.util.FileUtils;

/**
 * 
 * @author Luke Melaia
 */
class EpisodesTab extends LTab {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    public final double WIDTH = 460;

    public final double HEIGHT = 520;

    private GridPane parent = new GridPane();

    private TextField rootFile = new LTextField().setTooltipKey("info.rootfile");

    private FileListView filesList = new FileListView();

    private Button moveUp = new LButton().setTooltipKey("info.moveup").setImage("up_arrow").register();

    private Button moveDown = new LButton().setTooltipKey("info.movedown").setImage("down_arrow").register();

    private Button addFile = new LButton().setTooltipKey("info.add").setImage("file_new").register();

    private Button removeFile = new LButton().setTooltipKey("info.remove").setImage("cross").register();

    private Button addFolder = new LButton().setTooltipKey("info.addall").setImage("folder_new").register();

    private Button selectFile = new LButton().setTextKey("info.file").setTooltipKey("info.file").register();

    private Button selectFolder = new LButton().setTextKey("info.folder").setTooltipKey("info.folder").register();

    private Label rootFileLabel = new LLabel().setTextKey("info.rootfile").register();

    private Label filesLabel = new LLabel().setTextKey("info.rootfiles").register();

    private FileChooser fileChooser = new FileChooser();

    private DirectoryChooser directoryChooser = new DirectoryChooser();

    private Stage window;

    public EpisodesTab(Stage window) {
        super();

        this.window = window;

        this.setTextKey("advancedinformation");
        this.setTooltipKey("advancedinformation");

        addItems();
        initButtonActions();
    }

    private void addItems() {
        parent.setAlignment(Pos.CENTER);
        parent.setMaxWidth(Double.MAX_VALUE);
        parent.setMaxHeight(Double.MAX_VALUE);
        parent.setHgap(5);
        parent.setVgap(5);

        filesList.setMaxHeight(300);
        rootFile.setMinWidth(330);
        filesList.setMinWidth(350);
        rootFile.setEditable(false);

        moveUp.setMaxWidth(Double.MAX_VALUE);
        moveDown.setMaxWidth(Double.MAX_VALUE);
        addFile.setMaxWidth(Double.MAX_VALUE);
        removeFile.setMaxWidth(Double.MAX_VALUE);
        addFolder.setMaxWidth(Double.MAX_VALUE);

        parent.add(rootFileLabel, 0, 0);

        HBox rootBox = new HBox(5);
        rootBox.getChildren().addAll(rootFile, selectFile, selectFolder);
        parent.add(rootBox, 0, 2, 3, 1);

        VBox buttonsBox = new VBox(10);
        buttonsBox.getChildren().addAll(moveUp, addFile, addFolder, removeFile, moveDown);

        parent.add(filesLabel, 0, 4);

        HBox filesBox = new HBox(10);
        filesBox.getChildren().addAll(filesList, buttonsBox);
        parent.add(filesBox, 0, 6, 3, 1);

        this.setContent(parent);
    }

    public void clear() {
        this.rootFile.setText("");
        this.filesList.clear();
        this.rootFile.requestFocus();
    }

    public String getRoot() {
        return this.rootFile.getText();
    }

    public String[] getFiles() {
        if (filesList.getFiles().length == 0) {
            return null;
        }

        String[] fileNames = new String[this.filesList.getFiles().length];
        for (int i = 0; i < fileNames.length; i++) {
            fileNames[i] = this.filesList.getFiles()[i].getAbsolutePath();
        }
        return fileNames;
    }

    public void populateFromDirectory(File root) throws IOException {
        if (!root.isDirectory()) {
            return;
        }
        File[] files = FileUtils.getFileTree(root, 2);

        for (File file : files) {
            this.filesList.addFile(file);
        }

        this.rootFile.setText(root.getAbsolutePath());
    }

    public void populateFields(EntryBase entry) {
        if (entry.getRootFile() != null) {
            this.rootFile.setText(entry.getRootFile());
        }

        if (entry.getFiles() != null) {
            for (String fileName : entry.getFiles()) {
                this.filesList.addFile(new File(fileName));
            }
        }
    }

    private void initButtonActions() {
        this.selectFile.setOnAction(event -> {
            File f = this.fileChooser.showOpenDialog(window);
            if (f != null) {
                this.rootFile.setText(f.getAbsolutePath());
            }
        });

        this.selectFolder.setOnAction(event -> {
            File f = this.directoryChooser.showDialog(window);
            if (f != null) {
                try {
                    this.rootFile.setText(f.getAbsolutePath());
                    this.filesList.clear();
                    this.filesList.addFiles(FileUtils.getFileTree(f, 2));
                } catch (IOException ex) {
                    log.error("Failed to set folder", ex);
                    new LAlert(Alert.AlertType.ERROR).setKey("file_walking").register().showAndWait();
                }
            }
        });

        this.moveUp.setOnAction(event -> {
            this.filesList.moveUp();
        });

        this.moveDown.setOnAction(event -> {
            this.filesList.moveDown();
        });

        this.removeFile.setOnAction(event -> {
            this.filesList.remove();
        });

        this.addFile.setOnAction(event -> {
            File f = fileChooser.showOpenDialog(window);
            if (f != null) {
                this.filesList.addFile(f);
            }
        });

        this.addFolder.setOnAction(event -> {
            File dir = directoryChooser.showDialog(window);
            if (dir != null) {
                try {
                    this.filesList.addFiles(FileUtils.getFileTree(dir, 1));
                } catch (IOException ex) {
                    log.error("Failed to add folder", ex);
                    new LAlert(Alert.AlertType.ERROR).setKey("file_walking").register().showAndWait();
                }
            }
        });
    }

}
