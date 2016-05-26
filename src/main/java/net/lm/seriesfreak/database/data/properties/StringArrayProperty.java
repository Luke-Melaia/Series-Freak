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
package net.lm.seriesfreak.database.data.properties;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;
import net.lm.seriesfreak.database.implementation.LoadType;

/**
 *
 * @author Luke Melaia
 */
@LoadType
public class StringArrayProperty implements Property<List<String>> {

    private List<String> value = new ArrayList<>();

    @Override
    public void set(List<String> value) {
        this.value = value;
    }

    @Override
    public List<String> get() {
        return this.value;
    }

    @Override
    public JsonObject serialize() {
        JsonObject data = new JsonObject();

        JsonArray values = new JsonArray();

        for (String val : value) {
            values.add(new JsonPrimitive(val));
        }

        data.add("Value", values);
        return data;
    }

    @Override
    public void deserialize(JsonObject object) {
        JsonArray values = object.getAsJsonArray("Value");

        for (int i = 0; i < values.size(); i++) {
            this.value.add(values.get(i).getAsString());
        }
    }

    @Override
    public String getTypeName() {
        return "StringArrayProperties";
    }

}
