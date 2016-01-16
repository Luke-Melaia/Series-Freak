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

/**
 *
 * @author Luke Melaia
 */
public enum ExitCode {

    UNKNOWN_FAILURE(1),
    SUCCESS(0),
    LANGUAGE_LOADING_FAILURE(-1),
    PROPERTIES_LOADING_FAILURE(-2),
    TYPE_LOADING_FAILURE(-3);

    private final int exitCode;

    ExitCode(int code) {
        this.exitCode = code;
    }

    public int code() {
        return exitCode;
    }
    
    @Override
    public String toString() {
        return "Exit code: " + this.name() + "(" + this.code() + ")";
    }
}
