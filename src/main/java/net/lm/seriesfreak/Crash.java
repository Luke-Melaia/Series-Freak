/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.lm.seriesfreak;

import com.sun.glass.ui.Window;
import com.sun.javafx.robot.impl.FXRobotHelper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;
import net.lm.seriesfreak.ui.window.FxmlWindow;
import net.lm.seriesfreak.ui.window.WindowLoader;
import net.lm.seriesfreak.ui.window.impls.CrashWindow;
import net.lm.seriesfreak.util.LoggingUtils;

/**
 * A crash utility class.
 *
 * @author Luke Melaia
 */
public class Crash {

    /**
     * Apache logger.
     */
    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    /**
     * The crash window loader.
     */
    private static WindowLoader<CrashWindow> crashWindowLoader
            = new WindowLoader<>(new CrashWindow());

    private Crash() {
    }

    /**
     * @return a default exception handler configured to crash the application
     * correctly.
     */
    public static Thread.UncaughtExceptionHandler getExceptionHandler() {
        return (Thread t, Throwable e) -> {
            crashWithWindow("An exception went uncaught on thread: "
                    + t.getName(), e);
        };
    }

    /**
     * Sets the default exception handler to: {@link #getExceptionHandler() }.
     */
    public static void setExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(getExceptionHandler());
    }

    /**
     * Crashes the application with an error window.
     *
     * @param info the information message
     * @param ex the cause of the crash.
     */
    public static void crashWithWindow(String info, Throwable ex) {
        crashWithoutWindow(info, ex);
        showCrashScreen(info, ex);
    }

    /**
     * Crashes the application without displaying a window.
     *
     * @param info the information message
     * @param ex the cause of the crash
     */
    public static void crashWithoutWindow(String info, Throwable ex) {
        //TODO: Close application.
        closeActiveWindows();
        if (info == null && ex == null) {
            throw new NullPointerException("info and exception cannot both be null");
        }

        //FIXME: not localized...
        log.fatal("Series Freak has crashed");
        log.info("The information message is: "
                + ((info != null) ? info : "None"));
        log.info("The cause is: ", ex);
        
        try {
            log.info("The error file is: " + LoggingUtils.getErrorFile());
        } catch (IOException ex1) {
            log.error("Coundn't get error file.", ex1);
        }
    }

    /**
     * Shows the crash window.
     *
     * @param title The title of the window. This can't be null.
     * @param info Information about the crash.
     * @param ex Reason for the crash.
     */
    private static void showCrashScreen(String info, Throwable ex) {
        try {
            showWindow(info, ex, LoggingUtils.getErrorFile());
        } catch (IOException ex1) {
            //Can't get to here.
        }
    }

    /**
     * Displays the crash window if it's not already being displayed.
     *
     * @param text the error text.
     */
    private static void showWindow(String message, Throwable cause, File errorFile) {
        CrashWindow window;
        
        try {
            window = crashWindowLoader.get();
        } catch (FxmlWindow.WindowLoadException ex) {
            log.fatal("Couldn't intialize crash window", ex);
            crashWithoutWindow("Couldn't initialize crash window", ex);
            return;
        }

        if (window.isShowing()) {
            return;
        }

        window.setErrorMessage(message);
        window.setError(cause);
        window.setErrorFile(errorFile);
        window.show();
    }

    /**
     * Closes all the active JAVAFX and swing windows.
     *
     * This should be called before showing the crash window.
     */
    private static void closeActiveWindows() {
        //This is done because a
        //ConcurrentModificationException
        //gets thrown if done the logical way...
        List<Stage> stages = new ArrayList<>();
        FXRobotHelper.getStages().forEach(stage -> stages.add(stage));
        stages.forEach(stage -> stage.close());
        
        Window.getWindows().forEach(window -> window.setVisible(false));
    }
}