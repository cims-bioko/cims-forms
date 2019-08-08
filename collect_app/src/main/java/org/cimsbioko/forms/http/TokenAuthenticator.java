package org.cimsbioko.forms.http;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import org.jetbrains.annotations.NotNull;
import timber.log.Timber;

import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenAuthenticator implements Authenticator {

    private static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer\\s+(\\S+)$");
    private final TokenProvider provider;

    public TokenAuthenticator(TokenProvider provider) {
        this.provider = provider;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, Response response) {
        try {
            synchronized (this) {
                Request initialRequest = response.request();
                if (isAuthenticated(initialRequest)) {
                    provider.invalidateToken(extractTokenValue(initialRequest));
                }
                String token = provider.getToken();
                if (token != null) {
                    return genBearerRequest(initialRequest, token);
                }
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        return null;
    }

    private static boolean isAuthenticated(Request request) {
        String auth = getAuthHeader(request);
        return auth != null;
    }

    private static String getAuthHeader(Request request) {
        return request.header("Authorization");
    }

    @NotNull
    private static Request genBearerRequest(Request request, String token) {
        return request.newBuilder()
                .removeHeader("Authorization")
                .addHeader("Authorization", genAuthHeaderValue(token))
                .build();
    }

    @NotNull
    private static String genAuthHeaderValue(String token) {
        return String.format("Bearer %s", token);
    }

    private static String extractTokenValue(Request request) {
        String value = getAuthHeader(request);
        Matcher m = BEARER_PATTERN.matcher(value);
        return m.matches() ? m.group(1) : null;
    }
}
