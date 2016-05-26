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
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.database.data.entries.Type;
import net.lm.seriesfreak.ui.language.alert.LAlert;
import net.lm.seriesfreak.ui.language.node.LLabel;
import net.lm.seriesfreak.ui.language.node.LRadioButton;
import net.lm.seriesfreak.ui.language.node.LTab;
import net.lm.seriesfreak.ui.language.node.LTextField;
import net.lm.seriesfreak.util.FileUtils;
import net.lm.seriesfreak.util.RatingHelper;

/**
 *
 * @author Luke Melaia
 */
class GeneralTab extends LTab {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    public final double WIDTH = 315;

    public final double HEIGHT = 290;

    private GridPane root = new GridPane();

    private Label nameLabel = new LLabel().setTextKey("info.name").register();

    private Label episodeLabel = new LLabel().setTextKey("info.episode").register();

    private Label ratingLabel = new LLabel().setTextKey("info.rating").register();

    private Label typeLabel = new LLabel().setTextKey("info.type").register();

    private Label slashLabel = new Label("/");

    private TextField nameField = new LTextField().setPromptKey("info.name").setTooltipKey("info.name").register();

    private TextField episodeField = new LTextField().setPromptKey("info.episodeon").setTooltipKey("info.episodeon").register();

    private TextField episodesField = new LTextField().setPromptKey("info.episodetotal").setTooltipKey("info.episodetotal").register();

    private ComboBox<String> ratingBox = new ComboBox<>(FXCollections.observableArrayList(RatingHelper.getTextualRatings()));

    private RadioButton seriesButton = new LRadioButton().setTextKey("info.series").setTooltipKey("info.series").register();

    private RadioButton movieButton = new LRadioButton().setTextKey("info.movie").setTooltipKey("info.movie").register();

    private RadioButton bookButton = new LRadioButton().setTextKey("info.book").setTooltipKey("info.book").register();

    public GeneralTab() {
        super();
        super.setTextKey("basicinformation");
        super.setTooltipKey("basicinformation");

        addItems();
    }

    public void clear() {
        this.nameField.setText("");
        this.episodeField.setText("");
        this.episodesField.setText("");
        this.ratingBox.getSelectionModel().select(ratingBox.getItems().size() - 1);
        this.seriesButton.setSelected(true);
        this.nameField.requestFocus();
    }

    public String getName() throws InvalidFieldDataException {
        String name = this.nameField.getText();
        if (!name.equals("")) {
            return name;
        }

        new LAlert(Alert.AlertType.INFORMATION).setKey("no_name").register().showAndWait();
        throw new InvalidFieldDataException("Name is empty");
    }

    public int getEpisode() throws InvalidFieldDataException {
        try {
            return Short.parseShort(this.episodeField.getText());//We're going to parse as a short simply because Integer is just too large.
        } catch (NumberFormatException ex) {
            if (this.episodeField.getText().isEmpty()) {
                return 0;
            }
            new LAlert(Alert.AlertType.INFORMATION).setKey("invalid_episode_format").register().showAndWait();
            throw new InvalidFieldDataException("Episode must be a number or empty");
        }
    }

    public int getEpisodes() throws InvalidFieldDataException {
        String episodes = this.episodesField.getText();

        if (episodes.replace(" ", "").equals("?")) {
            return -1;
        } else {
            try {
                return Short.parseShort(episodes);//We're going to parse as a short simply because Integer is just too large.
            } catch (NumberFormatException ex) {
                new LAlert(Alert.AlertType.INFORMATION).setKey("invalid_episodes_format").register().showAndWait();
                throw new InvalidFieldDataException("Episodes must be a number or a question mark (?)");
            }
        }
    }

    public double getRating() {
        try {
            return Double.parseDouble(this.ratingBox.getValue());
        } catch (NumberFormatException ex) {
            return 0.0;
        }
    }

    public Type getType() {
        if (this.seriesButton.isSelected()) {
            return Type.SERIES;
        } else if (this.movieButton.isSelected()) {
            return Type.MOVIE;
        } else {
            return Type.BOOK;
        }
    }

    public void populateFromDirectory(File root) throws IOException {
        if (!root.isDirectory()) {
            return;
        }
        this.episodesField.setText(String.valueOf(FileUtils.getFileTreeNames(root, 2).length));
        this.nameField.setText(root.getName());
    }

    public void populateFields(EntryBase entry) {
        this.nameField.setText(entry.getName());
        this.episodeField.setText(String.valueOf(entry.getEpisode()));
        this.episodesField.setText((entry.getEpisodes() != -1) ? String.valueOf(entry.getEpisodes()) : "?");
        if (entry.getRating() == 0.0) {
            this.ratingBox.getSelectionModel().selectLast();
        } else {
            this.ratingBox.getSelectionModel().select(String.valueOf(entry.getRating()));
        }

        switch (entry.getType()) {
            case SERIES:
                this.seriesButton.setSelected(true);
                break;
            case BOOK:
                this.bookButton.setSelected(true);
                break;
            case MOVIE:
                this.movieButton.setSelected(true);
                break;
        }
    }

    private void addItems() {
        root.setAlignment(Pos.CENTER);
        root.setHgap(5);
        root.setVgap(5);

        final HBox parent = new HBox(20);

        final VBox labels = new VBox(20);
        final VBox components = new VBox(13);
        components.setMinWidth(200);

        final HBox episodeBox = new HBox(10);
        episodeField.setMaxWidth(70);
        episodesField.setMaxWidth(70);
        episodeBox.getChildren().addAll(episodeField, slashLabel, episodesField);

        ratingBox.getSelectionModel().select(RatingHelper.getTextualRatings()[RatingHelper.getTextualRatings().length - 1]);
        ratingBox.setMinWidth(200);

        final HBox typeBox = new HBox(10);
        typeBox.getChildren().addAll(seriesButton, movieButton, bookButton);

        ToggleGroup group = new ToggleGroup();
        seriesButton.setToggleGroup(group);
        seriesButton.setSelected(true);
        movieButton.setToggleGroup(group);
        bookButton.setToggleGroup(group);

        labels.getChildren().addAll(nameLabel, episodeLabel, ratingLabel, typeLabel);
        components.getChildren().addAll(nameField, episodeBox, ratingBox, typeBox);

        parent.getChildren().addAll(labels, components);
        root.add(parent, 0, 0, 3, 3);
        this.setContent(root);
    }
}
