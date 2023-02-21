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
package org.exbin.framework.bined.gui;

import java.net.URI;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.DefaultListModel;
import org.exbin.auxiliary.paged_data.delta.DataSegment;
import org.exbin.auxiliary.paged_data.delta.DeltaDocument;
import org.exbin.auxiliary.paged_data.delta.FileSegment;
import org.exbin.auxiliary.paged_data.delta.MemorySegment;
import org.exbin.auxiliary.paged_data.delta.list.DefaultDoublyLinkedList;
import org.exbin.bined.swing.extended.ExtCodeArea;
import org.exbin.framework.utils.LanguageUtils;
import org.exbin.framework.utils.WindowUtils;
import org.exbin.framework.editor.api.EditorProvider;
import org.exbin.framework.bined.BinEdFileHandler;
import org.exbin.framework.file.api.FileHandler;

/**
 * File properties panel.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class PropertiesPanel extends javax.swing.JPanel {

    private final java.util.ResourceBundle resourceBundle = LanguageUtils.getResourceBundleByClass(PropertiesPanel.class);

    public PropertiesPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileNameLabel = new javax.swing.JLabel();
        fileNameTextField = new javax.swing.JTextField();
        fileSizeLabel = new javax.swing.JLabel();
        fileSizeTextField = new javax.swing.JTextField();
        structurePanel = new javax.swing.JPanel();
        structureScrollPane = new javax.swing.JScrollPane();
        structureList = new javax.swing.JList<>();

        fileNameLabel.setText(resourceBundle.getString("fileNameLabel.text")); // NOI18N

        fileNameTextField.setEditable(false);

        fileSizeLabel.setText(resourceBundle.getString("fileSizeLabel.text")); // NOI18N

        fileSizeTextField.setEditable(false);

        structurePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceBundle.getString("structurePanel.border.title"))); // NOI18N

        structureList.setModel(new DefaultListModel<String>());
        structureScrollPane.setViewportView(structureList);

        javax.swing.GroupLayout structurePanelLayout = new javax.swing.GroupLayout(structurePanel);
        structurePanel.setLayout(structurePanelLayout);
        structurePanelLayout.setHorizontalGroup(
            structurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(structurePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(structureScrollPane)
                .addContainerGap())
        );
        structurePanelLayout.setVerticalGroup(
            structurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(structurePanelLayout.createSequentialGroup()
                .addComponent(structureScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileNameLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(structurePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fileSizeTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(fileSizeLabel)
                                .addGap(0, 297, Short.MAX_VALUE))
                            .addComponent(fileNameTextField, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileSizeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileSizeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(structurePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Test method for this panel.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WindowUtils.invokeDialog(new PropertiesPanel());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fileNameLabel;
    private javax.swing.JTextField fileNameTextField;
    private javax.swing.JLabel fileSizeLabel;
    private javax.swing.JTextField fileSizeTextField;
    private javax.swing.JList<String> structureList;
    private javax.swing.JPanel structurePanel;
    private javax.swing.JScrollPane structureScrollPane;
    // End of variables declaration//GEN-END:variables

    public void setEditorProvider(EditorProvider editorProvider) {
        Optional<FileHandler> activeFile = editorProvider.getActiveFile();
        if (!activeFile.isPresent()) {
            throw new IllegalStateException();
        }

        ExtCodeArea codeArea = ((BinEdFileHandler) activeFile.get()).getCodeArea();
        Optional<URI> fileUri = ((BinEdFileHandler) activeFile.get()).getFileUri();
        fileNameTextField.setText(fileUri.isPresent() ? fileUri.get().toString() : "");
        fileSizeTextField.setText(Long.toString(codeArea.getDataSize()));

        if (codeArea.getContentData() instanceof DeltaDocument) {
            DefaultDoublyLinkedList<DataSegment> segments = ((DeltaDocument) codeArea.getContentData()).getSegments();
            DataSegment segment = segments.first();
            DefaultListModel<String> listModel = (DefaultListModel<String>) structureList.getModel();
            while (segment != null) {
                if (segment instanceof FileSegment) {
                    listModel.addElement("FILE: " + ((FileSegment) segment).getStartPosition() + ", " + ((FileSegment) segment).getLength());
                } else {
                    listModel.addElement("MEMORY: " + ((MemorySegment) segment).getStartPosition() + ", " + ((MemorySegment) segment).getLength());
                }
                segment = segment.getNext();
            }
        }
    }

    @Nonnull
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
