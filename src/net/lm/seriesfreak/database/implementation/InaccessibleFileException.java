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

/**
 *
 * @author Luke Melaia
 */
public class InaccessibleFileException extends Exception {

    public InaccessibleFileException() {
        super();
    }

    public InaccessibleFileException(String message) {
        super(message);
    }

    public InaccessibleFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public InaccessibleFileException(Throwable cause) {
        super(cause);
    }

    protected InaccessibleFileException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
