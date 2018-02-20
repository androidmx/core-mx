package mx.gigigo.core.domain.repository.error;

import java.net.HttpURLConnection;

import mx.gigigo.core.rxextensions.HttpErrorHandling;

/**
 * @author VT - January 26, 2018.
 * @version 0.0.1
 * @since 0.0.1
 */
public class HttpErrorHandler
        extends HttpErrorHandling {

    public HttpErrorHandler() {
        put(HttpURLConnection.HTTP_CONFLICT, "Conflict");
    }

}