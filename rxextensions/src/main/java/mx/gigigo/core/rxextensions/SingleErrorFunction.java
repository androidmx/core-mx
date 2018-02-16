package mx.gigigo.core.rxextensions;

import java.io.IOException;

import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
/**
 * @author VT - January 25, 2018.
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class SingleErrorFunction<T, E extends ResponseError>
        implements Function<Throwable, SingleSource<? extends T>> {

    protected final HttpErrorHandling httpErrorHandling;
    protected final Class<E> errorClass;

    public SingleErrorFunction(HttpErrorHandling httpErrorHandling, Class<E> errorClass){
        this.httpErrorHandling = httpErrorHandling;
        this.errorClass = errorClass;
    }

    protected abstract ResponseState getResponseState(String errorBody, int code)
            throws IOException;
}
