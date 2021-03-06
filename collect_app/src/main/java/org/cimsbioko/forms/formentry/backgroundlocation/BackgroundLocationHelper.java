package org.cimsbioko.forms.formentry.backgroundlocation;

import android.location.Location;

import org.cimsbioko.forms.application.FormsApp;
import org.cimsbioko.forms.location.client.LocationClients;
import org.cimsbioko.forms.logic.AuditConfig;
import org.cimsbioko.forms.logic.AuditEvent;
import org.cimsbioko.forms.preferences.GeneralSharedPreferences;
import org.cimsbioko.forms.utilities.PermissionUtils;

import static org.cimsbioko.forms.preferences.GeneralKeys.KEY_BACKGROUND_LOCATION;

/**
 * Wrapper on resources needed by {@link BackgroundLocationManager} to make testing easier.
 *
 * Ideally this would be replaced by more coherent abstractions in the future.
 *
 * The methods on the {@link org.cimsbioko.forms.logic.FormController} are wrapped here rather
 * than the form controller being passed in when constructing the {@link BackgroundLocationManager}
 * because the form controller isn't set until
 * {@link org.cimsbioko.forms.activities.FormEntryActivity}'s onCreate.
 */
public class BackgroundLocationHelper {
    boolean isAndroidLocationPermissionGranted() {
        return PermissionUtils.areLocationPermissionsGranted(FormsApp.getInstance().getApplicationContext());
    }

    boolean isBackgroundLocationPreferenceEnabled() {
        return GeneralSharedPreferences.getInstance().getBoolean(KEY_BACKGROUND_LOCATION, true);
    }

    boolean arePlayServicesAvailable() {
        return LocationClients.areGooglePlayServicesAvailable(FormsApp.getInstance().getApplicationContext());
    }

    /**
     * @return true if the global form controller has been initialized.
     */
    boolean isCurrentFormSet() {
        return FormsApp.getInstance().getFormController() != null;
    }

    /**
     * @return true if the current form definition requests any kind of background location.
     *
     * Precondition: the global form controller has been initialized.
     */
    boolean currentFormCollectsBackgroundLocation() {
        return FormsApp.getInstance().getFormController().currentFormCollectsBackgroundLocation();
    }

    /**
     * @return true if the current form definition requests a background location audit, false
     * otherwise.
     *
     * Precondition: the global form controller has been initialized.
     */
    boolean currentFormAuditsLocation() {
        return FormsApp.getInstance().getFormController().currentFormAuditsLocation();
    }

    /**
     * @return the configuration for the audit requested by the current form definition.
     *
     * Precondition: the global form controller has been initialized.
     */
    AuditConfig getCurrentFormAuditConfig() {
        return FormsApp.getInstance().getFormController().getSubmissionMetadata().auditConfig;
    }

    /**
     * Logs an audit event of the given type.
     *
     * Precondition: the global form controller has been initialized.
     */
    void logAuditEvent(AuditEvent.AuditEventType eventType) {
        FormsApp.getInstance().getFormController().getAuditEventLogger().logEvent(eventType, false);
    }

    /**
     * Provides the location to the global audit event logger.
     *
     * Precondition: the global form controller has been initialized.
     */
    void provideLocationToAuditLogger(Location location) {
        FormsApp.getInstance().getFormController().getAuditEventLogger().addLocation(location);
    }
}
