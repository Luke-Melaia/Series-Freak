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
package net.lm.seriesfreak.ui.node;

/**
 *
 * @author Luke Melaia
 */
public final class TextualProgress {

    private double progress;
    private String text;

    public TextualProgress() {
        this(-1, "");
    }

    public TextualProgress(double progress) {
        this(progress, "");
    }

    public TextualProgress(String text) {
        this(-1, text);
    }

    public TextualProgress(double progress, String text) {
        this.progress = progress;
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public double getProgress() {
        return this.progress;
    }
}
