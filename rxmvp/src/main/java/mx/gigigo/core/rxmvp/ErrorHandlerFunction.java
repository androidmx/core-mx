package mx.gigigo.core.rxmvp;

import java.io.IOException;

/**
 * @author VT - January 25, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class ErrorHandlerFunction<T> {
    public abstract ResponseState getResponseState(T response) throws IOException;
}
