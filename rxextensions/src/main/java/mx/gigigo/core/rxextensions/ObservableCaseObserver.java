package mx.gigigo.core.rxextensions;

import io.reactivex.observers.DisposableObserver;

/**
 * @author JG - December 19, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public class ObservableCaseObserver<T>
        extends DisposableObserver<T> {

    @Override
    public void onComplete() { }

    @Override
    public void onError(Throwable e) { }

    @Override
    public void onNext(T t) { }
}
