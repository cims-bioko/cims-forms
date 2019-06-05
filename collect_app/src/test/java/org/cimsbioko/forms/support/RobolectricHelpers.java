package org.cimsbioko.forms.support;

import org.cimsbioko.forms.application.Collect;
import org.cimsbioko.forms.injection.config.AppDependencyComponent;
import org.cimsbioko.forms.injection.config.AppDependencyModule;
import org.cimsbioko.forms.injection.config.DaggerAppDependencyComponent;
import org.robolectric.RuntimeEnvironment;

public class RobolectricHelpers {

    private RobolectricHelpers() {}

    public static void overrideAppDependencyModule(AppDependencyModule appDependencyModule) {
        AppDependencyComponent testComponent = DaggerAppDependencyComponent.builder()
                .application(RuntimeEnvironment.application)
                .appDependencyModule(appDependencyModule)
                .build();
        ((Collect) RuntimeEnvironment.application).setComponent(testComponent);
    }

    public static AppDependencyComponent getApplicationComponent() {
        return ((Collect) RuntimeEnvironment.application).getComponent();
    }
}
