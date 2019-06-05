package org.cimsbioko.forms;

import android.content.Context;

import org.cimsbioko.forms.utilities.PlayServicesUtil;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(PlayServicesUtil.class)
public abstract class ShadowPlayServicesUtil {

    @Implementation
    public static boolean isGooglePlayServicesAvailable(Context context) {
        return true;
    }
}

