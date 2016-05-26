/*
 * Copyright (C) 2016 Luke Melaia
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
package net.lm.seriesfreak.ui.alert;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import net.lm.seriesfreak.util.ImageHelper;

/**
 *
 * @author Luke Melaia
 */
public class CrashAlert extends Alert {

    public CrashAlert(Throwable throwable) {
        super(AlertType.ERROR);
        Stage s = (Stage) this.getDialogPane().getScene().getWindow();
        s.getIcons().add(ImageHelper.getApplicationIcon());
        init(throwable);
    }

    private void init(Throwable throwable) {
        this.setTitle("Crash");
        this.setHeaderText("Unfortunately Series Freak has crashed.");
        this.setContentText("Reason: " + throwable.getMessage());

        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        throwable.printStackTrace(printWriter);

        Label expand = new Label("Exception:");

        TextArea exception = new TextArea(writer.toString());

        exception.setEditable(false);
        exception.setWrapText(true);

        exception.setMaxWidth(Double.MAX_VALUE);
        exception.setMaxHeight(Double.MAX_VALUE);

        GridPane.setVgrow(exception, Priority.ALWAYS);
        GridPane.setHgrow(exception, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(expand, 0, 0);
        expContent.add(exception, 0, 1);

        this.getDialogPane().setExpandableContent(expContent);
    }
}
