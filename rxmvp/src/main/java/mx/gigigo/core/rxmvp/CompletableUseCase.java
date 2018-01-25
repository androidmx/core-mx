package mx.gigigo.core.rxmvp;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableCompletableObserver;

/**
 * @author JG - January 25, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class CompletableUseCase<P>
        extends UseCase {

    public CompletableUseCase(Scheduler executorThread, Scheduler uiThread) {
        super(executorThread, uiThread);
    }

    public void execute(DisposableCompletableObserver observer, P parameters) {
        if (null == observer) {
            throw new NullPointerException("Observer must not be null");
        }

        final Completable observable = createObservableUseCase(parameters)
                .subscribeOn(executorThread)
                .observeOn(uiThread);

        compositeDisposable.add(observable.subscribeWith(observer));
    }

    protected abstract Completable createObservableUseCase(P parameters);
}
