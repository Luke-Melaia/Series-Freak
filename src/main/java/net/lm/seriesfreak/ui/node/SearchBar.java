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

import net.lm.seriesfreak.ui.language.node.LTextField;
import net.lm.seriesfreak.ui.language.node.LButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import net.lm.seriesfreak.util.ChangeListener;

/**
 *
 * @author Luke Melaia
 */
public class SearchBar extends HBox {

    public final TextField textField = new LTextField().setPromptKey("search").setTooltipKey("search").register();

    public final Button clearButton = new LButton().setImage("cross").setTooltipKey("clear").register();

    private ChangeListener<String> changeListener;

    public SearchBar(ChangeListener<String> changeListener) {
        super(1);
        this.textField.setMinWidth(160);
        this.changeListener = changeListener;

        this.clearButton.setOnAction(event -> {
            this.textField.clear();
            this.changeListener.onChanged(this.textField.getText());
        });

        this.textField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.changeListener.onChanged(this.textField.getText());
            }
        });

        addItems();
    }

    public String getText() {
        return this.textField.getText();
    }

    private void addItems() {
        this.getChildren().addAll(clearButton, textField);
    }
}
