/*
 * Copyright (C) 2011 University of Washington
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

package org.cimsbioko.forms.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Window;

import org.cimsbioko.forms.R;
import org.cimsbioko.forms.application.FormsApp;
import org.cimsbioko.forms.listeners.PermissionListener;
import org.cimsbioko.forms.preferences.GeneralKeys;
import org.cimsbioko.forms.utilities.DialogUtils;
import org.cimsbioko.forms.utilities.PermissionUtils;

import timber.log.Timber;

import java.util.List;

public class SplashScreenActivity extends Activity {

    private static final boolean EXIT = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this splash screen should be a blank slate
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (!isCIMSInstalled(getPackageManager())) {
            promptCIMSInstall(this);
            return;
        }

        new PermissionUtils().requestStoragePermissions(this, new PermissionListener() {
            @Override
            public void granted() {
                // must be at the beginning of any activity that can be called from an external intent
                try {
                    FormsApp.createODKDirs();
                } catch (RuntimeException e) {
                    DialogUtils.showDialog(DialogUtils.createErrorDialog(SplashScreenActivity.this,
                            e.getMessage(), EXIT), SplashScreenActivity.this);
                    return;
                }

                init();
            }

            @Override
            public void denied() {
                // The activity has to finish because CIMS Forms cannot function without these permissions.
                finish();
            }
        });
    }

    public static boolean isCIMSInstalled(PackageManager manager) {
        Intent contentIntent = new Intent("org.cimsbioko.ENTITY_LOOKUP");
        List<ResolveInfo> intentMatches = manager.queryIntentActivities(contentIntent, 0);
        return !intentMatches.isEmpty();
    }

    public static void promptCIMSInstall(final Activity activity) {

        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    launchFormsAppMarketInstall();
                }
                activity.finish();
            }

            private void launchFormsAppMarketInstall() {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=org.cimsbioko"));
                activity.startActivity(intent);
            }
        };

        DialogInterface.OnCancelListener cancelListener = dialog -> activity.finish();

        new AlertDialog.Builder(activity)
                .setTitle(R.string.cims_app_required)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.cims_app_install_prompt)
                .setNegativeButton(R.string.quit_label, clickListener)
                .setPositiveButton(R.string.install_label, clickListener)
                .setOnCancelListener(cancelListener)
                .show();
    }

    private void init() {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();

        setContentView(R.layout.splash_screen);

        // get the shared preferences object
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // get the package info object with version number
        PackageInfo packageInfo = null;
        try {
            packageInfo =
                    getPackageManager().getPackageInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e, "Unable to get package info");
        }

        // if you've increased version code, then update the version number
        if (sharedPreferences.getLong(GeneralKeys.KEY_LAST_VERSION, 0)
                < packageInfo.versionCode) {
            editor.putLong(GeneralKeys.KEY_LAST_VERSION, packageInfo.versionCode);
            editor.apply();
        }

        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }
}
