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

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class TextualProgressBarTableCell<S> extends TableCell<S, TextualProgress> {

    private final TextualProgressBar progressBar;
    private ObservableValue value;

    public static <S> Callback<TableColumn<S, TextualProgress>, TableCell<S, TextualProgress>> forTableColumn() {
        return (TableColumn<S, TextualProgress> param) -> new TextualProgressBarTableCell<>();
    }

    public TextualProgressBarTableCell() {
        this.getStyleClass().add("textual-progress-bar-table-cell");
        this.progressBar = new TextualProgressBar();
        setGraphic(progressBar);
    }

    @Override
    public void updateItem(TextualProgress item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            value = getTableColumn().getCellObservableValue(getIndex());
            if (value != null) {
                progressBar.setProgress(((TextualProgress) value.getValue()).getProgress());
                progressBar.setText(((TextualProgress) value.getValue()).getText());
            } else {
                progressBar.setProgress(item.getProgress());
                progressBar.setText(item.getText());
            }
            setGraphic(progressBar);
        }
    }
}
