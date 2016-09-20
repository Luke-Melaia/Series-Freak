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

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import net.lm.seriesfreak.ui.language.node.LTextField;
import net.lm.seriesfreak.ui.language.node.LButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javax.swing.ImageIcon;
import net.lm.seriesfreak.util.ChangeListener;
import net.lm.seriesfreak.util.ImageHelper;

/**
 *
 * @author Luke Melaia
 */
public class SearchBar extends HBox {

    private final TextField textField = new LTextField().setPromptKey("search").setTooltipKey("search").register();

    private final Button searchButton = new LButton().setTooltipKey("clear").register();

    private final ImageView clearImage = ImageHelper.getImage("cross");
    
    private final HBox buttonGraphic = new HBox(5);
    
    private ChangeListener<String> changeListener;

    public SearchBar(ChangeListener<String> changeListener) {
        super(1);
        this.textField.setMinWidth(130);
        this.textField.setMaxWidth(145);
        this.changeListener = changeListener;

        this.buttonGraphic.getChildren().addAll(clearImage, textField);
        HBox.setMargin(clearImage, new Insets(5, 0, 0, 0));
        this.searchButton.setGraphic(buttonGraphic);
        this.textField.setAlignment(Pos.CENTER);
        
        this.searchButton.setOnAction(event -> {
            if(this.textField.isFocused()){
                return;
            }
            
            this.textField.clear();
            this.changeListener.onChanged(this.textField.getText());
        });

        this.textField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.changeListener.onChanged(this.textField.getText());
            }
            event.consume();
        });

        addItems();
    }
    
    public String getText() {
        return this.textField.getText();
    }

    private void addItems() {
        this.getChildren().addAll(searchButton);
    }
}
