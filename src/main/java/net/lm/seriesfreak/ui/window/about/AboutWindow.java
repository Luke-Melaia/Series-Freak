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
package net.lm.seriesfreak.ui.window.about;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import net.lm.seriesfreak.ui.window.Window;
import net.lm.seriesfreak.util.ImageHelper;

/**
 *
 * @author Luke Melaia
 */
public class AboutWindow extends Window {

    private Label title;

    private StackPane root;

    public AboutWindow() {
        this.setTitle("About");
        this.getIcons().add(ImageHelper.getApplicationIcon());
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setOnCloseRequest(event -> {
            this.destroy();
        });
    }

    @Override
    public void initProperties() {
        //NO-OP
    }

    @Override
    public void initComponents() {
        this.root = new StackPane();
        title = new Label("", ImageHelper.getImage("title"));
    }

    @Override
    public void addComponents() {
        this.setScene(new Scene(root, 500, 200));

        VBox items = new VBox(
                new StackPane(title),
                new Region(),
                new StackPane(new Label("Version 1.0")),
                new StackPane(new Label("Series/Movie/Book Manager")),
                new Region(), new Label(), new Region(),
                new StackPane(new Label("Copyright(C) 2015 Luke Melaia.")),
                new StackPane(new Label("Licensed under GNU General Public License, Version 3.")),
                new StackPane(new Label("This is free software. If you paid for it, get a refund."))
        );

        this.root.getChildren().add(items);
    }

    @Override
    public final void destroy() {
        this.hide();
    }

}
