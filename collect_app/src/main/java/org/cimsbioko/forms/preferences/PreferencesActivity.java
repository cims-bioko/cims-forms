/*
 * Copyright 2018 Nafundi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cimsbioko.forms.preferences;

import android.app.Fragment;
import android.os.Bundle;

import org.cimsbioko.forms.R;
import org.cimsbioko.forms.activities.CollectAbstractActivity;
import org.cimsbioko.forms.application.FormsApp;
import org.cimsbioko.forms.listeners.OnBackPressedListener;
import org.cimsbioko.forms.utilities.ThemeUtils;

public class PreferencesActivity extends CollectAbstractActivity {

    public static final String TAG = "GeneralPreferencesFragment";
    public static final String INTENT_KEY_ADMIN_MODE = "adminMode";

    private OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(new ThemeUtils(this).getSettingsTheme());

        setTitle(R.string.general_preferences);
        if (savedInstanceState == null) {
            boolean adminMode = getIntent().getBooleanExtra(INTENT_KEY_ADMIN_MODE, false);
            Fragment fragment = GeneralPreferencesFragment.newInstance(adminMode);
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, TAG)
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FormsApp.getInstance().initializeJavaRosa();
    }

    // If the onBackPressedListener is set then onBackPressed is delegated to it.
    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
        } else {
            super.onBackPressed();
        }
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }
}
