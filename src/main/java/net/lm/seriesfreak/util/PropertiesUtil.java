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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Luke Melaia
 */
public class PropertiesUtil {

    public static void load(Properties props, File file) throws IOException {
        try (InputStream reader = new FileInputStream(file)) {
            props.load(reader);
            reader.close();
        }
    }

    public static void save(Properties props, File file, String comment) throws IOException {
        try (OutputStream stream = new FileOutputStream(file)) {
            props.store(stream, comment);
            stream.close();
        }
    }
}
