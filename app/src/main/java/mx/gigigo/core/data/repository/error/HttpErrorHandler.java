package mx.gigigo.core.data.repository.error;

import android.content.Context;

import mx.gigigo.core.retrofitextensions.IHttpErrorHandling;

/**
 * @author VT - January 26, 2018.
 * @version 0.0.1
 * @since 0.0.1
 */
public class HttpErrorHandler
        extends IHttpErrorHandling {

    public HttpErrorHandler(Context context) {
        super(context);
    }

}