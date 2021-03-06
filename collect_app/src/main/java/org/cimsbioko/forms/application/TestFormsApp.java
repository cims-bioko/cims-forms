package org.cimsbioko.forms.application;

import com.squareup.leakcanary.RefWatcher;

/**
 * @author James Knight
 *
 * This class will automatically be used by Robolectric
 * tests as a replacement for the application class configured
 * in the Android manifest as it prefixes that class with `Test`.
 */

public class TestFormsApp extends FormsApp {

    @Override
    protected RefWatcher setupLeakCanary() {
        // No leakcanary in unit tests.
        return RefWatcher.DISABLED;
    }

    @Override
    protected void setupOSMDroid() {
        // no op for Robolectric
    }
}