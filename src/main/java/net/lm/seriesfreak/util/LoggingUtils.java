/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.lm.seriesfreak.util;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 * A set of utilities for logging.
 *
 * @author Luke Melaia
 */
public class LoggingUtils {

    /**
     * The default log file.
     */
    public static final File LOG_FILE = new File(
            System.getProperty("user.dir") + "/logs/Series Freak.log"
    );

    /**
     * The error log file. Isn't created until its fetched.
     */
    private static final File ERROR_FILE;

    static {
        File error;
        int errorFileNumber = 1;

        for (;;) {
            error = new File(
                    System.getProperty("user.dir") + "/logs/Series Freak - "
                    + "Error " + errorFileNumber + ".log"
            );

            if (error.exists()) {
                errorFileNumber++;
            } else {
                break;
            }
        }

        ERROR_FILE = error;
    }

    /**
     * Creates the error log file and copies the contents from the log file.
     *
     * @return the error log file.
     * @throws IOException if the file cannot be copied or created.
     */
    public static File getErrorFile() throws IOException {
        if (!ERROR_FILE.exists()) {
            ERROR_FILE.createNewFile();
        }

        org.apache.log4j.LogManager.shutdown();
        FileUtils.copyFile(LOG_FILE, ERROR_FILE);

        return ERROR_FILE;
    }
}