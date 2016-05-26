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

package net.lm.seriesfreak.ui.language;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import net.lm.seriesfreak.Application;
import net.lm.seriesfreak.ExitCode;
import resources.Resources;

/**
 * 
 * @author Luke Melaia
 */
public class LanguageLoader {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private final String resourceFileList;

    private final String resourcesPath;

    private List<Language> languages = new ArrayList<>(3);

    public LanguageLoader(String jsonFileList, String resourcesPath) {
        log.trace("Initializing language loader");

        if (resourcesPath.startsWith("/")) {
            resourcesPath = resourcesPath.replaceFirst("/", "");
        }
        this.resourcesPath = "/" + resourcesPath;

        if (jsonFileList.startsWith("/")) {
            jsonFileList = jsonFileList.replaceFirst("/", "");
        }

        this.resourceFileList = "/" + jsonFileList;
        load();
    }

    private void load() {
        getFiles();
    }

    @SuppressWarnings("UseSpecificCatch")
    private void getFiles() {
        try {
            JsonObject resourceListFile = Resources.getResources();
            
            JsonArray langs = resourceListFile.getAsJsonArray("lang");

            for (int i = 0; i < langs.size(); i++) {
                Language lang = new Language(langs.get(i).getAsString());

                try (InputStream resIn = this.getClass().getResourceAsStream(resourcesPath + "/" + lang.getName() + ".lang")) {
                    lang.load(resIn);
                }

                languages.add(lang);
            }
        } catch (Exception ex) {
            log.fatal("Failed to get languages files: ", ex);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Files");
            alert.setHeaderText("Application cannot load");
            alert.setContentText(
                    "The language files for this application are missing.\n\n"
                    + "You will probably need to reinstall the application to fix this.");
            alert.showAndWait();

            Application.exit(ExitCode.LANGUAGE_LOADING_FAILURE);
        }
    }

    public Language[] getLanguages() {
        Language[] ret = new Language[languages.size()];
        ret = languages.toArray(ret);
        return ret;
    }

}
