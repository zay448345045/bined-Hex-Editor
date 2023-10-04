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
package org.exbin.framework.bined;

import org.exbin.framework.bined.action.ShowRowPositionAction;
import org.exbin.framework.bined.action.ClipboardCodeActions;
import org.exbin.framework.bined.action.GoToPositionAction;
import org.exbin.framework.bined.action.CodeTypeActions;
import org.exbin.framework.bined.action.CodeAreaFontAction;
import org.exbin.framework.bined.action.ViewModeHandlerActions;
import org.exbin.framework.bined.action.PrintAction;
import org.exbin.framework.bined.action.ShowUnprintablesActions;
import org.exbin.framework.bined.action.RowWrappingAction;
import org.exbin.framework.bined.action.PropertiesAction;
import org.exbin.framework.bined.handler.CodeAreaPopupMenuHandler;
import org.exbin.framework.bined.action.HexCharactersCaseActions;
import org.exbin.framework.bined.action.PositionCodeTypeActions;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import org.exbin.auxiliary.paged_data.delta.SegmentsRepository;
import org.exbin.bined.EditOperation;
import org.exbin.bined.basic.BasicCodeAreaZone;
import org.exbin.bined.PositionCodeType;
import org.exbin.bined.swing.CodeAreaCore;
import org.exbin.bined.swing.extended.ExtCodeArea;
import org.exbin.framework.XBFrameworkUtils;
import org.exbin.framework.api.Preferences;
import org.exbin.framework.api.XBApplication;
import org.exbin.framework.api.XBApplicationModule;
import org.exbin.framework.api.XBModuleRepositoryUtils;
import org.exbin.framework.bined.action.ShowHeaderAction;
import org.exbin.framework.bined.gui.BinEdComponentPanel;
import org.exbin.framework.bined.gui.BinaryStatusPanel;
import org.exbin.framework.editor.text.EncodingsHandler;
import org.exbin.framework.utils.ComponentPopupEventDispatcher;
import org.exbin.framework.action.api.MenuGroup;
import org.exbin.framework.action.api.MenuPosition;
import org.exbin.framework.action.api.NextToMode;
import org.exbin.framework.action.api.PositionMode;
import org.exbin.framework.action.api.SeparationMode;
import org.exbin.framework.action.api.ToolBarGroup;
import org.exbin.framework.action.api.ToolBarPosition;
import org.exbin.framework.options.api.OptionsModuleApi;
import org.exbin.framework.utils.LanguageUtils;
import org.exbin.xbup.plugin.XBModuleHandler;
import org.exbin.framework.bined.preferences.BinaryEditorPreferences;
import org.exbin.framework.bined.preferences.EditorPreferences;
import org.exbin.framework.editor.text.preferences.TextEncodingPreferences;
import org.exbin.framework.bined.service.BinaryAppearanceService;
import org.exbin.framework.bined.service.impl.BinaryAppearanceServiceImpl;
import org.exbin.framework.editor.text.service.TextEncodingService;
import org.exbin.framework.utils.ActionUtils;
import org.exbin.framework.editor.api.EditorProvider;
import org.exbin.framework.editor.api.EditorProviderVariant;
import org.exbin.framework.editor.api.MultiEditorProvider;
import org.exbin.framework.file.api.FileDependentAction;
import org.exbin.framework.file.api.FileHandler;
import org.exbin.framework.utils.ClipboardActionsApi;
import org.exbin.framework.action.api.ActionModuleApi;
import org.exbin.framework.bined.action.CodeAreaAction;
import org.exbin.framework.bined.action.EditSelectionAction;
import org.exbin.framework.bined.action.ReloadFileAction;
import org.exbin.framework.editor.api.EditorModuleApi;
import org.exbin.framework.file.api.FileModuleApi;
import org.exbin.framework.frame.api.FrameModuleApi;

/**
 * Binary data editor module.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class BinedModule implements XBApplicationModule {

    public static final String MODULE_ID = XBModuleRepositoryUtils.getModuleIdByApi(BinedModule.class);
    public static final String BINARY_POPUP_MENU_ID = MODULE_ID + ".binaryPopupMenu";
    public static final String CODE_AREA_POPUP_MENU_ID = MODULE_ID + ".codeAreaPopupMenu";
    public static final String VIEW_MODE_SUBMENU_ID = MODULE_ID + ".viewModeSubMenu";
    public static final String CODE_TYPE_SUBMENU_ID = MODULE_ID + ".codeTypeSubMenu";
    public static final String POSITION_CODE_TYPE_SUBMENU_ID = MODULE_ID + ".positionCodeTypeSubMenu";
    public static final String HEX_CHARACTERS_CASE_SUBMENU_ID = MODULE_ID + ".hexCharactersCaseSubMenu";

    private static final String VIEW_UNPRINTABLES_MENU_GROUP_ID = MODULE_ID + ".viewUnprintablesMenuGroup";
    private static final String BINED_TOOL_BAR_GROUP_ID = MODULE_ID + ".binedToolBarGroup";

    public static final String BINARY_STATUS_BAR_ID = "binaryStatusBar";

    private java.util.ResourceBundle resourceBundle = null;

    private XBApplication application;
    private EditorProvider editorProvider;
    private BinEdFileManager fileManager;
    private BinedOptionsManager binedOptionsManager;

    private ShowUnprintablesActions showUnprintablesActions;
    private CodeAreaFontAction codeAreaFontAction;
    private RowWrappingAction rowWrappingAction;
    private GoToPositionAction goToPositionAction;
    private EditSelectionAction editSelectionAction;
    private PropertiesAction propertiesAction;
    private ReloadFileAction reloadFileAction;
    private PrintAction printAction;
    private ViewModeHandlerActions viewModeActions;
    private ShowRowPositionAction showRowPositionAction;
    private ShowHeaderAction showHeaderAction;
    private CodeTypeActions codeTypeActions;
    private PositionCodeTypeActions positionCodeTypeActions;
    private HexCharactersCaseActions hexCharactersCaseActions;
    private ClipboardCodeActions clipboardCodeActions;
    private EncodingsHandler encodingsHandler;

    public BinedModule() {
    }

    @Override
    public void init(XBModuleHandler application) {
        this.application = (XBApplication) application;
    }

    public void initEditorProvider(EditorProviderVariant variant) {
        fileManager = new BinEdFileManager();
        fileManager.setApplication(application);
        switch (variant) {
            case SINGLE: {
                editorProvider = createSingleEditorProvider();
                break;
            }
            case MULTI: {
                editorProvider = createMultiEditorProvider();
                break;
            }
            default:
                throw XBFrameworkUtils.getInvalidTypeException(variant);
        }
        fileManager.setEditorProvider(editorProvider);
    }

    public void setEditorProvider(EditorProvider editorProvider) {
        this.editorProvider = editorProvider;
        fileManager.setEditorProvider(editorProvider);
    }

    @Override
    public void unregisterModule(String moduleId) {
    }

    @Nonnull
    public ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            resourceBundle = LanguageUtils.getResourceBundleByClass(BinedModule.class);
        }

        return resourceBundle;
    }

    @Nonnull
    private EditorProvider createSingleEditorProvider() {
        if (editorProvider == null) {

            BinEdFileHandler editorFile = new BinEdFileHandler();
            fileManager.initFileHandler(editorFile);

            EditorPreferences editorPreferences = new EditorPreferences(application.getAppPreferences());
            FileHandlingMode fileHandlingMode = editorPreferences.getFileHandlingMode();
            editorFile.setNewData(fileHandlingMode);

            editorProvider = new BinaryEditorProvider(application, editorFile);
            FileModuleApi fileModule = application.getModuleRepository().getModuleByInterface(FileModuleApi.class);
            fileModule.setFileOperations(editorProvider);

            BinEdComponentPanel componentPanel = editorFile.getComponent();
            componentPanel.setPopupMenu(createPopupMenu(editorFile.getId(), editorFile.getComponent().getCodeArea()));
        }

        return editorProvider;
    }

    @Nonnull
    private EditorProvider createMultiEditorProvider() {
        if (editorProvider == null) {
            editorProvider = new BinaryMultiEditorProvider(application);
            ((BinaryMultiEditorProvider) editorProvider).setSegmentsRepository(new SegmentsRepository());
            EditorPreferences editorPreferences = new EditorPreferences(application.getAppPreferences());
            FileHandlingMode fileHandlingMode = editorPreferences.getFileHandlingMode();
            ((BinaryMultiEditorProvider) editorProvider).setDefaultFileHandlingMode(fileHandlingMode);
            FileModuleApi fileModule = application.getModuleRepository().getModuleByInterface(FileModuleApi.class);
            fileModule.setFileOperations(editorProvider);
            ((BinaryMultiEditorProvider) editorProvider).setCodeAreaPopupMenuHandler(createCodeAreaPopupMenuHandler(PopupMenuVariant.EDITOR));

            ((MultiEditorProvider) editorProvider).addActiveFileChangeListener(e -> {
                Optional<FileHandler> activeFile = editorProvider.getActiveFile();
                CodeAreaCore codeArea = activeFile.isPresent() ? ((BinEdFileHandler) activeFile.get()).getCodeArea() : null;
                updateActionStatus(codeArea);
            });
            ((BinaryMultiEditorProvider) editorProvider).setClipboardActionsUpdateListener(() -> {
                updateClipboardActionStatus();
            });
            fileModule.setFileOperations(editorProvider);
        }

        return editorProvider;
    }

    public void updateActionStatus(@Nullable CodeAreaCore codeArea) {
        EditorModuleApi editorModule = application.getModuleRepository().getModuleByInterface(EditorModuleApi.class);
        editorModule.updateActionStatus();
        FileDependentAction[] fileDepActions = new FileDependentAction[]{
            codeAreaFontAction, propertiesAction, reloadFileAction
        };
        for (FileDependentAction fileDepAction : fileDepActions) {
            if (fileDepAction != null) {
                fileDepAction.updateForActiveFile();
            }
        }

        CodeAreaAction[] codeAreaActions = new CodeAreaAction[]{
            goToPositionAction, editSelectionAction,
            hexCharactersCaseActions, codeTypeActions, positionCodeTypeActions,
            rowWrappingAction, viewModeActions, showHeaderAction,
            showRowPositionAction, showUnprintablesActions,
            clipboardCodeActions, printAction
        };
        for (CodeAreaAction codeAreaAction : codeAreaActions) {
            if (codeAreaAction != null) {
                codeAreaAction.updateForActiveCodeArea(codeArea);
            }
        }

        fileManager.updateActionStatus(codeArea);

        FileModuleApi fileModule = application.getModuleRepository().getModuleByInterface(FileModuleApi.class);
        fileModule.updateForFileOperations();
    }

    public void updateClipboardActionStatus() {
        if (clipboardCodeActions != null) {
            Optional<FileHandler> activeFile = editorProvider.getActiveFile();
            CodeAreaCore codeArea = activeFile.isPresent() ? ((BinEdFileHandler) activeFile.get()).getCodeArea() : null;
            clipboardCodeActions.updateForActiveCodeArea(codeArea);
        }
    }

    @Nonnull
    public EditorProvider getEditorProvider() {
        return Objects.requireNonNull(editorProvider, "Editor provider was not yet initialized");
    }

    private void ensureSetup() {
        if (editorProvider == null) {
            getEditorProvider();
        }

        if (resourceBundle == null) {
            getResourceBundle();
        }
    }

    @Nonnull
    public BinedOptionsManager getMainOptionsManager() {
        if (binedOptionsManager == null) {
            binedOptionsManager = new BinedOptionsManager();
            binedOptionsManager.setApplication(application);
            binedOptionsManager.setEditorProvider(editorProvider);
        }
        return binedOptionsManager;
    }

    public void registerStatusBar() {
        fileManager.registerStatusBar();
        fileManager.setStatusControlHandler(new BinaryStatusPanel.StatusControlHandler() {
            @Override
            public void changeEditOperation(EditOperation editOperation) {
                Optional<FileHandler> activeFile = editorProvider.getActiveFile();
                if (activeFile.isPresent()) {
                    ((BinEdFileHandler) activeFile.get()).getCodeArea().setEditOperation(editOperation);
                }
            }

            @Override
            public void changeCursorPosition() {
                if (goToPositionAction != null) {
                    goToPositionAction.actionPerformed(null);
                }
            }

            @Override
            public void cycleEncodings() {
                if (encodingsHandler != null) {
                    encodingsHandler.cycleEncodings();
                }
            }

            @Override
            public void encodingsPopupEncodingsMenu(MouseEvent mouseEvent) {
                if (encodingsHandler != null) {
                    encodingsHandler.popupEncodingsMenu(mouseEvent);
                }
            }

            @Override
            public void changeMemoryMode(BinaryStatusApi.MemoryMode memoryMode) {
                Optional<FileHandler> activeFile = editorProvider.getActiveFile();
                if (activeFile.isPresent()) {
                    BinEdFileHandler fileHandler = (BinEdFileHandler) activeFile.get();
                    FileHandlingMode fileHandlingMode = fileHandler.getFileHandlingMode();
                    FileHandlingMode newHandlingMode = memoryMode == BinaryStatusApi.MemoryMode.DELTA_MODE ? FileHandlingMode.DELTA : FileHandlingMode.MEMORY;
                    if (newHandlingMode != fileHandlingMode) {
                        BinaryEditorPreferences preferences = new BinaryEditorPreferences(application.getAppPreferences());
                        if (editorProvider.releaseFile(fileHandler)) {
                            fileHandler.switchFileHandlingMode(newHandlingMode);
                            preferences.getEditorPreferences().setFileHandlingMode(newHandlingMode);
                        }
                        ((BinEdEditorProvider) editorProvider).updateStatus();
                    }
                }
            }
        });

        if (encodingsHandler != null) {
            fileManager.updateTextEncodingStatus(encodingsHandler);
        }
    }

    public void registerOptionsMenuPanels() {
        getEncodingsHandler();
        encodingsHandler.rebuildEncodings();

        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.TOOLS_MENU_ID, MODULE_ID, encodingsHandler.getToolsEncodingMenu(), new MenuPosition(PositionMode.TOP_LAST));
    }

    public void registerOptionsPanels() {
        BinaryAppearanceService binaryAppearanceService = new BinaryAppearanceServiceImpl(this, editorProvider);
        getMainOptionsManager().registerOptionsPanels(getEncodingsHandler(), fileManager, binaryAppearanceService, getCodeTypeActions(), getShowUnprintablesActions(), getHexCharactersCaseActions(), getPositionCodeTypeActions(), getViewModeActions());
    }

    public void registerWordWrapping() {
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.VIEW_MENU_ID, MODULE_ID, getRowWrappingAction(), new MenuPosition(PositionMode.BOTTOM));
    }

    public void registerGoToPosition() {
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.EDIT_MENU_ID, MODULE_ID, getGoToPositionAction(), new MenuPosition(PositionMode.BOTTOM));
    }

    public void registerEditSelection() {
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.EDIT_MENU_ID, MODULE_ID, getEditSelectionAction(), new MenuPosition(PositionMode.BOTTOM));
    }

    @Nullable
    public BinaryStatusPanel getBinaryStatusPanel() {
        return fileManager.getBinaryStatusPanel();
    }

    @Nonnull
    private AbstractAction getShowHeaderAction() {
        if (showHeaderAction == null) {
            ensureSetup();
            showHeaderAction = new ShowHeaderAction();
            showHeaderAction.setup(application, resourceBundle);
        }

        return showHeaderAction;
    }

    @Nonnull
    private AbstractAction getShowRowPositionAction() {
        if (showRowPositionAction == null) {
            ensureSetup();
            showRowPositionAction = new ShowRowPositionAction();
            showRowPositionAction.setup(application, resourceBundle);
        }

        return showRowPositionAction;
    }

    @Nonnull
    public AbstractAction getRowWrappingAction() {
        if (rowWrappingAction == null) {
            ensureSetup();
            rowWrappingAction = new RowWrappingAction();
            rowWrappingAction.setup(application, resourceBundle);
        }

        return rowWrappingAction;
    }

    @Nonnull
    public ShowUnprintablesActions getShowUnprintablesActions() {
        if (showUnprintablesActions == null) {
            ensureSetup();
            showUnprintablesActions = new ShowUnprintablesActions();
            showUnprintablesActions.setup(application, resourceBundle);
        }

        return showUnprintablesActions;
    }

    @Nonnull
    public CodeAreaFontAction getCodeAreaFontAction() {
        if (codeAreaFontAction == null) {
            ensureSetup();
            codeAreaFontAction = new CodeAreaFontAction();
            codeAreaFontAction.setup(application, editorProvider, resourceBundle);
        }

        return codeAreaFontAction;
    }

    @Nonnull
    public AbstractAction getGoToPositionAction() {
        if (goToPositionAction == null) {
            ensureSetup();
            goToPositionAction = new GoToPositionAction();
            goToPositionAction.setup(application, resourceBundle);
        }

        return goToPositionAction;
    }

    @Nonnull
    public AbstractAction getEditSelectionAction() {
        if (editSelectionAction == null) {
            ensureSetup();
            editSelectionAction = new EditSelectionAction();
            editSelectionAction.setup(application, resourceBundle);
        }

        return editSelectionAction;
    }

    @Nonnull
    public AbstractAction getPropertiesAction() {
        if (propertiesAction == null) {
            ensureSetup();
            propertiesAction = new PropertiesAction();
            propertiesAction.setup(application, editorProvider, resourceBundle);
        }

        return propertiesAction;
    }

    @Nonnull
    public EncodingsHandler getEncodingsHandler() {
        if (encodingsHandler == null) {
            ensureSetup();
            encodingsHandler = new EncodingsHandler();
            encodingsHandler.setApplication(application);
            encodingsHandler.setParentComponent(editorProvider.getEditorComponent());
            fileManager.updateTextEncodingStatus(encodingsHandler);
            encodingsHandler.init();

            encodingsHandler.setEncodingChangeListener(new TextEncodingService.EncodingChangeListener() {
                @Override
                public void encodingListChanged() {
                    encodingsHandler.rebuildEncodings();
                }

                @Override
                public void selectedEncodingChanged() {
                    Optional<FileHandler> activeFile = editorProvider.getActiveFile();
                    if (activeFile.isPresent()) {
                        ((BinEdFileHandler) activeFile.get()).setCharset(Charset.forName(encodingsHandler.getSelectedEncoding()));
                    }
                }
            });
        }

        return encodingsHandler;
    }

    @Nonnull
    private AbstractAction getReloadFileAction() {
        if (reloadFileAction == null) {
            ensureSetup();
            reloadFileAction = new ReloadFileAction();
            reloadFileAction.setup(application, editorProvider, resourceBundle);
        }

        return reloadFileAction;
    }

    @Nonnull
    public AbstractAction getPrintAction() {
        if (printAction == null) {
            ensureSetup();
            printAction = new PrintAction();
            printAction.setup(application, resourceBundle);
        }

        return printAction;
    }

    @Nonnull
    public ViewModeHandlerActions getViewModeActions() {
        if (viewModeActions == null) {
            ensureSetup();
            viewModeActions = new ViewModeHandlerActions();
            viewModeActions.setup(application, resourceBundle);
        }

        return viewModeActions;
    }

    @Nonnull
    public CodeTypeActions getCodeTypeActions() {
        if (codeTypeActions == null) {
            ensureSetup();
            codeTypeActions = new CodeTypeActions();
            codeTypeActions.setup(application, resourceBundle);
        }

        return codeTypeActions;
    }

    @Nonnull
    public PositionCodeTypeActions getPositionCodeTypeActions() {
        if (positionCodeTypeActions == null) {
            ensureSetup();
            positionCodeTypeActions = new PositionCodeTypeActions();
            positionCodeTypeActions.setup(application, resourceBundle);
        }

        return positionCodeTypeActions;
    }

    @Nonnull
    public HexCharactersCaseActions getHexCharactersCaseActions() {
        if (hexCharactersCaseActions == null) {
            ensureSetup();
            hexCharactersCaseActions = new HexCharactersCaseActions();
            hexCharactersCaseActions.setup(application, resourceBundle);
        }

        return hexCharactersCaseActions;
    }

    @Nonnull
    public ClipboardCodeActions getClipboardCodeActions() {
        if (clipboardCodeActions == null) {
            ensureSetup();
            clipboardCodeActions = new ClipboardCodeActions();
            clipboardCodeActions.setup(application, resourceBundle);
        }

        return clipboardCodeActions;
    }

    public void registerCodeTypeToolBarActions() {
        getCodeTypeActions();
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerToolBarGroup(FrameModuleApi.MAIN_TOOL_BAR_ID, new ToolBarGroup(BINED_TOOL_BAR_GROUP_ID, new ToolBarPosition(PositionMode.MIDDLE), SeparationMode.ABOVE));
        actionModule.registerToolBarItem(FrameModuleApi.MAIN_TOOL_BAR_ID, MODULE_ID, codeTypeActions.getCycleCodeTypesAction(), new ToolBarPosition(BINED_TOOL_BAR_GROUP_ID));
    }

    public void registerShowUnprintablesToolBarActions() {
        getShowUnprintablesActions();
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerToolBarGroup(FrameModuleApi.MAIN_TOOL_BAR_ID, new ToolBarGroup(BINED_TOOL_BAR_GROUP_ID, new ToolBarPosition(PositionMode.MIDDLE), SeparationMode.NONE));
        actionModule.registerToolBarItem(FrameModuleApi.MAIN_TOOL_BAR_ID, MODULE_ID, showUnprintablesActions.getViewUnprintablesToolbarAction(), new ToolBarPosition(BINED_TOOL_BAR_GROUP_ID));
    }

    public void registerViewUnprintablesMenuActions() {
        getShowUnprintablesActions();
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuGroup(FrameModuleApi.VIEW_MENU_ID, new MenuGroup(VIEW_UNPRINTABLES_MENU_GROUP_ID, new MenuPosition(PositionMode.BOTTOM), SeparationMode.NONE));
        actionModule.registerMenuItem(FrameModuleApi.VIEW_MENU_ID, MODULE_ID, showUnprintablesActions.getViewUnprintablesAction(), new MenuPosition(VIEW_UNPRINTABLES_MENU_GROUP_ID));
    }

    public void registerToolsOptionsMenuActions() {
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.TOOLS_MENU_ID, MODULE_ID, getCodeAreaFontAction(), new MenuPosition(PositionMode.TOP));
    }

    public void registerClipboardCodeActions() {
        getClipboardCodeActions();
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.EDIT_MENU_ID, MODULE_ID, clipboardCodeActions.getCopyAsCodeAction(), new MenuPosition(NextToMode.AFTER, (String) actionModule.getClipboardActions().getCopyAction().getValue(Action.NAME)));
        actionModule.registerMenuItem(FrameModuleApi.EDIT_MENU_ID, MODULE_ID, clipboardCodeActions.getPasteFromCodeAction(), new MenuPosition(NextToMode.AFTER, (String) actionModule.getClipboardActions().getPasteAction().getValue(Action.NAME)));
    }

    public void registerPropertiesMenu() {
        getPropertiesAction();
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.FILE_MENU_ID, MODULE_ID, getPropertiesAction(), new MenuPosition(PositionMode.BOTTOM));
    }

    public void registerReloadFileMenu() {
        getReloadFileAction();
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.FILE_MENU_ID, MODULE_ID, getReloadFileAction(), new MenuPosition(PositionMode.BOTTOM));
    }

    public void registerPrintMenu() {
        getPrintAction();
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.FILE_MENU_ID, MODULE_ID, getPrintAction(), new MenuPosition(PositionMode.BOTTOM));
    }

    public void registerViewModeMenu() {
        getViewModeActions();
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.VIEW_MENU_ID, MODULE_ID, VIEW_MODE_SUBMENU_ID, resourceBundle.getString("viewModeSubMenu.text"), new MenuPosition(PositionMode.BOTTOM));
        actionModule.registerMenu(VIEW_MODE_SUBMENU_ID, MODULE_ID);
        actionModule.registerMenuItem(VIEW_MODE_SUBMENU_ID, MODULE_ID, viewModeActions.getDualModeAction(), new MenuPosition(PositionMode.TOP));
        actionModule.registerMenuItem(VIEW_MODE_SUBMENU_ID, MODULE_ID, viewModeActions.getCodeMatrixModeAction(), new MenuPosition(PositionMode.TOP));
        actionModule.registerMenuItem(VIEW_MODE_SUBMENU_ID, MODULE_ID, viewModeActions.getTextPreviewModeAction(), new MenuPosition(PositionMode.TOP));
    }

    public void registerLayoutMenu() {
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.VIEW_MENU_ID, MODULE_ID, getShowHeaderAction(), new MenuPosition(PositionMode.BOTTOM));
        actionModule.registerMenuItem(FrameModuleApi.VIEW_MENU_ID, MODULE_ID, getShowRowPositionAction(), new MenuPosition(PositionMode.BOTTOM));
    }

    public void registerCodeTypeMenu() {
        getCodeTypeActions();
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.VIEW_MENU_ID, MODULE_ID, CODE_TYPE_SUBMENU_ID, resourceBundle.getString("codeTypeSubMenu.text"), new MenuPosition(PositionMode.BOTTOM));
        actionModule.registerMenu(CODE_TYPE_SUBMENU_ID, MODULE_ID);
        actionModule.registerMenuItem(CODE_TYPE_SUBMENU_ID, MODULE_ID, codeTypeActions.getBinaryCodeTypeAction(), new MenuPosition(PositionMode.TOP));
        actionModule.registerMenuItem(CODE_TYPE_SUBMENU_ID, MODULE_ID, codeTypeActions.getOctalCodeTypeAction(), new MenuPosition(PositionMode.TOP));
        actionModule.registerMenuItem(CODE_TYPE_SUBMENU_ID, MODULE_ID, codeTypeActions.getDecimalCodeTypeAction(), new MenuPosition(PositionMode.TOP));
        actionModule.registerMenuItem(CODE_TYPE_SUBMENU_ID, MODULE_ID, codeTypeActions.getHexadecimalCodeTypeAction(), new MenuPosition(PositionMode.TOP));
    }

    public void registerPositionCodeTypeMenu() {
        getPositionCodeTypeActions();
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.VIEW_MENU_ID, MODULE_ID, POSITION_CODE_TYPE_SUBMENU_ID, resourceBundle.getString("positionCodeTypeSubMenu.text"), new MenuPosition(PositionMode.BOTTOM));
        actionModule.registerMenu(POSITION_CODE_TYPE_SUBMENU_ID, MODULE_ID);
        actionModule.registerMenuItem(POSITION_CODE_TYPE_SUBMENU_ID, MODULE_ID, positionCodeTypeActions.getOctalCodeTypeAction(), new MenuPosition(PositionMode.TOP));
        actionModule.registerMenuItem(POSITION_CODE_TYPE_SUBMENU_ID, MODULE_ID, positionCodeTypeActions.getDecimalCodeTypeAction(), new MenuPosition(PositionMode.TOP));
        actionModule.registerMenuItem(POSITION_CODE_TYPE_SUBMENU_ID, MODULE_ID, positionCodeTypeActions.getHexadecimalCodeTypeAction(), new MenuPosition(PositionMode.TOP));
    }

    public void registerHexCharactersCaseHandlerMenu() {
        getHexCharactersCaseActions();
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.VIEW_MENU_ID, MODULE_ID, HEX_CHARACTERS_CASE_SUBMENU_ID, resourceBundle.getString("hexCharsCaseSubMenu.text"), new MenuPosition(PositionMode.BOTTOM));
        actionModule.registerMenu(HEX_CHARACTERS_CASE_SUBMENU_ID, MODULE_ID);
        actionModule.registerMenuItem(HEX_CHARACTERS_CASE_SUBMENU_ID, MODULE_ID, hexCharactersCaseActions.getUpperHexCharsAction(), new MenuPosition(PositionMode.TOP));
        actionModule.registerMenuItem(HEX_CHARACTERS_CASE_SUBMENU_ID, MODULE_ID, hexCharactersCaseActions.getLowerHexCharsAction(), new MenuPosition(PositionMode.TOP));
    }

    public void registerEditSelectionAction() {
        getEditSelectionAction();
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.EDIT_MENU_ID, MODULE_ID, getEditSelectionAction(), new MenuPosition(ActionModuleApi.CLIPBOARD_ACTIONS_MENU_GROUP_ID));
    }

    public void start() {
        if (editorProvider instanceof MultiEditorProvider) {
            editorProvider.newFile();
        }
    }

    public void startWithFile(String filePath) {
        FileModuleApi fileModule = application.getModuleRepository().getModuleByInterface(FileModuleApi.class);
        URI uri = new File(filePath).toURI();
        fileModule.loadFromFile(uri.toASCIIString());
    }

    @Nonnull
    public BinEdFileManager getFileManager() {
        return fileManager;
    }

    @Nonnull
    private JPopupMenu createPopupMenu(int postfix, ExtCodeArea codeArea) {
        String popupMenuId = BINARY_POPUP_MENU_ID + "." + postfix;

        JPopupMenu popupMenu = new JPopupMenu() {
            @Override
            public void show(Component invoker, int x, int y) {
                int clickedX = x;
                int clickedY = y;
                if (invoker instanceof JViewport) {
                    clickedX += ((JViewport) invoker).getParent().getX();
                    clickedY += ((JViewport) invoker).getParent().getY();
                }
                CodeAreaPopupMenuHandler codeAreaPopupMenuHandler = createCodeAreaPopupMenuHandler(PopupMenuVariant.EDITOR);
                JPopupMenu popupMenu = codeAreaPopupMenuHandler.createPopupMenu(codeArea, popupMenuId, clickedX, clickedY);
                popupMenu.addPopupMenuListener(new PopupMenuListener() {
                    @Override
                    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                    }

                    @Override
                    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                        codeAreaPopupMenuHandler.dropPopupMenu(popupMenuId);
                    }

                    @Override
                    public void popupMenuCanceled(PopupMenuEvent e) {
                    }
                });
                popupMenu.show(invoker, x, y);
            }
        };
//        menuModule.buildMenu(popupMenu, popupMenuId);
        return popupMenu;
    }

    @Nonnull
    private JPopupMenu createCodeAreaPopupMenu(final ExtCodeArea codeArea, String menuPostfix, PopupMenuVariant variant, int x, int y) {
        getClipboardCodeActions();
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        OptionsModuleApi optionsModule = application.getModuleRepository().getModuleByInterface(OptionsModuleApi.class);
        actionModule.registerMenu(CODE_AREA_POPUP_MENU_ID + menuPostfix, MODULE_ID);

        BasicCodeAreaZone positionZone = codeArea.getPainter().getPositionZone(x, y);

        final JPopupMenu popupMenu = new JPopupMenu();
        switch (positionZone) {
            case TOP_LEFT_CORNER:
            case HEADER: {
                if (variant != PopupMenuVariant.BASIC) {
                    popupMenu.add(createShowHeaderMenuItem(codeArea));
                    popupMenu.add(createPositionCodeTypeMenuItem(codeArea));
                    break;
                }
            }
            case ROW_POSITIONS: {
                if (variant != PopupMenuVariant.BASIC) {
                    popupMenu.add(createShowRowPositionMenuItem(codeArea));
                    popupMenu.add(createPositionCodeTypeMenuItem(codeArea));
                    popupMenu.add(new JSeparator());
                    popupMenu.add(createGoToMenuItem());

                    break;
                }
            }
            default: {
                ClipboardActionsApi clipboardActions = actionModule.getClipboardActions();
                final JMenuItem cutMenuItem = ActionUtils.actionToMenuItem(clipboardActions.getCutAction());
                cutMenuItem.setEnabled(codeArea.hasSelection() && codeArea.isEditable());
                cutMenuItem.addActionListener((ActionEvent e) -> {
                    codeArea.cut();
                });
                popupMenu.add(cutMenuItem);

                final JMenuItem copyMenuItem = ActionUtils.actionToMenuItem(clipboardActions.getCopyAction());
                copyMenuItem.setEnabled(codeArea.hasSelection());
                copyMenuItem.addActionListener((ActionEvent e) -> {
                    codeArea.copy();
                });
                popupMenu.add(copyMenuItem);

                final JMenuItem copyAsCodeMenuItem = ActionUtils.actionToMenuItem(clipboardCodeActions.getCopyAsCodeAction());
                copyAsCodeMenuItem.setEnabled(codeArea.hasSelection());
                copyAsCodeMenuItem.addActionListener((ActionEvent e) -> {
                    codeArea.copyAsCode();
                });
                popupMenu.add(copyAsCodeMenuItem);

                final JMenuItem pasteMenuItem = ActionUtils.actionToMenuItem(clipboardActions.getPasteAction());
                pasteMenuItem.setEnabled(codeArea.canPaste() && codeArea.isEditable());
                pasteMenuItem.addActionListener((ActionEvent e) -> {
                    codeArea.paste();
                });
                popupMenu.add(pasteMenuItem);

                final JMenuItem pasteFromCodeMenuItem = ActionUtils.actionToMenuItem(clipboardCodeActions.getPasteFromCodeAction());
                pasteFromCodeMenuItem.setEnabled(codeArea.canPaste() && codeArea.isEditable());
                pasteFromCodeMenuItem.addActionListener((ActionEvent e) -> {
                    try {
                        codeArea.pasteFromCode();
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(codeArea, ex.getMessage(), "Unable to Paste Code", JOptionPane.ERROR_MESSAGE);
                    }
                });
                popupMenu.add(pasteFromCodeMenuItem);

                final JMenuItem deleteMenuItem = ActionUtils.actionToMenuItem(clipboardActions.getDeleteAction());
                deleteMenuItem.setEnabled(codeArea.hasSelection() && codeArea.isEditable());
                deleteMenuItem.addActionListener((ActionEvent e) -> {
                    codeArea.delete();
                });
                popupMenu.add(deleteMenuItem);
                popupMenu.addSeparator();

                final JMenuItem selectAllMenuItem = ActionUtils.actionToMenuItem(clipboardActions.getSelectAllAction());
                selectAllMenuItem.addActionListener((ActionEvent e) -> {
                    codeArea.selectAll();
                });
                popupMenu.add(selectAllMenuItem);
                JMenuItem editSelectionMenuItem = createEditSelectionMenuItem();
                popupMenu.add(editSelectionMenuItem);

                if (variant != PopupMenuVariant.BASIC) {
                    JMenuItem goToMenuItem = createGoToMenuItem();
                    popupMenu.add(goToMenuItem);
                }
            }
        }

        if (variant == PopupMenuVariant.EDITOR) {
            popupMenu.addSeparator();

            switch (positionZone) {
                case TOP_LEFT_CORNER:
                case HEADER:
                case ROW_POSITIONS: {
                    break;
                }
                default: {
                    JMenu showMenu = new JMenu(resourceBundle.getString("popupShowSubMenu.text"));
                    JMenuItem showHeader = createShowHeaderMenuItem(codeArea);
                    showMenu.add(showHeader);
                    JMenuItem showRowPosition = createShowRowPositionMenuItem(codeArea);
                    showMenu.add(showRowPosition);
                    popupMenu.add(showMenu);
                }
            }

            final JMenuItem optionsMenuItem = ActionUtils.actionToMenuItem(optionsModule.getOptionsAction());
            popupMenu.add(optionsMenuItem);
        }

        fileManager.insertActionsIntoPopupMenu(popupMenu, codeArea, menuPostfix, variant, x, y);

        updateActionStatus(codeArea);
        return popupMenu;
    }

    @Nonnull
    public JPopupMenu createBinEdComponentPopupMenu(CodeAreaPopupMenuHandler codeAreaPopupMenuHandler, BinEdComponentPanel binaryPanel, int clickedX, int clickedY) {
        return codeAreaPopupMenuHandler.createPopupMenu(binaryPanel.getCodeArea(), "", clickedX, clickedY);
    }

    public void dropBinEdComponentPopupMenu() {
        dropCodeAreaPopupMenu("");
    }

    @Nonnull
    private JMenuItem createGoToMenuItem() {
        return ActionUtils.actionToMenuItem(getGoToPositionAction());
    }

    @Nonnull
    private JMenuItem createEditSelectionMenuItem() {
        return ActionUtils.actionToMenuItem(getEditSelectionAction());
    }

    @Nonnull
    private JMenuItem createShowHeaderMenuItem(ExtCodeArea codeArea) {
        AbstractAction action = getShowHeaderAction();
        final JCheckBoxMenuItem showHeader = new JCheckBoxMenuItem((String) action.getValue(Action.NAME));
        showHeader.setSelected(Objects.requireNonNull(codeArea.getLayoutProfile()).isShowHeader());
        showHeader.addActionListener(action);
        return showHeader;
    }

    @Nonnull
    private JMenuItem createShowRowPositionMenuItem(ExtCodeArea codeArea) {
        AbstractAction action = getShowRowPositionAction();
        final JCheckBoxMenuItem showRowPosition = new JCheckBoxMenuItem((String) action.getValue(Action.NAME));
        showRowPosition.setSelected(Objects.requireNonNull(codeArea.getLayoutProfile()).isShowRowPosition());
        showRowPosition.addActionListener(action);
        return showRowPosition;
    }

    @Nonnull
    private JMenuItem createPositionCodeTypeMenuItem(ExtCodeArea codeArea) {
        JMenu menu = new JMenu(resourceBundle.getString("positionCodeTypeSubMenu.text"));
        PositionCodeType codeType = codeArea.getPositionCodeType();

        final JRadioButtonMenuItem octalCodeTypeMenuItem = new JRadioButtonMenuItem(resourceBundle.getString("octalCodeTypeAction.text"));
        octalCodeTypeMenuItem.setSelected(codeType == PositionCodeType.OCTAL);
        octalCodeTypeMenuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codeArea.setPositionCodeType(PositionCodeType.OCTAL);
//                preferences.getCodeAreaParameters().setPositionCodeType(PositionCodeType.OCTAL);
            }
        });
        menu.add(octalCodeTypeMenuItem);

        final JRadioButtonMenuItem decimalCodeTypeMenuItem = new JRadioButtonMenuItem(resourceBundle.getString("decimalCodeTypeAction.text"));
        decimalCodeTypeMenuItem.setSelected(codeType == PositionCodeType.DECIMAL);
        decimalCodeTypeMenuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codeArea.setPositionCodeType(PositionCodeType.DECIMAL);
//                preferences.getCodeAreaParameters().setPositionCodeType(PositionCodeType.DECIMAL);
            }
        });
        menu.add(decimalCodeTypeMenuItem);

        final JRadioButtonMenuItem hexadecimalCodeTypeMenuItem = new JRadioButtonMenuItem(resourceBundle.getString("hexadecimalCodeTypeAction.text"));
        hexadecimalCodeTypeMenuItem.setSelected(codeType == PositionCodeType.HEXADECIMAL);
        hexadecimalCodeTypeMenuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codeArea.setPositionCodeType(PositionCodeType.HEXADECIMAL);
//                preferences.getCodeAreaParameters().setPositionCodeType(PositionCodeType.HEXADECIMAL);
            }
        });
        menu.add(hexadecimalCodeTypeMenuItem);

        return menu;
    }

    private void dropCodeAreaPopupMenu(String menuPostfix) {
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.unregisterMenu(CODE_AREA_POPUP_MENU_ID + menuPostfix);
    }

    public void loadFromPreferences(Preferences preferences) {
        encodingsHandler.loadFromPreferences(new TextEncodingPreferences(preferences));
        fileManager.loadFromPreferences(preferences);
    }

    @Nonnull
    public CodeAreaPopupMenuHandler createCodeAreaPopupMenuHandler(PopupMenuVariant variant) {
        return new CodeAreaPopupMenuHandler() {
            @Override
            public JPopupMenu createPopupMenu(ExtCodeArea codeArea, String menuPostfix, int x, int y) {
                return createCodeAreaPopupMenu(codeArea, menuPostfix, variant, x, y);
            }

            @Override
            public void dropPopupMenu(String menuPostfix) {
                dropCodeAreaPopupMenu(menuPostfix);
            }
        };
    }

    public void registerCodeAreaPopupEventDispatcher() {
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.addComponentPopupEventDispatcher(new ComponentPopupEventDispatcher() {

            private static final String DEFAULT_MENU_POSTFIX = ".default";
            private JPopupMenu popupMenu = null;

            @Override
            public boolean dispatchMouseEvent(MouseEvent mouseEvent) {
                Component component = getSource(mouseEvent);
                if (component instanceof ExtCodeArea) {
                    if (((ExtCodeArea) component).getComponentPopupMenu() == null) {
                        CodeAreaPopupMenuHandler handler = createCodeAreaPopupMenuHandler(PopupMenuVariant.NORMAL);
                        if (popupMenu != null) {
                            handler.dropPopupMenu(DEFAULT_MENU_POSTFIX);
                        }

                        int x;
                        int y;
                        Point point = component.getMousePosition();
                        if (point != null) {
                            x = (int) point.getX();
                            y = (int) point.getY();
                        } else {
                            x = mouseEvent.getX();
                            y = mouseEvent.getY();
                        }

                        popupMenu = handler.createPopupMenu((ExtCodeArea) component, DEFAULT_MENU_POSTFIX, x, y);

                        if (point != null) {
                            popupMenu.show(component, x, y);
                        } else {
                            popupMenu.show(mouseEvent.getComponent(), x, y);
                        }
                        return true;
                    }
                }

                return false;
            }

            @Override
            public boolean dispatchKeyEvent(KeyEvent keyEvent) {
                Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

                if (component instanceof ExtCodeArea) {
                    if (((ExtCodeArea) component).getComponentPopupMenu() == null) {
                        CodeAreaPopupMenuHandler handler = createCodeAreaPopupMenuHandler(PopupMenuVariant.NORMAL);
                        if (popupMenu != null) {
                            handler.dropPopupMenu(DEFAULT_MENU_POSTFIX);
                        }

                        Point point = new Point(component.getWidth() / 2, component.getHeight() / 2);
                        int x = (int) point.getX();
                        int y = (int) point.getY();
                        popupMenu = handler.createPopupMenu((ExtCodeArea) component, DEFAULT_MENU_POSTFIX, x, y);

                        popupMenu.show(component, x, y);
                        return true;
                    }
                }

                return false;
            }

            @Nullable
            private Component getSource(MouseEvent e) {
                return SwingUtilities.getDeepestComponentAt(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    public enum PopupMenuVariant {
        BASIC, NORMAL, EDITOR
    }
}
