package mx.gigigo.core.rxextensions;

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

    public void execute(DisposableSingleObserver<T> observer, P parameters) {
        if(null == observer) {
            throw new NullPointerException("Observer must not be null");
        }

        final Single<T> observable = createObservableUseCase(parameters)
                .subscribeOn(executorThread)
                .observeOn(uiThread);

        compositeDisposable.add(observable.subscribeWith(observer));
    }

    protected abstract Single<T> createObservableUseCase(P parameters);
}
