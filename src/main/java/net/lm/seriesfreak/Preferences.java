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

package net.lm.seriesfreak;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import net.lm.seriesfreak.util.PropertiesUtil;

/**
 * 
 * @author Luke Melaia
 */
public final class Preferences extends Properties {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private static final File preferenceFile = new File(System.getProperty("user.dir") + "/preferences");

    private static final Preferences INSTANCE = new Preferences();

    private boolean hasChanged = false;

    private Preferences() {
        super();
        log.trace("Initializing preferences");
        read();
    }

    private void read() {
        try {
            PropertiesUtil.load(this, preferenceFile);
        } catch (IOException ex) {
            log.error("Failed to load preferences - will be using default settings", ex);
        }
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        String prop = super.getProperty(key);
        if (prop != null) {
            return prop;
        }
        
        this.put(key, defaultValue);
        hasChanged = true;
        return defaultValue;
    }
    
    @Override
    public Object setProperty(String key, String value) {
        hasChanged = true;
        log.info("Set proprty: " + key + " -> " + value);
        return super.setProperty(key, value);

    }

    public void save() {
        if (this.hasChanged) {
            try {
                PropertiesUtil.save(this, preferenceFile, "DO NOT MODIFY\nSeries Freak user preferences");
            } catch (IOException ioe) {
                log.error("Failed to save preferences", ioe);
            }
            return;
        }
    }
    
    public static void savePreferences() {
        INSTANCE.save();
    }

    public static Object set(String key, Object value) {
        return INSTANCE.setProperty(key, String.valueOf(value));
    }

    public static String get(String key, String defaultValue) {
        return INSTANCE.getProperty(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(get(key, String.valueOf(defaultValue)));
    }

    public static int getInteger(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public static double getDouble(String key, double defaultValue) {
        try {
            return Double.parseDouble(get(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}
