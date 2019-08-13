/*
 * Copyright (C) 2018 Callum Stott
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.cimsbioko.forms.preferences;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;
import org.cimsbioko.forms.listeners.ActionListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import static org.cimsbioko.forms.preferences.AdminKeys.ALL_KEYS;
import static org.cimsbioko.forms.preferences.AdminKeys.KEY_ADMIN_PW;
import static org.cimsbioko.forms.preferences.GeneralKeys.GENERAL_KEYS;

public class PreferenceSaver {

    private final AdminSharedPreferences adminSharedPreferences;

    public PreferenceSaver(AdminSharedPreferences adminSharedPreferences) {
        this.adminSharedPreferences = adminSharedPreferences;
    }

    public void fromJSON(String content, @Nullable ActionListener listener) {
        try {
            JSONObject settingsJson = new JSONObject(content);
            Map<String, Object> generalPrefs = convertJSONToMap(settingsJson.getJSONObject("general"));
            JSONObject adminPrefsJson = settingsJson.getJSONObject("admin");

            if (!(new PreferenceValidator(GENERAL_KEYS).isValid(generalPrefs))) {
                if (listener != null) {
                    listener.onFailure(new GeneralSharedPreferences.ValidationException());
                }

                return;
            }

            for (String key : getAllAdminKeys()) {

                if (adminPrefsJson.has(key)) {
                    Object value = adminPrefsJson.get(key);
                    adminSharedPreferences.save(key, value);
                } else {
                    adminSharedPreferences.reset(key);
                }
            }

            AutoSendPreferenceMigrator.migrate(settingsJson.getJSONObject("general"));

            if (listener != null) {
                listener.onSuccess();
            }
        } catch (JSONException exception) {
            if (listener != null) {
                listener.onFailure(exception);
            }
        }
    }

    private static Map<String, Object> convertJSONToMap(JSONObject json) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keys = json.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            map.put(key, json.get(key));
        }

        return map;
    }

    private static Collection<String> getAllAdminKeys() {
        Collection<String> keys = new HashSet<>(ALL_KEYS);
        keys.add(KEY_ADMIN_PW);
        return keys;
    }
}
