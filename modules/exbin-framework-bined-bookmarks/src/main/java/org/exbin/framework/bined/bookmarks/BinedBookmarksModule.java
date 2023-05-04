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
package org.exbin.framework.bined.bookmarks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.AbstractAction;
import org.exbin.framework.action.api.ActionModuleApi;
import org.exbin.framework.action.api.MenuPosition;
import org.exbin.framework.action.api.PositionMode;
import org.exbin.framework.api.XBApplication;
import org.exbin.framework.api.XBApplicationModule;
import org.exbin.framework.api.XBModuleRepositoryUtils;
import org.exbin.framework.bined.BinedModule;
import org.exbin.framework.bined.bookmarks.action.ManageBookmarksAction;
import org.exbin.framework.bined.bookmarks.model.BookmarkRecord;
import org.exbin.framework.utils.LanguageUtils;
import org.exbin.xbup.plugin.XBModuleHandler;
import org.exbin.framework.editor.api.EditorProvider;
import org.exbin.framework.editor.api.EditorProviderVariant;
import org.exbin.framework.frame.api.FrameModuleApi;

/**
 * Binary data editor module.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class BinedBookmarksModule implements XBApplicationModule {

    public static final String MODULE_ID = XBModuleRepositoryUtils.getModuleIdByApi(BinedBookmarksModule.class);

    private java.util.ResourceBundle resourceBundle = null;

    private XBApplication application;
    private EditorProvider editorProvider;

    private ManageBookmarksAction manageBookmarksAction;
    private final List<BookmarkRecord> bookmarkRecords = new ArrayList<>();
    private BookmarksPositionColorModifier bookmarksPositionColorModifier;

    public BinedBookmarksModule() {
        bookmarkRecords.add(new BookmarkRecord(3, 5, Color.RED));
        bookmarkRecords.add(new BookmarkRecord(10, 20, Color.BLUE));
        bookmarkRecords.add(new BookmarkRecord(15, 3, Color.GREEN));
    }

    @Override
    public void init(XBModuleHandler application) {
        this.application = (XBApplication) application;
    }

    public void initEditorProvider(EditorProviderVariant variant) {
    }

    public void setEditorProvider(EditorProvider editorProvider) {
        this.editorProvider = editorProvider;

        BinedModule binedModule = application.getModuleRepository().getModuleByInterface(BinedModule.class);
        bookmarksPositionColorModifier = new BookmarksPositionColorModifier(bookmarkRecords);
        binedModule.addPainterColorModifier(bookmarksPositionColorModifier);
    }

    @Override
    public void unregisterModule(String moduleId) {
    }

    public void registerEditMenuActions() {
        ActionModuleApi actionModule = application.getModuleRepository().getModuleByInterface(ActionModuleApi.class);
        actionModule.registerMenuItem(FrameModuleApi.EDIT_MENU_ID, MODULE_ID, getManageBookmarksAction(), new MenuPosition(PositionMode.BOTTOM));
    }

    @Nonnull
    public AbstractAction getManageBookmarksAction() {
        if (manageBookmarksAction == null) {
            ensureSetup();
            manageBookmarksAction = new ManageBookmarksAction();
            manageBookmarksAction.setup(application, editorProvider, resourceBundle);
        }

        return manageBookmarksAction;
    }

    @Nonnull
    public ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            resourceBundle = LanguageUtils.getResourceBundleByClass(BinedBookmarksModule.class);
        }

        return resourceBundle;
    }

    @Nonnull
    public EditorProvider getEditorProvider() {
        return Objects.requireNonNull(editorProvider, "Editor provider was not yet initialized");
    }

    @Nonnull
    public List<BookmarkRecord> getBookmarkRecords() {
        return bookmarkRecords;
    }

    public void setBookmarkRecords(List<BookmarkRecord> records) {
        bookmarkRecords.clear();
        bookmarkRecords.addAll(records);
        bookmarksPositionColorModifier.notifyBookmarksChanged();
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
