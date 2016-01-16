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

import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import net.lm.seriesfreak.ui.language.Language;
import net.lm.seriesfreak.ui.language.LanguageRegistry;
import net.lm.seriesfreak.util.ImageHelper;

/**
 *
 * @author Luke Melaia
 */
public class LTextInputDialog extends TextInputDialog implements LanguageAlert<LTextInputDialog> {

    private static final String PREFIX = "textdialog.";

    private String key;

    private Language current;

    public LTextInputDialog() {
        super();
        Stage s = (Stage) this.getDialogPane().getScene().getWindow();
        s.getIcons().add(ImageHelper.getApplicationIcon());
    }

    public LTextInputDialog(String defaultValue) {
        super(defaultValue);
        Stage s = (Stage) this.getDialogPane().getScene().getWindow();
        s.getIcons().add(ImageHelper.getApplicationIcon());
    }

    @Override
    public LTextInputDialog setKey(String key) {
        this.key = key;

        if (current != null && key != null) {
            this.setHeaderText(current.getProperty(PREFIX + key + ".header"));
            this.setTitle(current.getProperty(PREFIX + key + ".title"));
            this.setContentText(current.getProperty(PREFIX + key + ".content"));
        }

        return this;
    }

    @Override
    public LTextInputDialog register() {
        LanguageRegistry.registerListener(this);
        return this;
    }

    @Override
    public void onChanged(Language language) {
        this.current = language;
        setKey(key);
    }

}
