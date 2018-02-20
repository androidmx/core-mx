package mx.gigigo.core.rxextensions;

import java.io.IOException;

import io.reactivex.functions.Function;

/**
 * Abstract class for error handling
 *
 * @param <E> The class type, must inherit from {@link ResponseError}
 * @param <R> The output value type, must be {@link io.reactivex.ObservableSource}
 *           or {@link io.reactivex.SingleSource}
 *           or {@link io.reactivex.CompletableSource}
 *
 * @author JG - February 16, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class SourceErrorFunction<E extends ResponseError, R>
        implements Function<Throwable, R> {

    protected final HttpErrorHandling httpErrorHandling;
    protected final Class<E> errorClass;

    /**
     *
     * @param httpErrorHandling
     * @param errorClass
     */
    public SourceErrorFunction(HttpErrorHandling httpErrorHandling, Class<E> errorClass){
        this.httpErrorHandling = httpErrorHandling;
        this.errorClass = errorClass;
    }

    /**
     *
     * @param errorBody
     * @param code
     * @return
     * @throws IOException
     */
    protected abstract ResponseState getResponseState(String errorBody, int code)
            throws IOException;
}
