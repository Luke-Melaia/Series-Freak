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

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Luke Melaia
 */
public class ImageHelper {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private static final String IMAGE_PATH = "images/";

    private ImageHelper() {
    }

    public static ImageView getImage(String name) {
        return new ImageView(new Image(ClassLoader.getSystemResourceAsStream(IMAGE_PATH + name + ".png")));
    }

    public static Image getApplicationIcon() {
        return new Image(ClassLoader.getSystemResourceAsStream(IMAGE_PATH + "icon.png"));
    }
}
