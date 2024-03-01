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

import java.awt.BorderLayout;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import org.exbin.bined.extended.theme.ExtendedBackgroundPaintMode;
import org.exbin.bined.swing.extended.ExtCodeArea;
import org.exbin.bined.swing.extended.layout.ExtendedCodeAreaDecorations;
import org.exbin.bined.swing.extended.theme.ExtendedCodeAreaThemeProfile;
import org.exbin.framework.App;
import org.exbin.framework.language.api.LanguageModuleApi;
import org.exbin.framework.utils.TestApplication;
import org.exbin.framework.utils.WindowUtils;

/**
 * Theme profile panel.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class ThemeProfilePanel extends javax.swing.JPanel {

    private final java.util.ResourceBundle resourceBundle = App.getModule(LanguageModuleApi.class).getBundle(ThemeProfilePanel.class);

    private final PreviewPanel previewPanel = new PreviewPanel();

    public ThemeProfilePanel() {
        initComponents();
        init();
    }

    private void init() {
        add(previewPanel, BorderLayout.CENTER);
    }

    @Nonnull
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setBackgroundModes(List<String> backgroundModes) {
        for (String backgroundMode : backgroundModes) {
            backgroundModeComboBox.addItem(backgroundMode);
        }
    }

    @Nonnull
    public ExtendedCodeAreaThemeProfile getThemeProfile() {
        ExtCodeArea codeArea = previewPanel.getCodeArea();
        ExtendedCodeAreaThemeProfile themeProfile = codeArea.getThemeProfile();
        return Objects.requireNonNull(themeProfile).createCopy();
    }

    public void setThemeProfile(ExtendedCodeAreaThemeProfile themeProfile) {
        updateThemeProfile(themeProfile);
        backgroundModeComboBox.setSelectedIndex(themeProfile.getBackgroundPaintMode().ordinal());
        paintRowPosBackgroundCheckBox.setSelected(themeProfile.isPaintRowPosBackground());
        decoratorHeaderLineCheckBox.setSelected(themeProfile.hasDecoration(ExtendedCodeAreaDecorations.HEADER_LINE));
        decoratorRowPosLineCheckBox.setSelected(themeProfile.hasDecoration(ExtendedCodeAreaDecorations.ROW_POSITION_LINE));
        decoratorSplitLineCheckBox.setSelected(themeProfile.hasDecoration(ExtendedCodeAreaDecorations.SPLIT_LINE));
        decoratorBoxCheckBox.setSelected(themeProfile.hasDecoration(ExtendedCodeAreaDecorations.BOX_LINES));
        verticalLineByteGroupSizeSpinner.setValue(themeProfile.getVerticalLineByteGroupSize());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        preferencesScrollPane = new javax.swing.JScrollPane();
        preferencesPanel = new javax.swing.JPanel();
        backgroundModeLabel = new javax.swing.JLabel();
        backgroundModeComboBox = new javax.swing.JComboBox<>();
        paintRowPosBackgroundCheckBox = new javax.swing.JCheckBox();
        linesPanel = new javax.swing.JPanel();
        decoratorRowPosLineCheckBox = new javax.swing.JCheckBox();
        decoratorSplitLineCheckBox = new javax.swing.JCheckBox();
        decoratorBoxCheckBox = new javax.swing.JCheckBox();
        decoratorHeaderLineCheckBox = new javax.swing.JCheckBox();
        verticalLineByteGroupSizeSpinner = new javax.swing.JSpinner();
        verticalLineByteGroupSizeLabel = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        backgroundModeLabel.setText(resourceBundle.getString("backgroundModeLabel.text")); // NOI18N

        backgroundModeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backgroundModeComboBoxActionPerformed(evt);
            }
        });

        paintRowPosBackgroundCheckBox.setText(resourceBundle.getString("paintRowPosBackgroundCheckBox.text")); // NOI18N
        paintRowPosBackgroundCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                paintRowPosBackgroundCheckBoxItemStateChanged(evt);
            }
        });

        linesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceBundle.getString("linesPanel.border.title"))); // NOI18N

        decoratorRowPosLineCheckBox.setText(resourceBundle.getString("decoratorRowPosLineCheckBox.text")); // NOI18N
        decoratorRowPosLineCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                decoratorRowPosLineCheckBoxItemStateChanged(evt);
            }
        });

        decoratorSplitLineCheckBox.setText(resourceBundle.getString("decoratorSplitLineCheckBox.text")); // NOI18N
        decoratorSplitLineCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                decoratorSplitLineCheckBoxItemStateChanged(evt);
            }
        });

        decoratorBoxCheckBox.setText(resourceBundle.getString("decoratorBoxCheckBox.text")); // NOI18N
        decoratorBoxCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                decoratorBoxCheckBoxItemStateChanged(evt);
            }
        });

        decoratorHeaderLineCheckBox.setText(resourceBundle.getString("decoratorHeaderLineCheckBox.text")); // NOI18N
        decoratorHeaderLineCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                decoratorHeaderLineCheckBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout linesPanelLayout = new javax.swing.GroupLayout(linesPanel);
        linesPanel.setLayout(linesPanelLayout);
        linesPanelLayout.setHorizontalGroup(
            linesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(linesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(linesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(decoratorRowPosLineCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(decoratorSplitLineCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(decoratorBoxCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(decoratorHeaderLineCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        linesPanelLayout.setVerticalGroup(
            linesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(linesPanelLayout.createSequentialGroup()
                .addComponent(decoratorHeaderLineCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(decoratorRowPosLineCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(decoratorSplitLineCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(decoratorBoxCheckBox))
        );

        verticalLineByteGroupSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        verticalLineByteGroupSizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                verticalLineByteGroupSizeSpinnerStateChanged(evt);
            }
        });

        verticalLineByteGroupSizeLabel.setText(resourceBundle.getString("verticalLineByteGroupSizeLabel.text")); // NOI18N

        javax.swing.GroupLayout preferencesPanelLayout = new javax.swing.GroupLayout(preferencesPanel);
        preferencesPanel.setLayout(preferencesPanelLayout);
        preferencesPanelLayout.setHorizontalGroup(
            preferencesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(preferencesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(preferencesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paintRowPosBackgroundCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backgroundModeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(linesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(verticalLineByteGroupSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(preferencesPanelLayout.createSequentialGroup()
                        .addGroup(preferencesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(backgroundModeLabel)
                            .addComponent(verticalLineByteGroupSizeLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        preferencesPanelLayout.setVerticalGroup(
            preferencesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(preferencesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backgroundModeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(backgroundModeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paintRowPosBackgroundCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(linesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verticalLineByteGroupSizeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verticalLineByteGroupSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(358, Short.MAX_VALUE))
        );

        preferencesScrollPane.setViewportView(preferencesPanel);

        add(preferencesScrollPane, java.awt.BorderLayout.WEST);
    }// </editor-fold>//GEN-END:initComponents

    private void backgroundModeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backgroundModeComboBoxActionPerformed
        ExtendedBackgroundPaintMode backgroundPaintMode = ExtendedBackgroundPaintMode.values()[backgroundModeComboBox.getSelectedIndex()];
        ExtendedCodeAreaThemeProfile themeProfile = getThemeProfile();
        themeProfile.setBackgroundPaintMode(backgroundPaintMode);
        updateThemeProfile(themeProfile);
    }//GEN-LAST:event_backgroundModeComboBoxActionPerformed

    private void paintRowPosBackgroundCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_paintRowPosBackgroundCheckBoxItemStateChanged
        boolean selected = paintRowPosBackgroundCheckBox.isSelected();
        ExtendedCodeAreaThemeProfile themeProfile = getThemeProfile();
        themeProfile.setPaintRowPosBackground(selected);
        updateThemeProfile(themeProfile);
    }//GEN-LAST:event_paintRowPosBackgroundCheckBoxItemStateChanged

    private void decoratorRowPosLineCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_decoratorRowPosLineCheckBoxItemStateChanged
        boolean selected = decoratorRowPosLineCheckBox.isSelected();
        ExtendedCodeAreaThemeProfile themeProfile = getThemeProfile();
        themeProfile.setDecoration(ExtendedCodeAreaDecorations.ROW_POSITION_LINE, selected);
        updateThemeProfile(themeProfile);
    }//GEN-LAST:event_decoratorRowPosLineCheckBoxItemStateChanged

    private void decoratorSplitLineCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_decoratorSplitLineCheckBoxItemStateChanged
        boolean selected = decoratorSplitLineCheckBox.isSelected();
        ExtendedCodeAreaThemeProfile themeProfile = getThemeProfile();
        themeProfile.setDecoration(ExtendedCodeAreaDecorations.SPLIT_LINE, selected);
        updateThemeProfile(themeProfile);
    }//GEN-LAST:event_decoratorSplitLineCheckBoxItemStateChanged

    private void decoratorBoxCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_decoratorBoxCheckBoxItemStateChanged
        boolean selected = decoratorBoxCheckBox.isSelected();
        ExtendedCodeAreaThemeProfile themeProfile = getThemeProfile();
        themeProfile.setDecoration(ExtendedCodeAreaDecorations.BOX_LINES, selected);
        updateThemeProfile(themeProfile);
    }//GEN-LAST:event_decoratorBoxCheckBoxItemStateChanged

    private void decoratorHeaderLineCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_decoratorHeaderLineCheckBoxItemStateChanged
        boolean selected = decoratorHeaderLineCheckBox.isSelected();
        ExtendedCodeAreaThemeProfile themeProfile = getThemeProfile();
        themeProfile.setDecoration(ExtendedCodeAreaDecorations.HEADER_LINE, selected);
        updateThemeProfile(themeProfile);
    }//GEN-LAST:event_decoratorHeaderLineCheckBoxItemStateChanged

    private void verticalLineByteGroupSizeSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_verticalLineByteGroupSizeSpinnerStateChanged
        int byteGroupSize = (Integer) verticalLineByteGroupSizeSpinner.getValue();
        ExtendedCodeAreaThemeProfile themeProfile = getThemeProfile();
        themeProfile.setVerticalLineByteGroupSize(byteGroupSize);
        updateThemeProfile(themeProfile);
    }//GEN-LAST:event_verticalLineByteGroupSizeSpinnerStateChanged

    private void updateThemeProfile(ExtendedCodeAreaThemeProfile themeProfile) {
        ExtCodeArea codeArea = previewPanel.getCodeArea();
        codeArea.setThemeProfile(themeProfile);
    }

    /**
     * Test method for this panel.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestApplication.run(() -> WindowUtils.invokeWindow(new ThemeProfilePanel()));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> backgroundModeComboBox;
    private javax.swing.JLabel backgroundModeLabel;
    private javax.swing.JCheckBox decoratorBoxCheckBox;
    private javax.swing.JCheckBox decoratorHeaderLineCheckBox;
    private javax.swing.JCheckBox decoratorRowPosLineCheckBox;
    private javax.swing.JCheckBox decoratorSplitLineCheckBox;
    private javax.swing.JPanel linesPanel;
    private javax.swing.JCheckBox paintRowPosBackgroundCheckBox;
    private javax.swing.JPanel preferencesPanel;
    private javax.swing.JScrollPane preferencesScrollPane;
    private javax.swing.JLabel verticalLineByteGroupSizeLabel;
    private javax.swing.JSpinner verticalLineByteGroupSizeSpinner;
    // End of variables declaration//GEN-END:variables
}
