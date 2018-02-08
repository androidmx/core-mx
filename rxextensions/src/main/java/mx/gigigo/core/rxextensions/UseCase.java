package mx.gigigo.core.rxextensions;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author JG - December 19, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class UseCase {

    protected final CompositeDisposable compositeDisposable;
    protected final Scheduler executorThread;
    protected final Scheduler uiThread;

    public UseCase(Scheduler executorThread,
                   Scheduler uiThread) {
        this.compositeDisposable = new CompositeDisposable();
        this.executorThread = executorThread;
        this.uiThread = uiThread;
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
