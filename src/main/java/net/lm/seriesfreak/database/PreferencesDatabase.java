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
package net.lm.seriesfreak.database;

import net.lm.seriesfreak.database.implementation.LoadType;
import net.lm.seriesfreak.database.implementation.TypeLoader;
import net.lm.seriesfreak.database.data.properties.Property;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.lm.seriesfreak.Application;
import net.lm.seriesfreak.ExitCode;
import net.lm.seriesfreak.util.ChangeListener;
import net.lm.seriesfreak.util.ChangeListenerList;

/**
 *
 * @author Luke Melaia
 */
public class PreferencesDatabase implements IDatabase {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private HashMap<String, Property> properties = new HashMap<>();

    private ChangeListenerList<State> listenerList = new ChangeListenerList<>();

    private final List<Class<? extends Property>> propertyTypes = new ArrayList<>();

    public PreferencesDatabase() {
        try {
            //@unstable: possibility of breaking if packages change
            TypeLoader loader = new TypeLoader("net.lm.seriesfreak.database.data.properties", LoadType.class);

            for (Class<? extends Property> propertyClass : loader.getClasses(Property.class)) {
                propertyTypes.add(propertyClass);
            }
        } catch (IOException ex) {
            log.fatal("Failed to load property types", ex);
            Application.exit(ExitCode.TYPE_LOADING_FAILURE);
        }
    }

    @Override
    public JsonObject serialize() {
        this.listenerList.notifyListeners(State.SERIALIZING);

        JsonObject data = new JsonObject();

        Map<String, JsonObject> propertyKeys = new HashMap<>();

        for (Map.Entry<String, Property> entry : properties.entrySet()) {
            Property property = entry.getValue();
            String propertyName = entry.getKey();

            JsonObject propertyKey;

            if (!propertyKeys.containsKey(property.getTypeName())) {
                propertyKey = new JsonObject();
                propertyKeys.put(property.getTypeName(), propertyKey);
            } else {
                propertyKey = propertyKeys.get(property.getTypeName());
            }

            propertyKey.add(propertyName, property.serialize());
        }

        for (String key : propertyKeys.keySet()) {
            data.add(key, propertyKeys.get(key));
        }

        return data;
    }

    @Override
    public void deserialize(JsonObject data) {
        if (data == null) {
            this.properties.clear();
            this.listenerList.notifyListeners(State.DESERIALIZING);
            return;
        }

        this.properties.clear();

        for (Class<? extends Property> propertyClass : propertyTypes) {
            try {
                Property property = propertyClass.newInstance();
                String typeName = property.getTypeName();
                JsonObject propertyKey = data.getAsJsonObject(typeName);

                if (propertyKey != null) {
                    for (Map.Entry<String, JsonElement> keyProperties : propertyKey.entrySet()) {
                        try {
                            property = propertyClass.newInstance();
                            property.deserialize(keyProperties.getValue().getAsJsonObject());
                            this.properties.put(keyProperties.getKey(), property);
                        } catch (IllegalArgumentException ex) {
                            log.error("Failed to create property: " + keyProperties.getKey(), ex);
                        }
                    }
                } else {
                    log.warn("Missing property key: " + typeName);
                }

            } catch (InstantiationException | IllegalAccessException e) {
                log.error("Failed to create property", e);
            }
        }

        listenerList.notifyListeners(State.DESERIALIZING);
    }

    @Override
    public String getName() {
        return "Properties";
    }

    @Override
    public void clear() {
        this.properties.clear();
    }
    
    public boolean addProperty(String name, Property property) {
        if (properties.containsKey(name)) {
            return false;
        }

        properties.put(name, property);
        return true;
    }

    public Property getProperty(String name) {
        return properties.get(name);
    }

    public void addStateListener(ChangeListener<State> listener) {
        this.listenerList.addListener(listener);
    }

    public void removeStateListener(ChangeListener<State> listener) {
        this.listenerList.removeListener(listener);
    }

    public enum State {

        SERIALIZING,
        DESERIALIZING;
    }
}
