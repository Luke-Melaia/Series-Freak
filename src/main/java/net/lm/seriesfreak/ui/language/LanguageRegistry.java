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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.lm.seriesfreak.util.ChangeListenerTemplate;
import net.lm.seriesfreak.util.ChangeListenerList;

/**
 * 
 * @author Luke Melaia
 */
public class LanguageRegistry implements ChangeListenerTemplate<Language, LanguageListener> {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private static final LanguageRegistry INSTANCE = new LanguageRegistry();

    private final ChangeListenerList<Language> listeners = new ChangeListenerList<>();

    private final Map<String, Language> languages = new HashMap<>();

    private Language currentLanguage = null;

    private LanguageRegistry() {
        log.trace("Initializing language registry");
    }

    public void load(Language lang) {
        languages.putIfAbsent(lang.getName(), lang);
        log.info("Loaded language: " + lang.getName());
    }

    public void loadAll(Language... langs) {
        for (Language lang : langs) {
            this.load(lang);
        }
    }

    public void selectLanguage(Language language) {
        //TODO: make another thread for this.
        if (!language.containsValue(language)) {
            this.load(language);
        }
        this.selectLanguage(language.getName());
    }

    public void selectLanguage(String languageName) {
        if (languages.containsKey(languageName)) {
            Language lang = languages.get(languageName);
            if (currentLanguage != lang) {
                currentLanguage = lang;
                log.info("Switching to language: " + lang);
                this.refreshListeners();
            }
        } else {
            log.warn("Language: " + languageName + " does not exist!");
            throw new IllegalArgumentException("language: " + languageName + " is not valid");
        }

    }
    
    public Language getCurrentLanguage() {
        return this.currentLanguage;
    }

    private void notifyListeners(Language lang) {
        listeners.notifyListeners(lang);
    }

    public void refreshListeners() {
        this.notifyListeners(currentLanguage);
    }

    @Override
    public void addListener(LanguageListener listener) {
        listeners.addListener(listener);
        if (currentLanguage != null) {
            listener.onChanged(currentLanguage);
        }
    }

    @Override
    public void removeListener(LanguageListener listener) {
        listeners.removeListener(listener);
    }

    public Language[] getLanguages() {
        Language[] ret = new Language[languages.size()];
        ret = languages.values().toArray(ret);
        Arrays.sort(ret, Language.LanguageComparator);
        return ret;
    }

    public static void registerListener(LanguageListener listener) {
        INSTANCE.addListener(listener);
    }

    public static void registerListeners(LanguageListener... listeners) {
        for (LanguageListener listener : listeners) {
            registerListener(listener);
        }
    }

    public static void unregisterListener(LanguageListener listener) {
        INSTANCE.removeListener(listener);
    }

    public static LanguageRegistry getInstance() {
        return INSTANCE;
    }
}
