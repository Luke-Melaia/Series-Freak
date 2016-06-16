/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lm.seriesfreak.ui.window.crash;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import net.lm.seriesfreak.ExitCode;
import net.lm.seriesfreak.ui.language.alert.LAlert;
import net.lm.seriesfreak.ui.window.Controller;
import net.lm.seriesfreak.util.LoggingUtils;

/**
 * CrashWindow controller class
 *
 * @author Luke Melaia
 */
public class CrashWindowController extends Controller implements Initializable {

    /**
     * The text area that displays the error report.
     */
    @FXML
    private TextArea errorTextArea;

    private LAlert reportFailed = new LAlert(Alert.AlertType.ERROR).setKey("report_request_failed").register();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Closes the application with the {@link ExitCode#UNKNOWN_FAILURE} exit
     * code.
     *
     * @param event
     */
    @FXML
    private void onClose(ActionEvent event) {
        this.window.close();
    }

    /**
     * Reboots the application.
     *
     * @param event
     */
    @FXML
    private void onReboot(ActionEvent event) {
        //TODO: Reboot application
    }

    /**
     * Opens the error report file.
     *
     * @param event
     */
    @FXML
    private void onReportRequest(ActionEvent event) {
        try {
            Desktop.getDesktop().open(
                    LoggingUtils.getErrorFile());
        } catch (IOException ex) {
            reportFailed.showAndWait();
        }
    }

    /**
     * @return the text area that displays the error report.
     */
    public TextArea getErrorTextArea() {
        return errorTextArea;
    }

    @Override
    public void onInit() {
        
    }

}
