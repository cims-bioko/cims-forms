/*
 * Copyright (C) 2009 University of Washington
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

package org.cimsbioko.forms.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import org.cimsbioko.forms.application.FormsApp;
import org.cimsbioko.forms.events.ReadPhoneStatePermissionRxEvent;
import org.cimsbioko.forms.events.RxEventBus;
import org.javarosa.core.services.IPropertyManager;
import org.javarosa.core.services.properties.IPropertyRules;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.cimsbioko.forms.preferences.GeneralKeys.KEY_METADATA_EMAIL;
import static org.cimsbioko.forms.preferences.GeneralKeys.KEY_METADATA_PHONENUMBER;

/**
 * Returns device properties and metadata to JavaRosa
 *
 * @author Yaw Anokwa (yanokwa@gmail.com)
 */
public class PropertyManager implements IPropertyManager {

    public static final String PROPMGR_DEVICE_ID        = "deviceid";
    public static final String PROPMGR_SUBSCRIBER_ID    = "subscriberid";
    public static final String PROPMGR_SIM_SERIAL       = "simserial";
    public static final String PROPMGR_PHONE_NUMBER     = "phonenumber";
    public static final String PROPMGR_USERNAME         = "username";
    public static final String PROPMGR_EMAIL            = "email";

    public static final String SCHEME_USERNAME     = "username";
    private static final String SCHEME_TEL          = "tel";
    private static final String SCHEME_MAILTO       = "mailto";
    private static final String SCHEME_IMSI         = "imsi";
    private static final String SCHEME_SIMSERIAL    = "simserial";

    private final Map<String, String> properties = new HashMap<>();

    @Inject
    RxEventBus eventBus;

    public String getName() {
        return "Property Manager";
    }

    private static class IdAndPrefix {
        String id;
        String prefix;

        IdAndPrefix(String id, String prefix) {
            this.id = id;
            this.prefix = prefix;
        }
    }

    public PropertyManager(Context context) {
        Timber.i("calling constructor");

        FormsApp.getInstance().getComponent().inject(this);
        try {
            // Device-defined properties
            IdAndPrefix idp = findDeviceId(context);
            final String blank = "";
            putProperty(PROPMGR_DEVICE_ID,     idp.prefix,          idp.id);
            putProperty(PROPMGR_PHONE_NUMBER,  SCHEME_TEL,          blank);
            putProperty(PROPMGR_SUBSCRIBER_ID, SCHEME_IMSI,         blank);
            putProperty(PROPMGR_SIM_SERIAL,    SCHEME_SIMSERIAL,    blank);
        } catch (SecurityException e) {
            Timber.e(e);
        }

        // User-defined properties. Will replace any above with the same PROPMGR_ name.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        initUserDefined(prefs, KEY_METADATA_PHONENUMBER, PROPMGR_PHONE_NUMBER,  SCHEME_TEL);
        initUserDefined(prefs, KEY_METADATA_EMAIL,       PROPMGR_EMAIL,         SCHEME_MAILTO);
    }

    private IdAndPrefix findDeviceId(Context context) throws SecurityException {
        final String androidIdName = Settings.Secure.ANDROID_ID;
        String deviceId = Settings.Secure.getString(context.getContentResolver(), androidIdName);
        return new IdAndPrefix(deviceId, androidIdName);
    }

    /**
     * Initializes a property and its associated “with URI” property, from shared preferences.
     * @param preferences the shared preferences object to be used
     * @param prefKey the preferences key
     * @param propName the name of the property to set
     * @param scheme the scheme for the associated “with URI” property
     */
    private void initUserDefined(SharedPreferences preferences, String prefKey,
                                 String propName, String scheme) {
        putProperty(propName, scheme, preferences.getString(prefKey, null));
    }

    public void putProperty(String propName, String scheme, String value) {
        if (value != null) {
            properties.put(propName, value);
            properties.put(withUri(propName), scheme + ":" + value);
        }
    }

    @Override
    public List<String> getProperty(String propertyName) {
        return null;
    }

    @Override
    public String getSingularProperty(String propertyName) {
        return properties.get(propertyName.toLowerCase(Locale.ENGLISH));
    }

    @Override
    public void setProperty(String propertyName, String propertyValue) {
    }

    @Override
    public void setProperty(String propertyName, List<String> propertyValue) {
    }

    @Override
    public void addRules(IPropertyRules rules) {
    }

    @Override
    public List<IPropertyRules> getRules() {
        return null;
    }

    public static String withUri(String name) {
        return "uri:" + name;
    }
}
