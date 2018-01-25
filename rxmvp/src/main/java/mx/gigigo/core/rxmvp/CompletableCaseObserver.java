package mx.gigigo.core.rxmvp;

import io.reactivex.observers.DisposableCompletableObserver;

/**
 * @author JG - January 25, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class CompletableCaseObserver
        extends DisposableCompletableObserver {

    @Override
    public void onComplete() { }

    @Override
    public void onError(Throwable e) { }
}
