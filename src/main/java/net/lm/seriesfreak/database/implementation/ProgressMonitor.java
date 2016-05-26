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
package net.lm.seriesfreak.database.implementation;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

/**
 *
 * @author Luke Melaia
 */
public final class ProgressMonitor {

    private static final SimpleObjectProperty<ProgressObject> SIMPLE_OBJECT_PROPERTY = new SimpleObjectProperty<>(new ProgressObject(0, -1, ""));

    private static final SimpleBooleanProperty IS_BUSY = new SimpleBooleanProperty(false);

    private ProgressMonitor() {

    }

    public static synchronized void addProgressListener(ChangeListener<ProgressObject> listener) {
        SIMPLE_OBJECT_PROPERTY.addListener(listener);
    }

    public static synchronized void removeProgressListener(ChangeListener<ProgressObject> listener) {
        SIMPLE_OBJECT_PROPERTY.removeListener(listener);
    }

    public static synchronized void addStateListener(ChangeListener<Boolean> listener) {
        IS_BUSY.addListener(listener);
    }

    public static synchronized void removeStateListener(ChangeListener<Boolean> listener) {
        IS_BUSY.removeListener(listener);
    }

    public static synchronized void setProgress(ProgressObject object) {
        SIMPLE_OBJECT_PROPERTY.set(object);
    }

    public static synchronized void setBusy(boolean busy) {
        IS_BUSY.set(busy);
    }
    
    public static final class ProgressObject {

        private final int max;

        private final int value;

        private final String information;

        public ProgressObject(int max, int value, String information) {
            this.max = max;
            this.value = value;
            this.information = information;
        }

        public final int getMax() {
            return this.max;
        }

        public final int getValue() {
            return this.value;
        }

        public final String getInformation() {
            return this.information;
        }
    }
}
