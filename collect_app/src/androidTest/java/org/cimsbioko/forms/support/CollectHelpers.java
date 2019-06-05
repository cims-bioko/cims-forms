package org.cimsbioko.forms.support;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.cimsbioko.forms.application.FormsApp;
import org.cimsbioko.forms.injection.config.AppDependencyComponent;
import org.cimsbioko.forms.logic.FormController;

public final class CollectHelpers {

    private CollectHelpers() {}

    public static FormController waitForFormController() throws InterruptedException {
        if (FormsApp.getInstance().getFormController() == null) {
            do {
                Thread.sleep(1);
            } while (FormsApp.getInstance().getFormController() == null);
        }

        return FormsApp.getInstance().getFormController();
    }

    public static AppDependencyComponent getAppDependencyComponent() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FormsApp application = (FormsApp) context;
        return application.getComponent();
    }
}
