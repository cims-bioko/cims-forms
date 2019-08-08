package org.cimsbioko.forms.http;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;

import java.io.IOException;

public interface TokenProvider {
    String getToken() throws AuthenticatorException, OperationCanceledException, IOException;
    void invalidateToken(String token);
}
