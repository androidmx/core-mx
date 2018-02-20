package mx.gigigo.core.rxextensions;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

/**
 *
 * @param <T> the type of the item emitted by the Single
 * @param <P> the params
 *
 * @author JG - January 23, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class SingleUseCase<T, P>
        extends UseCase {

    public SingleUseCase(Scheduler executorThread, Scheduler uiThread) {
        super(executorThread, uiThread);
    }

    /**
     * Executes the current use case
     * @param observer {@link DisposableSingleObserver} which will be listening to the single build
     * @param parameters the parameters (optionals) used to build/execute the use case
     */
    public void execute(DisposableSingleObserver<T> observer, P parameters) {
        if(null == observer) {
            throw new NullPointerException("Observer must not be null");
        }

        final Single<T> observable = createObservableUseCase(parameters)
                .subscribeOn(executorThread)
                .observeOn(uiThread);

        compositeDisposable.add(observable.subscribeWith(observer));
    }

    /**
     * Builds an {@link Single} which will be used when executing the current {@link UseCase}.
     * @param parameters the parameters (optionals) used to build/execute the use case
     * @return an {@link Single}
     */
    protected abstract Single<T> createObservableUseCase(P parameters);
}
