package org.cimsbioko.forms.utilities;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.cimsbioko.forms.http.HttpCredentials;
import org.cimsbioko.forms.http.HttpCredentialsInterface;
import org.cimsbioko.forms.preferences.GeneralSharedPreferences;
import org.cimsbioko.forms.preferences.GeneralKeys;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

@Singleton
public class WebCredentialsUtils {

    private static final Map<String, HttpCredentialsInterface> HOST_CREDENTIALS = new HashMap<>();

    public void saveCredentials(@NonNull String url, @NonNull String username, @NonNull String password) {
        if (username.isEmpty()) {
            return;
        }

        String host = Uri.parse(url).getHost();
        HOST_CREDENTIALS.put(host, new HttpCredentials(username, password));
    }

    /**
     * Forgets the temporary credentials saved in memory for a particular host. This is used when an
     * activity that does some work requiring authentication is called with intent extras specifying
     * credentials. Once the work is done, the temporary credentials are cleared so that different
     * ones can be used on a later request.
     *
     * TODO: is this necessary in all cases it's used? Maybe it's needed if we want to be able to do
     * an authenticated call followed by an anonymous one but even then, can't we pass in null
     * username and password if the intent extras aren't set?
     */
    public void clearCredentials(@NonNull String url) {
        if (url.isEmpty()) {
            return;
        }

        String host = Uri.parse(url).getHost();
        if (host != null) {
            HOST_CREDENTIALS.remove(host);
        }
    }
}
