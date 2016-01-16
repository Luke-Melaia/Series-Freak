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
package net.lm.seriesfreak.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luke Melaia
 */
public class ChangeListenerList<T> implements ListenerList<T> {

    private final List<ChangeListener<T>> listeners;

    public ChangeListenerList() {
        listeners = new ArrayList<>(10);
    }

    public ChangeListenerList(int initialListLength) {
        listeners = new ArrayList<>(initialListLength);
    }

    @Override
    public void addListener(ChangeListener<T> listener) {
        listeners.add((listener));
    }

    @Override
    public void removeListener(ChangeListener<T> listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyListeners(T object) {
        for (ChangeListener<T> listener : listeners) {
            listener.onChanged(object);
        }
    }

    public int size() {
        return this.listeners.size();
    }

    public List<ChangeListener<T>> getListeners() {
        ArrayList<ChangeListener<T>> ret = new ArrayList<>();

        for (ChangeListener<T> listener : listeners) {
            ret.add(listener);
        }
        
        return ret;
    }
}