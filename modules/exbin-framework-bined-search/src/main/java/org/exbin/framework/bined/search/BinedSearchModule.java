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
package org.exbin.framework.bined.search;

import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import org.exbin.framework.App;
import org.exbin.framework.Module;
import org.exbin.framework.ModuleUtils;
import org.exbin.framework.action.api.ActionConsts;
import org.exbin.framework.action.api.ActionModuleApi;
import org.exbin.framework.action.api.GroupMenuContributionRule;
import org.exbin.framework.action.api.GroupToolBarContributionRule;
import org.exbin.framework.action.api.MenuContribution;
import org.exbin.framework.action.api.PositionMode;
import org.exbin.framework.action.api.PositionToolBarContributionRule;
import org.exbin.framework.action.api.SeparationMode;
import org.exbin.framework.action.api.SeparationToolBarContributionRule;
import org.exbin.framework.action.api.ToolBarContribution;
import org.exbin.framework.bined.BinEdFileManager;
import org.exbin.framework.bined.BinedModule;
import org.exbin.framework.bined.gui.BinEdComponentPanel;
import org.exbin.framework.bined.search.action.FindReplaceActions;
import org.exbin.framework.language.api.LanguageModuleApi;
import org.exbin.framework.editor.api.EditorProvider;

/**
 * Binary editor search module.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class BinedSearchModule implements Module {

    public static final String MODULE_ID = ModuleUtils.getModuleIdByApi(BinedSearchModule.class);

    public static final String EDIT_FIND_TOOL_BAR_GROUP_ID = MODULE_ID + ".editFindToolBarGroup";

    private java.util.ResourceBundle resourceBundle = null;

    private EditorProvider editorProvider;

    private FindReplaceActions findReplaceActions;

    public BinedSearchModule() {
    }

    public void setEditorProvider(EditorProvider editorProvider) {
        this.editorProvider = editorProvider;
    }

    public void registerSearchComponent() {
        BinedModule binedModule = App.getModule(BinedModule.class);
        BinEdFileManager fileManager = binedModule.getFileManager();
        fileManager.addBinEdComponentExtension((BinEdComponentPanel component) -> Optional.of(new DefaultBinEdComponentSearch()));
    }

    @Nonnull
    public FindReplaceActions getFindReplaceActions() {
        if (findReplaceActions == null) {
            ensureSetup();
            findReplaceActions = new FindReplaceActions();
            findReplaceActions.setup(resourceBundle);
        }

        return findReplaceActions;
    }

    public void registerEditFindMenuActions() {
        getFindReplaceActions();
        ActionModuleApi actionModule = App.getModule(ActionModuleApi.class);
        MenuContribution contribution = actionModule.registerMenuItem(ActionConsts.EDIT_MENU_ID, MODULE_ID, findReplaceActions.getEditFindAction());
        actionModule.registerMenuRule(contribution, new GroupMenuContributionRule(BinedModule.EDIT_FIND_MENU_GROUP_ID));
        contribution = actionModule.registerMenuItem(ActionConsts.EDIT_MENU_ID, MODULE_ID, findReplaceActions.getEditFindAgainAction());
        actionModule.registerMenuRule(contribution, new GroupMenuContributionRule(BinedModule.EDIT_FIND_MENU_GROUP_ID));
        contribution = actionModule.registerMenuItem(ActionConsts.EDIT_MENU_ID, MODULE_ID, findReplaceActions.getEditReplaceAction());
        actionModule.registerMenuRule(contribution, new GroupMenuContributionRule(BinedModule.EDIT_FIND_MENU_GROUP_ID));
    }

    public void registerEditFindPopupMenuActions() {
        ActionModuleApi actionModule = App.getModule(ActionModuleApi.class);
        MenuContribution contribution = actionModule.registerMenuItem(BinedModule.CODE_AREA_POPUP_MENU_ID, MODULE_ID, getFindReplaceActions().getEditFindAction());
        actionModule.registerMenuRule(contribution, new GroupMenuContributionRule(BinedModule.CODE_AREA_POPUP_FIND_GROUP_ID));
        contribution = actionModule.registerMenuItem(BinedModule.CODE_AREA_POPUP_MENU_ID, MODULE_ID, getFindReplaceActions().getEditReplaceAction());
        actionModule.registerMenuRule(contribution, new GroupMenuContributionRule(BinedModule.CODE_AREA_POPUP_FIND_GROUP_ID));
    }

    public void registerEditFindToolBarActions() {
        getFindReplaceActions();
        ActionModuleApi actionModule = App.getModule(ActionModuleApi.class);
        ToolBarContribution contribution = actionModule.registerToolBarGroup(ActionConsts.MAIN_TOOL_BAR_ID, MODULE_ID, EDIT_FIND_TOOL_BAR_GROUP_ID);
        actionModule.registerToolBarRule(contribution, new PositionToolBarContributionRule(PositionMode.MIDDLE));
        actionModule.registerToolBarRule(contribution, new SeparationToolBarContributionRule(SeparationMode.AROUND));
        contribution = actionModule.registerToolBarItem(ActionConsts.MAIN_TOOL_BAR_ID, MODULE_ID, findReplaceActions.getEditFindAction());
        actionModule.registerToolBarRule(contribution, new GroupToolBarContributionRule(EDIT_FIND_TOOL_BAR_GROUP_ID));
    }

    @Nonnull
    public ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            resourceBundle = App.getModule(LanguageModuleApi.class).getBundle(BinedSearchModule.class);
        }

        return resourceBundle;
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
}
