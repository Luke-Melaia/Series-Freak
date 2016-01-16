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

package resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.MissingResourceException;

/**
 *
 * @author Luke Melaia
 */
public class Resources {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();
    
    private static final Resources RESOURCES = new Resources();

    public static JsonObject getResources() {
        return RESOURCES.getJson();
    }

    private JsonObject resources = new JsonObject();

    private Resources() {
        load();
    }

    private void load() {
        try (Reader in = new InputStreamReader(this.getClass().getResourceAsStream("/resources/resources"))) {
            JsonReader reader = new JsonReader(in);
            reader.setLenient(true);
            resources = new JsonParser().parse(reader).getAsJsonObject();
        } catch (IOException ex){
            log.fatal("Missing resources file", ex);
            throw new MissingResourceException("Missing resources/resources", "Resources.java", "resources");
        }
    }

    private JsonObject getJson() {
        return resources;
    }
}
