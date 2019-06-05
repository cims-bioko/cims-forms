package org.cimsbioko.forms.injection.config;

import android.app.Application;
import android.content.Context;
import android.telephony.SmsManager;
import android.webkit.MimeTypeMap;

import com.google.android.gms.analytics.Tracker;

import org.cimsbioko.forms.application.Collect;
import org.cimsbioko.forms.dao.FormsDao;
import org.cimsbioko.forms.dao.InstancesDao;
import org.cimsbioko.forms.events.RxEventBus;
import org.cimsbioko.forms.http.CollectServerClient;
import org.cimsbioko.forms.http.CollectThenSystemContentTypeMapper;
import org.cimsbioko.forms.http.OkHttpConnection;
import org.cimsbioko.forms.http.OpenRosaHttpInterface;
import org.cimsbioko.forms.tasks.sms.SmsSubmissionManager;
import org.cimsbioko.forms.tasks.sms.contracts.SmsSubmissionManagerContract;
import org.cimsbioko.forms.utilities.DownloadFormListUtils;
import org.cimsbioko.forms.utilities.PermissionUtils;
import org.cimsbioko.forms.utilities.WebCredentialsUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Add dependency providers here (annotated with @Provides)
 * for objects you need to inject
 */
@Module
public class AppDependencyModule {

    @Provides
    public SmsManager provideSmsManager() {
        return SmsManager.getDefault();
    }

    @Provides
    SmsSubmissionManagerContract provideSmsSubmissionManager(Application application) {
        return new SmsSubmissionManager(application);
    }

    @Provides
    Context context(Application application) {
        return application;
    }

    @Provides
    public InstancesDao provideInstancesDao() {
        return new InstancesDao();
    }

    @Provides
    public FormsDao provideFormsDao() {
        return new FormsDao();
    }

    @Provides
    @Singleton
    RxEventBus provideRxEventBus() {
        return new RxEventBus();
    }

    @Provides
    MimeTypeMap provideMimeTypeMap() {
        return MimeTypeMap.getSingleton();
    }

    @Provides
    OpenRosaHttpInterface provideHttpInterface(MimeTypeMap mimeTypeMap) {
        return new OkHttpConnection(null, new CollectThenSystemContentTypeMapper(mimeTypeMap));
    }

    @Provides
    public CollectServerClient provideCollectServerClient(OpenRosaHttpInterface httpInterface, WebCredentialsUtils webCredentialsUtils) {
        return new CollectServerClient(httpInterface, webCredentialsUtils);
    }

    @Provides
    WebCredentialsUtils provideWebCredentials() {
        return new WebCredentialsUtils();
    }

    @Provides
    DownloadFormListUtils provideDownloadFormListUtils(
            Application application,
            CollectServerClient collectServerClient,
            WebCredentialsUtils webCredentialsUtils,
            FormsDao formsDao) {
        return new DownloadFormListUtils(
                application,
                collectServerClient,
                webCredentialsUtils,
                formsDao
        );
    }

    @Provides
    @Singleton
    public Tracker providesTracker(Application application) {
        return ((Collect) application).getDefaultTracker();
    }

    @Provides
    public PermissionUtils providesPermissionUtils() {
        return new PermissionUtils();
    }
}
