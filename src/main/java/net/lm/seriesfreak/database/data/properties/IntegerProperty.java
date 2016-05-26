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

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.lm.seriesfreak.database.implementation.LoadType;

/**
 *
 * @author Luke Melaia
 */
@LoadType
public class IntegerProperty implements Property<Integer> {

    private int value;

    public IntegerProperty() {

    }

    public IntegerProperty(Integer value) {
        this.value = value;
    }

    @Override
    public void set(Integer value) {
        this.value = value;
    }

    @Override
    public Integer get() {
        return this.value;
    }

    @Override
    public JsonObject serialize() {
        JsonObject data = new JsonObject();

        data.add("Value", new JsonPrimitive(value));

        return data;
    }

    @Override
    public void deserialize(JsonObject object) {
        this.value = object.getAsJsonPrimitive("Value").getAsInt();
    }

    @Override
    public String getTypeName() {
        return "IntegerProperties";
    }

}
