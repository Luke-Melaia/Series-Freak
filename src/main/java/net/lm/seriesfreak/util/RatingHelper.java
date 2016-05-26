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

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import net.lm.seriesfreak.ui.language.node.LTextItem;

/**
 *
 * @author Luke Melaia
 */
public class RatingHelper {

    public static final double[] RATINGS_ALL = {
        10.0, 9.9, 9.8, 9.7, 9.6, 9.5, 9.4, 9.3, 9.2, 9.1,
        9.0, 8.9, 8.8, 8.7, 8.6, 8.5, 8.4, 8.3, 8.2, 8.1,
        8.0, 7.9, 7.8, 7.7, 7.6, 7.5, 7.4, 7.3, 7.2, 7.1,
        7.0, 6.9, 6.8, 6.7, 6.6, 6.5, 6.4, 6.3, 6.2, 6.1,
        6.0, 5.9, 5.8, 5.7, 5.6, 5.5, 5.4, 5.3, 5.2, 5.1,
        5.0, 4.9, 4.8, 4.7, 4.6, 4.5, 4.4, 4.3, 4.2, 4.1,
        4.0, 3.9, 3.8, 3.7, 3.6, 3.5, 3.4, 3.3, 3.2, 3.1,
        3.0, 3.9, 3.8, 3.7, 3.6, 3.5, 3.4, 3.3, 3.2, 3.1,
        3.0, 2.9, 2.8, 2.7, 2.6, 2.5, 2.4, 2.3, 2.2, 2.1,
        2.0, 1.9, 1.8, 1.7, 1.6, 1.5, 1.4, 1.3, 1.2, 1.1,
        1.0, 0.0
    };

    public static final double[] RATINGS_USABLE = Arrays.copyOfRange(RATINGS_ALL, 0, RATINGS_ALL.length - 1);

    public static final Collection<Double> RATINGS_USABLE_AS_COLLECTION;

    static {
        RATINGS_USABLE_AS_COLLECTION = new LinkedList<>();

        for (Double rating : RATINGS_USABLE) {
            RATINGS_USABLE_AS_COLLECTION.add(rating);
        }
    }

    private static final LTextItem incompleteText = new LTextItem("incomplete", null).register();

    public static String[] getTextualRatings() {
        String[] _return = new String[RATINGS_ALL.length];

        for (int i = 0; i < _return.length; i++) {
            String add = String.valueOf(RATINGS_ALL[i]);

            if (add.equals("0.0")) {
                add = incompleteText.getText();
            }

            _return[i] = add;
        }

        return _return;
    }
}
