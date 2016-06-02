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

import com.sun.javafx.robot.impl.FXRobotHelper;
import java.awt.Window;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.swing.JFrame;
import net.lm.seriesfreak.database.implementation.CorruptFileException;
import net.lm.seriesfreak.database.implementation.DatabaseHandler;
import net.lm.seriesfreak.database.implementation.InaccessibleFileException;
import net.lm.seriesfreak.ui.alert.CrashAlert;
import net.lm.seriesfreak.ui.language.LanguageLoader;
import net.lm.seriesfreak.ui.language.LanguageRegistry;
import net.lm.seriesfreak.ui.language.alert.LAlert;
import net.lm.seriesfreak.ui.window.main.MainWindow;
import net.lm.seriesfreak.ui.window.update.UpdateWindow;
import net.lm.seriesfreak.ui.window.WindowLoader;
import net.lm.seriesfreak.ui.window.loading.LoadingWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * 
 * @author Luke Melaia
 */
public final class Application {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private static Alert corruptAlert = new LAlert(Alert.AlertType.WARNING).setKey("corrupt").register();
    
    private static Alert inaccessibleAlert = new LAlert(Alert.AlertType.WARNING).setKey("inaccessible_loading").register();
    
    private static Alert unknownAlert = new LAlert(Alert.AlertType.WARNING).setKey("unknown_io_loading").register();
    
    private DatabaseHandler database;

    private WindowLoader<UpdateWindow> updateWindowLoader;

    private WindowLoader<MainWindow> mainWindowLoader;

    public Application() {
        log.info("Application initialized");
    }

    public void prepare() {
        log.info("Preparing application");
        setupExceptionHandler();
        logPcInfo();
        initLanguageRegistry();
        initDatabase();
        initWindows();
    }

    public void start() {
        log.info("Starting application");
        mainWindowLoader.get().setTitle("Series Freak");
        mainWindowLoader.get().show();

        String fileName = Preferences.get("save_file", "");

        if (fileName != null && !fileName.equals("")) {
            //TODO: Find a better place to put this.
            try {
                database.open(fileName);
            } catch (CorruptFileException ex) {
                corruptAlert.showAndWait();
            } catch (InaccessibleFileException ex) {
                inaccessibleAlert.showAndWait();
            } catch (IOException ex) {
                unknownAlert.showAndWait();
            }
        }
        closeSplash();
        System.gc();
    }

    private void closeSplash(){
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setVisible(false);
    }
    
    @Override
    public String toString() {
        return "Series Freak application class";
    }

    public static void exit(ExitCode code) {
        if (code == null) {
            return;
        }

        log.info(code);
        Preferences.set("language", LanguageRegistry.getInstance().getCurrentLanguage().getName());
        Preferences.savePreferences();
        LoggerContext context = (LoggerContext) LogManager.getContext();
        Configurator.shutdown(context);
        System.exit(code.code());
    }
    
    public static void crash(Throwable throwable) {
        log.fatal("Application crashing due to exception", throwable);
        if (throwable == null) {
            exit(ExitCode.UNKNOWN_FAILURE);
        }

        Alert crash = new CrashAlert(throwable);
        crash.setOnCloseRequest(event -> exit(ExitCode.UNKNOWN_FAILURE));
        crash.show();

        //I add the windows to a separate list
        //and then close them from that list
        //because a ConcurrentModificationException
        //gets thrown otherwise.
        List<Stage> stages = new ArrayList();
        FXRobotHelper.getStages().forEach(stage -> stages.add(stage));
        stages.forEach(stage -> stage.hide());
        
        List<Window> windows = new ArrayList();
        Arrays.asList(Window.getWindows()).forEach(window -> windows.add(window));
        windows.forEach(window -> window.setVisible(false));
    }

    private void initLanguageRegistry() {
        String _default = "English";
        LanguageRegistry.getInstance().loadAll(new LanguageLoader("resources", "lang").getLanguages());

        try {
            LanguageRegistry.getInstance().selectLanguage(Preferences.get("language", _default));
        } catch (IllegalArgumentException ex) {
            LanguageRegistry.getInstance().selectLanguage(_default);
        }
    }

    private void logPcInfo() {
        log.info("Runtime info:");
        log.info("User name: " + System.getProperty("user.name"));
        log.info("Operating system: " + System.getProperty("os.name"));
        log.info("Operating system version: " + System.getProperty("os.version"));
        log.info("Java version: " + System.getProperty("java.version"));
        log.info("Run directory: " + System.getProperty("user.dir"));

    }

    private void setupExceptionHandler() {
        Thread.UncaughtExceptionHandler handler = (Thread t, Throwable e) -> {
            crash(e);
        };

        Thread.setDefaultUncaughtExceptionHandler(handler);
        Thread.currentThread().setUncaughtExceptionHandler(handler);
    }

    private void initDatabase() {
        this.database = new DatabaseHandler(0);
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    private void initWindows() {
        this.updateWindowLoader = new WindowLoader<>(new UpdateWindow(database));
        this.mainWindowLoader = new WindowLoader<>(new MainWindow(updateWindowLoader, database));
        LoadingWindow.start();
    }
}
