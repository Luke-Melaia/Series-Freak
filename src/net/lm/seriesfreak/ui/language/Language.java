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

import java.util.Comparator;
import java.util.Properties;

/**
 * 
 * @author Luke Melaia
 */
public class Language extends Properties {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private final String name;

    public Language(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getProperty(String key) {
        String value = super.getProperty(key);

        if (value == null || value.equals("")) {
            log.warn("Missing key: " + name + " -> " + key);

            return "<Missing>";
        }

        return value;
    }
    
    
    public static Comparator<Language> LanguageComparator = (Language o1, Language o2) -> {
        String name1 = o1.name.toUpperCase();
        String name2 = o2.name.toUpperCase();

        return name1.compareTo(name2);
    };

    @Override
    public String toString() {
        return this.name;
    }
}
