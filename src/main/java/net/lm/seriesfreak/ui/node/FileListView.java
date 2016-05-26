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

package net.lm.seriesfreak.ui.node;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Luke Melaia
 */
public class FileListView extends StackPane {

    private ListView<String> fileNamesList = new ListView<>();

    private ObservableList<File> files = FXCollections.observableArrayList();

    private ObservableList<String> fileNames = fileNamesList.getItems();

    private final int trim;

    private final ListChangeListener<File> filesListener = (ListChangeListener.Change<? extends File> c) -> {
        this.fileNames.clear();
        for (File file : c.getList()) {
            this.fileNames.add(getPrefixedName(file));
        }
    };

    public FileListView() {
        this(2);
    }

    public FileListView(int trim) throws IllegalArgumentException {
        super();
        if (trim < 0) {
            throw new IllegalArgumentException("Trim < 0");
        }
        this.trim = trim;
        this.getChildren().add(fileNamesList);
        this.fileNamesList.setMaxWidth(Double.MAX_VALUE);
        this.fileNamesList.setMaxHeight(Double.MAX_VALUE);
        this.files.addListener(filesListener);
    }

    public void addFile(File file) throws NullPointerException {
        if (file == null) {
            throw new NullPointerException("File may not be null");
        }

        if (!file.isDirectory() && !file.isHidden()) {
            this.files.add(file);
        }
    }

    public void addFiles(File... files) throws NullPointerException {
        if (files == null) {
            throw new NullPointerException("Files may not be null");
        }

        for (File file : files) {
            if (!file.isDirectory() && !file.isHidden()) {
                addFile(file);
            }
        }
    }
    
    public void clear() {
        this.files.clear();
    }

    public void moveUp() {
        int selectedIndex = this.fileNamesList.getSelectionModel().getSelectedIndex();
        int replaceIndex = selectedIndex - 1;

        if (selectedIndex == -1 || selectedIndex == 0) {
            return;
        }

        List<File> newFiles = new ArrayList<>();

        for (int i = 0; i < replaceIndex; i++) {
            newFiles.add(files.get(i));
        }

        newFiles.add(this.files.get(selectedIndex));
        newFiles.add(this.files.get(replaceIndex));

        for (int i = selectedIndex + 1; i < this.fileNames.size(); i++) {
            newFiles.add(this.files.get(i));
        }

        this.clear();
        this.files.addAll(newFiles);
        this.fileNamesList.getSelectionModel().select(replaceIndex);
        this.fileNamesList.scrollTo(this.fileNamesList.getSelectionModel().getSelectedIndex());
    }

    public void moveDown() {
        int selectedIndex = this.fileNamesList.getSelectionModel().getSelectedIndex();
        int replaceIndex = selectedIndex + 1;

        if (selectedIndex == -1 || this.fileNames.size() == -1 || replaceIndex == this.fileNames.size()) {
            return;
        }

        List<File> newFiles = new ArrayList<>();

        for (int i = 0; i < selectedIndex; i++) {
            newFiles.add(files.get(i));
        }

        newFiles.add(this.files.get(replaceIndex));
        newFiles.add(this.files.get(selectedIndex));

        for (int i = selectedIndex + 2; i < this.fileNames.size(); i++) {
            newFiles.add(this.files.get(i));
        }

        this.clear();
        this.files.addAll(newFiles);
        this.fileNamesList.getSelectionModel().select(replaceIndex);
        this.fileNamesList.scrollTo(this.fileNamesList.getSelectionModel().getSelectedIndex());
    }

    public void remove() {
        int selectedIndex = this.fileNamesList.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            return;
        }

        this.files.remove(selectedIndex);
    }

    public File[] getFiles() {
        File[] filesF = new File[this.files.size()];
        return files.toArray(filesF);
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName() + "[ value = " + this.files + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof FileListView)) {
            return false;
        }

        FileListView other = (FileListView) obj;

        if (other.files.size() != this.files.size()) {
            return false;
        }

        File[] othersFiles = other.files.toArray(new File[other.files.size()]);
        File[] myFiles = this.files.toArray(new File[this.files.size()]);

        if (!Arrays.deepEquals(othersFiles, myFiles)) {
            return false;
        }

        return this.trim == other.trim;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        //Unfortunately ObservableLists don't override hashcode(i might be wrong) so i had to implement a subpar hashing algorithm
        hash = 97 * hash + this.files.size();
        hash = 97 * hash + this.trim;
        return hash;
    }

    private String getPrefixedName(File file) {
        String[] directoryTree = file.getAbsolutePath().split(Pattern.quote(System.getProperty("file.separator")));
        int length = directoryTree.length;

        String ret = "";

        for (int i = length - trim; i < length; i++) {
            ret += directoryTree[i] + ((i != length - 1) ? System.getProperty("file.separator") : "");
        }

        return ret;
    }
}
