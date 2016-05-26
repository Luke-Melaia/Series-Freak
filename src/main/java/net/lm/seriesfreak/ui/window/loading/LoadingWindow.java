/*
 * Copyright 2015 Luke Melaia.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.lm.seriesfreak.ui.window.loading;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javax.swing.ImageIcon;
import net.lm.seriesfreak.database.implementation.ProgressMonitor;

/**
 *
 * @author Luke Melaia
 */
public final class LoadingWindow extends javax.swing.JFrame {

    public static void start() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                LoadingWindow loadingWindow = new LoadingWindow();
            }
        };

        thread.start();
    }

    private LoadingWindow() {
        this.setIconImage(new ImageIcon(ClassLoader.getSystemResource("images/icon.png")).getImage());

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            //This will never happen
        }

        ProgressMonitor.addProgressListener(progressListener);
        ProgressMonitor.addStateListener(stateListener);
        initComponents();
    }

    private ChangeListener<ProgressMonitor.ProgressObject> progressListener
            = (ObservableValue<? extends ProgressMonitor.ProgressObject> observable, ProgressMonitor.ProgressObject oldValue, ProgressMonitor.ProgressObject newValue) -> {
                this.jProgressBar1.setValue(newValue.getValue());
                this.jProgressBar1.setMaximum(newValue.getMax());
                this.jProgressBar1.setString(newValue.getInformation());
            };

    private ChangeListener<Boolean> stateListener
            = (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                this.setVisible(newValue);
            };


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();

        setAlwaysOnTop(true);
        setResizable(false);

        jProgressBar1.setString("");
        jProgressBar1.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
