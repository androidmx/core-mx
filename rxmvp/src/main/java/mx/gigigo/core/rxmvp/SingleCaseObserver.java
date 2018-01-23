package mx.gigigo.core.rxmvp;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author JG - January 23, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class SingleCaseObserver<T>
        extends DisposableSingleObserver<T> {

    @Override
    public void onSuccess(T t) { }

    @Override
    public void onError(Throwable e) { }
}
