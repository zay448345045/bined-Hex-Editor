/*
 * Copyright (C) ExBin Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.exbin.framework.bined.tool.content.gui;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.JButton;
import org.exbin.framework.App;
import org.exbin.framework.language.api.LanguageModuleApi;
import org.exbin.framework.utils.OkCancelListener;
import org.exbin.framework.utils.TestApplication;
import org.exbin.framework.utils.WindowUtils;
import org.exbin.framework.utils.UiUtils;
import org.exbin.framework.utils.UtilsModule;

/**
 * Clipboard content control panel.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class ClipboardContentControlPanel extends javax.swing.JPanel implements ClipboardContentControlHandler.ClipboardContentControlService {

    private final java.util.ResourceBundle resourceBundle;
    private ClipboardContentControlHandler handler;
    private OkCancelListener okCancelListener;

    public ClipboardContentControlPanel() {
        this(App.getModule(LanguageModuleApi.class).getBundle(ClipboardContentControlPanel.class));
        initComponents();
        init();
    }

    public ClipboardContentControlPanel(java.util.ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        initComponents();
        init();
    }

    private void init() {
        okCancelListener = new OkCancelListener() {
            @Override
            public void okEvent() {
                performClick(ClipboardContentControlHandler.ControlActionType.CLOSE);
            }

            @Override
            public void cancelEvent() {
                performClick(ClipboardContentControlHandler.ControlActionType.CLOSE);
            }
        };
    }

    public void setHandler(ClipboardContentControlHandler handler) {
        this.handler = handler;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        refreshButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        refreshButton.setText(resourceBundle.getString("refreshButton.text")); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        closeButton.setText(resourceBundle.getString("closeButton.text")); // NOI18N
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(refreshButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closeButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(refreshButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        if (handler != null) {
            handler.controlActionPerformed(ClipboardContentControlHandler.ControlActionType.REFRESH);
        }
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        if (handler != null) {
            handler.controlActionPerformed(ClipboardContentControlHandler.ControlActionType.CLOSE);
        }
    }//GEN-LAST:event_closeButtonActionPerformed

    @Override
    public void performClick(ClipboardContentControlHandler.ControlActionType actionType) {
        switch (actionType) {
            case REFRESH: {
                UiUtils.doButtonClick(refreshButton);
                break;
            }
            case CLOSE: {
                UiUtils.doButtonClick(closeButton);
            }
        }
    }

    @Nonnull
    @Override
    public Optional<JButton> getDefaultButton() {
        return Optional.of(closeButton);
    }

    @Nonnull
    @Override
    public OkCancelListener getOkCancelListener() {
        return okCancelListener;
    }

    /**
     * Test method for this panel.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestApplication testApplication = UtilsModule.createTestApplication();
        testApplication.launch(() -> {
            testApplication.addModule(org.exbin.framework.language.api.LanguageModuleApi.MODULE_ID, new org.exbin.framework.language.api.utils.TestLanguageModule());
            WindowUtils.invokeWindow(new ClipboardContentControlPanel());
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JButton refreshButton;
    // End of variables declaration//GEN-END:variables

}
