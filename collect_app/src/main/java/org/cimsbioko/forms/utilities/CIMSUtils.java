package org.cimsbioko.forms.utilities;

import android.accounts.Account;
import android.accounts.AccountManager;
import org.cimsbioko.forms.application.FormsApp;

public class CIMSUtils {

    public static final String ACCOUNT_TYPE = "org.openhds.mobile";
    public static final String AUTH_TOKEN_TYPE = "Device";

    public static Account[] getAccounts() {
        return getAccountManager().getAccountsByType(ACCOUNT_TYPE);
    }

    public static AccountManager getAccountManager() {
        return AccountManager.get(FormsApp.getInstance().getApplicationContext());
    }

    public static Account getFirstAccount() {
        Account[] accounts = getAccounts();
        return accounts.length > 0 ? accounts[0] : null;
    }

    public static String getFirstAccountUsername() {
        return getFirstAccount() == null? null : getFirstAccount().name;
    }
}
