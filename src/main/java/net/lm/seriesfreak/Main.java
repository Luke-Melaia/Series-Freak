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

import javafx.application.Application;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Luke Melaia
 */
public final class Main extends Application {

    public Main() {
    }

    public static void main(String... args) {
        int javaVersion;
        String sJavaVersion = System.getProperty("java.version");

        javaVersion = Integer.parseInt(sJavaVersion.replace(".", "").replace("_", ""));

        if (javaVersion < 18065) {//Check java version before anything else.
            JOptionPane.showMessageDialog(null,
                    "Minimum java version required is incorrect.\n"
                    + "You need: 1.8.0_65\n"
                    + "You have: " + sJavaVersion + "\n"
                    + "Please download the correct version and try again.",
                    "Java Version Too Low", JOptionPane.ERROR_MESSAGE);
            System.exit(-2);
        }

        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        try {
            net.lm.seriesfreak.Application app = new net.lm.seriesfreak.Application();
            app.prepare();
            app.start();
        } catch (Exception e) {
            net.lm.seriesfreak.Application.crash(e);
        }
    }

    @Override
    public String toString() {
        return "Series Freak main class";
    }
}
