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
package org.exbin.framework.bined.inspector.preferences;

import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import org.exbin.framework.preferences.api.Preferences;
import javax.annotation.ParametersAreNonnullByDefault;
import org.exbin.framework.bined.inspector.options.DataInspectorOptions;

/**
 * Data inspector preferences.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class DataInspectorPreferences implements DataInspectorOptions {

    public static final String PREFERENCES_SHOW_PARSING_PANEL = "showValuesPanel";
    public static final String PREFERENCES_USE_DEFAULT_FONT = "data_inspector.useDefaultFont";
    public static final String PREFERENCES_TEXT_FONT_PREFIX = "data_inspector.textFont.";
    public static final String PREFERENCES_TEXT_FONT_DEFAULT = PREFERENCES_TEXT_FONT_PREFIX + "default";
    public static final String PREFERENCES_TEXT_FONT_FAMILY = PREFERENCES_TEXT_FONT_PREFIX + "family";
    public static final String PREFERENCES_TEXT_FONT_SIZE = PREFERENCES_TEXT_FONT_PREFIX + "size";
    public static final String PREFERENCES_TEXT_FONT_UNDERLINE = PREFERENCES_TEXT_FONT_PREFIX + "underline";
    public static final String PREFERENCES_TEXT_FONT_STRIKETHROUGH = PREFERENCES_TEXT_FONT_PREFIX + "strikethrough";
    public static final String PREFERENCES_TEXT_FONT_STRONG = PREFERENCES_TEXT_FONT_PREFIX + "strong";
    public static final String PREFERENCES_TEXT_FONT_ITALIC = PREFERENCES_TEXT_FONT_PREFIX + "italic";
    public static final String PREFERENCES_TEXT_FONT_SUBSCRIPT = PREFERENCES_TEXT_FONT_PREFIX + "subscript";
    public static final String PREFERENCES_TEXT_FONT_SUPERSCRIPT = PREFERENCES_TEXT_FONT_PREFIX + "superscript";

    private final Preferences preferences;

    public DataInspectorPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean isShowParsingPanel() {
        return preferences.getBoolean(PREFERENCES_SHOW_PARSING_PANEL, true);
    }

    @Override
    public void setShowParsingPanel(boolean show) {
        preferences.putBoolean(PREFERENCES_SHOW_PARSING_PANEL, show);
    }

    @Override
    public boolean isUseDefaultFont() {
        return preferences.getBoolean(PREFERENCES_USE_DEFAULT_FONT, true);
    }

    @Override
    public void setUseDefaultFont(boolean useDefaultFont) {
        preferences.putBoolean(PREFERENCES_USE_DEFAULT_FONT, useDefaultFont);
    }

    @Nonnull
    @Override
    public Map<TextAttribute, Object> getFontAttributes() {
        Map<TextAttribute, Object> attribs = new HashMap<>();
        Optional<String> fontFamily = preferences.get(PREFERENCES_TEXT_FONT_FAMILY);
        if (fontFamily.isPresent()) {
            attribs.put(TextAttribute.FAMILY, fontFamily.get());
        }
        Optional<String> fontSize = preferences.get(PREFERENCES_TEXT_FONT_SIZE);
        if (fontSize.isPresent()) {
            attribs.put(TextAttribute.SIZE, Integer.valueOf(fontSize.get()).floatValue());
        }
        if (preferences.getBoolean(PREFERENCES_TEXT_FONT_UNDERLINE, false)) {
            attribs.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
        }
        if (preferences.getBoolean(PREFERENCES_TEXT_FONT_STRIKETHROUGH, false)) {
            attribs.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        }
        if (preferences.getBoolean(PREFERENCES_TEXT_FONT_STRONG, false)) {
            attribs.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        }
        if (preferences.getBoolean(PREFERENCES_TEXT_FONT_ITALIC, false)) {
            attribs.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
        }
        if (preferences.getBoolean(PREFERENCES_TEXT_FONT_SUBSCRIPT, false)) {
            attribs.put(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB);
        }
        if (preferences.getBoolean(PREFERENCES_TEXT_FONT_SUPERSCRIPT, false)) {
            attribs.put(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER);
        }
        return attribs;
    }

    @Override
    public void setFontAttributes(Map<TextAttribute, ?> attribs) {
        String value = (String) attribs.get(TextAttribute.FAMILY);
        if (value != null) {
            preferences.put(PREFERENCES_TEXT_FONT_FAMILY, value);
        } else {
            preferences.remove(PREFERENCES_TEXT_FONT_FAMILY);
        }
        Float fontSize = (Float) attribs.get(TextAttribute.SIZE);
        if (fontSize != null) {
            preferences.put(PREFERENCES_TEXT_FONT_SIZE, Integer.toString((int) (float) fontSize));
        } else {
            preferences.remove(PREFERENCES_TEXT_FONT_SIZE);
        }
        preferences.putBoolean(PREFERENCES_TEXT_FONT_UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL.equals(attribs.get(TextAttribute.UNDERLINE)));
        preferences.putBoolean(PREFERENCES_TEXT_FONT_STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON.equals(attribs.get(TextAttribute.STRIKETHROUGH)));
        preferences.putBoolean(PREFERENCES_TEXT_FONT_STRONG, TextAttribute.WEIGHT_BOLD.equals(attribs.get(TextAttribute.WEIGHT)));
        preferences.putBoolean(PREFERENCES_TEXT_FONT_ITALIC, TextAttribute.POSTURE_OBLIQUE.equals(attribs.get(TextAttribute.POSTURE)));
        preferences.putBoolean(PREFERENCES_TEXT_FONT_SUBSCRIPT, TextAttribute.SUPERSCRIPT_SUB.equals(attribs.get(TextAttribute.SUPERSCRIPT)));
        preferences.putBoolean(PREFERENCES_TEXT_FONT_SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER.equals(attribs.get(TextAttribute.SUPERSCRIPT)));
    }
}
