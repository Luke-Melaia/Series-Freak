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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Luke Melaia
 */
public class RandomUtils {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private static final Date DATE = new Date();

    public static String getCurrentDate() {
        return DATE_FORMAT.format(DATE);
    }

    public static int getDateAsInt(String date) {

        String[] numbers = date.split("/");
        String reverseDate = numbers[2] + numbers[1] + numbers[0];
        return Integer.parseInt(reverseDate);
    }

    public static String getOrdinalIndicator(int number) {
        String ordinal;

        switch (String.valueOf(number).toCharArray()[String.valueOf(number).length() - 1]) {
            case '1':
                ordinal = "st";
                break;
            case '2':
                ordinal = "nd";
                break;
            case '3':
                ordinal = "rd";
                break;
            default:
                ordinal = "th";
        }

        return number + ordinal;
    }
    
    public static double getProgress(int value, int total){
        return (double)value / total;
    }
}
