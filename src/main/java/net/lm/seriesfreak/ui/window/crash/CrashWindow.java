/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lm.seriesfreak.ui.window.crash;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import net.lm.seriesfreak.Application;
import net.lm.seriesfreak.ExitCode;
import net.lm.seriesfreak.ui.window.FxmlWindow;

/**
 * The window displayed to the user when the application crashes.
 *
 * @author Luke Melaia
 */
public class CrashWindow extends FxmlWindow<CrashWindowController> {

    /**
     * The crash message.
     */
    private String message;

    /**
     * The reason for the crash.
     */
    private Throwable cause;

    /**
     * The file the error report was saved to.
     */
    private File errorFile;

    /**
     * Default constructor.
     */
    public CrashWindow() {
        super("Series Freak", "CrashWindow", 600, 500);
    }

    /**
     * Sets the error message.
     *
     * @param message
     */
    public void setErrorMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the reason for the crash.
     *
     * @param throwable
     */
    public void setError(Throwable throwable) {
        this.cause = throwable;
    }

    /**
     * Sets the file the error report was saved to.
     *
     * @param file
     */
    public void setErrorFile(File file) {
        this.errorFile = file;
    }

    /**
     * Sets the windows close operation.
     */
    @Override
    public void initProperties() {
        this.setOnCloseRequest(request -> {
            this.close();
        });
    }

    /**
     * Further initialization of the window components.
     */
    @Override
    public void initComponents() {
        this.setOnShowing(event -> {
            StringWriter writer = null;

            if (cause != null) {
                writer = new StringWriter();
                PrintWriter writer2 = new PrintWriter(writer);
                cause.printStackTrace(writer2);
            }

            this.controller.getErrorTextArea().setText(
                    new StringBuilder()
                    .append("Series Freak has crashed!\n\n")
                    .append((message == null) ? "No error message was given.\n"
                                    : "The error message is: " + message + "\n")
                    .append((writer == null) ? "No exception was given.\n"
                                    : "The exception type given was: "
                                    + cause.toString() + "\n"
                                    + "Stacktrace: \n" + writer.toString() + "\n")
                    .append("Running Thread: ")
                    .append(Thread.currentThread().toString())
                    .append("\n\n")
                    .append((errorFile == null) ? "The error file is unavailable."
                                    : "The error file is: " + errorFile.getAbsolutePath())
                    .toString()
            );
        });

    }

    /**
     * Unused.
     */
    @Override
    public void addComponents() {
        
    }

    /**
     * Closes the window.
     */
    @Override
    public void destroy() {
        this.close();
    }

    /**
     * Hides the window and exits the application.
     */
    @Override
    public void close() {
        super.close();
        Application.exit(ExitCode.UNKNOWN_FAILURE);
    }

}
