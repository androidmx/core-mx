package mx.gigigo.core.rxmvp;

import io.reactivex.ObservableSource;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * Created by Gigio on 25/01/18.
 */

public  abstract class ErrorHandlerFunction<T, E extends ResponseError, P> {

    private final Class<E> errorClass;

    public ErrorHandlerFunction(Class<E> errorClass) {
        this.errorClass = errorClass;
    }

    public abstract ResponseState getResponseState(P response) throws Exception;

}
