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
package net.lm.seriesfreak.ui.window.libraries;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import net.lm.seriesfreak.ui.language.node.LLabel;
import net.lm.seriesfreak.ui.language.node.LTextItem;
import net.lm.seriesfreak.ui.window.Window;
import net.lm.seriesfreak.util.ImageHelper;

/**
 *
 * @author Luke Melaia
 */
public class LibrariesWindow extends Window {

    private static final LTextItem windowName = new LTextItem("libraries", null).register();

    private StackPane root = new StackPane();

    private Text header;

    private Label description = new LLabel().setTextKey("description").register();

    private Label gsonName = new Label("Google Gson");

    private Label gsonLicense = new Label("Apache License, Version 2.0");

    private Hyperlink gsonGet = new Hyperlink("Web site");

    private Label guavaName = new Label("Google Guava");

    private Label guavaLicense = new Label("Apache License, Version 2.0");

    private Hyperlink guavaGet = new Hyperlink("Web site");

    private Label lfjName = new Label("Apache Log4j");

    private Label lfjLicense = new Label("Apache License, Version 2.0");

    private Hyperlink lfjGet = new Hyperlink("Web site");

    private Label anName = new Label("Alphanum Comparator");

    private Label anLicense = new Label("GNU Lesser General Public License");

    private Hyperlink anGet = new Hyperlink("Web site");

    public LibrariesWindow() {
        this.setTitle(windowName.getText());
        this.getIcons().add(ImageHelper.getApplicationIcon());
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.setOnCloseRequest(event -> destroy());
    }

    @Override
    public void initProperties() {
        //NO-OP
    }

    @Override
    public void initComponents() {
        header = new Text(windowName.getText());
        Font font = new Font(this.header.getFont().getName(), 20);
        header.setFont(font);

        gsonGet.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.github.com/google/gson"));
            } catch (IOException | URISyntaxException ex) {
                
            }
        });

        guavaGet.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.github.com/google/guava"));
            } catch (IOException | URISyntaxException ex) {
                
            }
        });

        lfjGet.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI("http://logging.apache.org/log4j"));
            } catch (IOException | URISyntaxException ex) {
                
            }
        });

        anGet.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI("http://www.davekoelle.com/alphanum.html"));
            } catch (IOException | URISyntaxException ex) {
                
            }
        });
    }

    @Override
    public void addComponents() {
        this.setScene(new Scene(root, 400, 540));

        VBox components = new VBox(10);
        components.setPadding(new Insets(20, 0, 0, 0));

        components.getChildren().addAll(new StackPane(header), new StackPane(description));

        VBox librariesComponents = new VBox(10);
        librariesComponents.setPadding(new Insets(30, 0, 0, 0));

        librariesComponents.getChildren().addAll(
                new StackPane(gsonName), new StackPane(gsonLicense), new StackPane(gsonGet),
                new Label(),
                new StackPane(guavaName), new StackPane(guavaLicense), new StackPane(guavaGet),
                new Label(),
                new StackPane(lfjName), new StackPane(lfjLicense), new StackPane(lfjGet),
                new Label(),
                new StackPane(anName), new StackPane(anLicense), new StackPane(anGet)
        );

        components.getChildren().add(new StackPane(librariesComponents));

        root.getChildren().addAll(components);
    }

    @Override
    public final void destroy() {
        this.hide();
    }

}
