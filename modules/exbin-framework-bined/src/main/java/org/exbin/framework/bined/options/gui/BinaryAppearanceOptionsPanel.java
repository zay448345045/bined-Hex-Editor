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
package org.exbin.framework.bined.options.gui;

import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import org.exbin.framework.App;
import org.exbin.framework.bined.options.impl.BinaryAppearanceOptionsImpl;
import org.exbin.framework.language.api.LanguageModuleApi;
import org.exbin.framework.utils.WindowUtils;
import org.exbin.framework.options.api.OptionsModifiedListener;
import org.exbin.framework.options.api.OptionsComponent;
import org.exbin.framework.utils.TestApplication;

/**
 * Binary viewer/editor appearance options panel.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class BinaryAppearanceOptionsPanel extends javax.swing.JPanel implements OptionsComponent<BinaryAppearanceOptionsImpl> {

    private OptionsModifiedListener optionsModifiedListener;
    private final ResourceBundle resourceBundle = App.getModule(LanguageModuleApi.class).getBundle(BinaryAppearanceOptionsPanel.class);

    public BinaryAppearanceOptionsPanel() {
        initComponents();
    }

    @Nonnull
    @Override
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    @Override
    public void loadFromOptions(BinaryAppearanceOptionsImpl options) {
        multiFileModeCheckBox.setSelected(options.isMultiFileMode());
    }

    @Override
    public void saveToOptions(BinaryAppearanceOptionsImpl options) {
        options.setMultiFileMode(multiFileModeCheckBox.isSelected());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        multiFileModeCheckBox = new javax.swing.JCheckBox();

        setName("Form"); // NOI18N

        multiFileModeCheckBox.setSelected(true);
        multiFileModeCheckBox.setText(resourceBundle.getString("multiFileModeCheckBox.text")); // NOI18N
        multiFileModeCheckBox.setName("multiFileModeCheckBox"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(multiFileModeCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(multiFileModeCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Test method for this panel.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestApplication.run(() -> WindowUtils.invokeWindow(new BinaryAppearanceOptionsPanel()));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox multiFileModeCheckBox;
    // End of variables declaration//GEN-END:variables

    private void setModified(boolean modified) {
        if (optionsModifiedListener != null) {
            optionsModifiedListener.wasModified();
        }
    }

    @Override
    public void setOptionsModifiedListener(OptionsModifiedListener listener) {
        optionsModifiedListener = listener;
    }
}
