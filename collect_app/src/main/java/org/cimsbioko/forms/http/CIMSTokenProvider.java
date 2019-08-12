package org.cimsbioko.forms.http;

import android.accounts.Account;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;

import java.io.IOException;

import static org.cimsbioko.forms.utilities.CIMSUtils.*;

public class CIMSTokenProvider implements TokenProvider {

    @Override
    public String getToken() throws AuthenticatorException, OperationCanceledException, IOException {
        Account account = getFirstAccount();
        return getAccountManager().blockingGetAuthToken(account, AUTH_TOKEN_TYPE, true);
    }

    @Override
    public void invalidateToken(String token) {
        getAccountManager().invalidateAuthToken(ACCOUNT_TYPE, token);
    }
}
