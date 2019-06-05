package org.cimsbioko.forms.injection.config;

import android.app.Application;
import android.telephony.SmsManager;

import org.cimsbioko.forms.activities.FormDownloadList;
import org.cimsbioko.forms.activities.FormEntryActivity;
import org.cimsbioko.forms.activities.GoogleDriveActivity;
import org.cimsbioko.forms.activities.GoogleSheetsUploaderActivity;
import org.cimsbioko.forms.activities.InstanceUploaderListActivity;
import org.cimsbioko.forms.adapters.InstanceUploaderAdapter;
import org.cimsbioko.forms.application.Collect;
import org.cimsbioko.forms.events.RxEventBus;
import org.cimsbioko.forms.fragments.DataManagerList;
import org.cimsbioko.forms.http.CollectServerClient;
import org.cimsbioko.forms.http.OpenRosaHttpInterface;
import org.cimsbioko.forms.logic.PropertyManager;
import org.cimsbioko.forms.preferences.ServerPreferencesFragment;
import org.cimsbioko.forms.tasks.InstanceServerUploaderTask;
import org.cimsbioko.forms.tasks.ServerPollingJob;
import org.cimsbioko.forms.tasks.sms.SmsNotificationReceiver;
import org.cimsbioko.forms.tasks.sms.SmsSender;
import org.cimsbioko.forms.tasks.sms.SmsSentBroadcastReceiver;
import org.cimsbioko.forms.tasks.sms.SmsService;
import org.cimsbioko.forms.tasks.sms.contracts.SmsSubmissionManagerContract;
import org.cimsbioko.forms.utilities.AuthDialogUtility;
import org.cimsbioko.forms.utilities.DownloadFormListUtils;
import org.cimsbioko.forms.utilities.FormDownloader;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Dagger component for the application. Should include
 * application level Dagger Modules and be built with Application
 * object.
 *
 * Add an `inject(MyClass myClass)` method here for objects you want
 * to inject into so Dagger knows to wire it up.
 *
 * Annotated with @Singleton so modules can include @Singletons that will
 * be retained at an application level (as this an instance of this components
 * is owned by the Application object).
 *
 * If you need to call a provider directly from the component (in a test
 * for example) you can add a method with the type you are looking to fetch
 * (`MyType myType()`) to this interface.
 *
 * To read more about Dagger visit: https://google.github.io/dagger/users-guide
 **/

@Singleton
@Component(modules = {
        AppDependencyModule.class
})
public interface AppDependencyComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        Builder appDependencyModule(AppDependencyModule testDependencyModule);

        AppDependencyComponent build();
    }

    void inject(Collect collect);

    void inject(SmsService smsService);

    void inject(SmsSender smsSender);

    void inject(SmsSentBroadcastReceiver smsSentBroadcastReceiver);

    void inject(SmsNotificationReceiver smsNotificationReceiver);

    void inject(InstanceUploaderAdapter instanceUploaderAdapter);

    void inject(DataManagerList dataManagerList);

    void inject(PropertyManager propertyManager);

    void inject(FormEntryActivity formEntryActivity);

    void inject(InstanceServerUploaderTask uploader);

    void inject(CollectServerClient collectClient);

    void inject(ServerPreferencesFragment serverPreferencesFragment);

    void inject(FormDownloader formDownloader);

    void inject(ServerPollingJob serverPollingJob);

    void inject(AuthDialogUtility authDialogUtility);

    void inject(FormDownloadList formDownloadList);

    void inject(InstanceUploaderListActivity activity);

    void inject(GoogleDriveActivity googleDriveActivity);

    void inject(GoogleSheetsUploaderActivity googleSheetsUploaderActivity);

    SmsManager smsManager();

    SmsSubmissionManagerContract smsSubmissionManagerContract();

    RxEventBus rxEventBus();

    OpenRosaHttpInterface openRosaHttpInterface();

    DownloadFormListUtils downloadFormListUtils();
}
