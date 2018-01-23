package mx.gigigo.core.rxmvp;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author JG - January 23, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class SingleUseCase<T, P>
        extends UseCase {

    public SingleUseCase(Scheduler executorThread, Scheduler uiThread) {
        super(executorThread, uiThread);
    }

    public void execute(DisposableSingleObserver<T> disposableObserver, P parameters) {
        if(null == disposableObserver) {
            throw new NullPointerException("DisposableSingleObserver must not be null");
        }

        final Single<T> observable = createObservableUseCase(parameters)
                .subscribeOn(executorThread)
                .observeOn(uiThread);

        DisposableSingleObserver observer = observable.subscribeWith(disposableObserver);
        compositeDisposable.add(observer);
    }

    protected abstract Single<T> createObservableUseCase(P parameters);
}
