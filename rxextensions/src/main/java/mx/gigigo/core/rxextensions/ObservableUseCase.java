package mx.gigigo.core.rxextensions;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

/**
 * @author JG - January 23, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class ObservableUseCase<T, P>
        extends UseCase {

    public ObservableUseCase(Scheduler executorThread, Scheduler uiThread) {
        super(executorThread, uiThread);
    }

    public void execute(DisposableObserver<T> observer, P parameters) {
        if(null == observer) {
            throw new NullPointerException("Observer must not be null");
        }

        final Observable<T> observable = createObservableUseCase(parameters)
                .subscribeOn(executorThread)
                .observeOn(uiThread);

        compositeDisposable.add(observable.subscribeWith(observer));
    }

    protected abstract Observable<T> createObservableUseCase(P parameters);
}
