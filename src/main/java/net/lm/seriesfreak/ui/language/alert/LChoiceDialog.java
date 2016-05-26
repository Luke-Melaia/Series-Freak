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
package net.lm.seriesfreak.ui.language.alert;

import java.util.Collection;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;
import net.lm.seriesfreak.ui.language.Language;
import net.lm.seriesfreak.util.ImageHelper;

/**
 *
 * @author Luke Melaia
 */
public class LChoiceDialog<T> extends ChoiceDialog<T> implements LanguageAlert<LChoiceDialog<T>> {

    private static final String PREFIX = "choicedialog.";

    private String key;

    public LChoiceDialog() {
        super();
        Stage s = (Stage) this.getDialogPane().getScene().getWindow();
        s.getIcons().add(ImageHelper.getApplicationIcon());
    }

    public LChoiceDialog(T choice, T... choices) {
        super(choice, choices);
        Stage s = (Stage) this.getDialogPane().getScene().getWindow();
        s.getIcons().add(ImageHelper.getApplicationIcon());
    }

    public LChoiceDialog(T choice, Collection<T> choices) {
        super(choice, choices);
        Stage s = (Stage) this.getDialogPane().getScene().getWindow();
        s.getIcons().add(ImageHelper.getApplicationIcon());
    }

    @Override
    public LChoiceDialog<T> setKey(String key) {
        this.key = key;
        return this;
    }

    @Override
    public void onChanged(Language paramater) {
        this.setTitle(paramater.getProperty(PREFIX + key + ".title"));
        this.setHeaderText(paramater.getProperty(PREFIX + key + ".header"));
        this.setContentText(paramater.getProperty(PREFIX + key + ".content"));
    }

}
