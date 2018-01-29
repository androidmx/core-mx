package mx.gigigo.core.rxmvp;

import java.io.IOException;

import io.reactivex.ObservableSource;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * Created by Gigio on 25/01/18.
 */

public abstract class ErrorHandlerFunction<T> {

    public abstract ResponseState getResponseState(T response) throws IOException;

}
