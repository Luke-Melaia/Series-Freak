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

import com.davekoelle.alphanum.AlphanumComparator;
import java.util.Comparator;
import net.lm.seriesfreak.ui.node.TextualProgress;

/**
 *
 * @author Luke Melaia
 */
public final class Comparators {

    private Comparators() {
    }

    public static final Comparator<String> ALPHANUM_COMPARATOR = new AlphanumComparator() {

        @Override
        public int compare(Object o1, Object o2) {
            String s1 = (String) o1;
            String s2 = (String) o2;

            if (s1 != null && s2 == null) {
                return -1;
            }
            if (s1 == null && s2 == null) {
                return 0;
            }
            if (s1 == null && s2 != null) {
                return 1;
            }

            return super.compare(s1, s2);
        }

    };

    public static final Comparator<String> DATE_SORT_COMPARATOR = (String date1, String date2) -> {
        int iDate1;
        try {
            iDate1 = RandomUtils.getDateAsInt(date1);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            iDate1 = Integer.MIN_VALUE;
        }

        int iDate2;
        try {
            iDate2 = RandomUtils.getDateAsInt(date2);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            iDate2 = Integer.MIN_VALUE;
        }

        return iDate2 - iDate1;
    };

    public static final Comparator<String> RATING_SORT_COMPARATOR = (String s1, String s2) -> {

        double rating1;

        try {
            rating1 = Double.parseDouble(s1);
        } catch (NumberFormatException numberFormatException) {
            rating1 = 11;
        }

        double rating2;
        try {
            rating2 = Double.parseDouble(s2);
        } catch (NumberFormatException numberFormatException) {
            rating2 = 11;
        }

        if (rating1 == rating2) {
            return 0;
        }

        return (rating1 < rating2) ? 1 : -1;
    };

    public static final Comparator<TextualProgress> PROGRESS_SORT_COMPARATOR = (TextualProgress p1, TextualProgress p2) -> {
        double progress1 = p1.getProgress();
        double progress2 = p2.getProgress();

        if (progress1 == progress2) {
            return 0;
        }

        return (progress1 - progress2 < 0) ? 1 : -1;
    };
}
