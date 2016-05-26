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

import javafx.event.Event;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 *
 * @author Luke Melaia
 */
public class TextualProgressBar extends StackPane {

    private ProgressBar progressBar = new ProgressBar();
    private Text text = new Text();

    public TextualProgressBar() {
        this(-1, "");
    }

    public TextualProgressBar(double progress) {
        this(progress, "");
    }

    public TextualProgressBar(String text) {
        this(-1, text);
    }

    public TextualProgressBar(double percent, String text) {
        this.progressBar.setProgress(percent);
        this.text.setText(text);

        progressBar.setMaxWidth(Double.MAX_VALUE);

        this.progressBar.setOnMousePressed(event -> {
            this.text.fireEvent(event);
        });

        getChildren().setAll(progressBar, this.text);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setProgress(double progress) {
        this.progressBar.setProgress(progress);
    }

    public static class SelectedEvent extends Event {

        private SelectedEvent() {
            super(Event.ANY);
        }
    }
}
