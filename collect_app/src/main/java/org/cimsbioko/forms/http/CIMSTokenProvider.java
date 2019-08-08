package org.cimsbioko.forms.http;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import org.cimsbioko.forms.application.FormsApp;

import java.io.IOException;

public class CIMSTokenProvider implements TokenProvider {

    private static final String ACCOUNT_TYPE = "org.openhds.mobile";
    private static final String AUTH_TOKEN_TYPE = "Device";

    @Override
    public String getToken() throws AuthenticatorException, OperationCanceledException, IOException {
        Account account = getFirstAccount();
        return getAccountManager().blockingGetAuthToken(account, AUTH_TOKEN_TYPE, true);
    }

    @Override
    public void invalidateToken(String token) {
        getAccountManager().invalidateAuthToken(ACCOUNT_TYPE, token);
    }

    private static Account[] getAccounts() {
        return getAccountManager().getAccountsByType(ACCOUNT_TYPE);
    }

    private static AccountManager getAccountManager() {
        return AccountManager.get(FormsApp.getInstance().getApplicationContext());
    }

    private static Account getFirstAccount() {
        Account[] accounts = getAccounts();
        return accounts.length > 0 ? accounts[0] : null;
    }
}
