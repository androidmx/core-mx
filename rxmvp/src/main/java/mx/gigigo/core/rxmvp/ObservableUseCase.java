package mx.gigigo.core.rxmvp;

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

    public void execute(DisposableObserver<T> disposableObserver, P parameters) {
        if(null == disposableObserver) {
            throw new NullPointerException("DisposableObserver must not be null");
        }

        final Observable<T> observable = createObservableUseCase(parameters)
                .subscribeOn(executorThread)
                .observeOn(uiThread);

        DisposableObserver observer = observable.subscribeWith(disposableObserver);
        compositeDisposable.add(observer);
    }

    protected abstract Observable<T> createObservableUseCase(P parameters);
}
