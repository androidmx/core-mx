package mx.gigigo.core.rxmvp;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author JG - December 13, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class BasePresenter<V extends View>
        implements Presenter<V> {

    protected final String TAG = getClass().getSimpleName();

    private WeakReference<V> viewReference;
    private List<Object> parameters;

    public void attachView(V view) {
        viewReference = new WeakReference<>(view);
    }

    public void detachView() {
        if (viewReference != null) {
            viewReference.clear();
            viewReference = null;
        }
    }

    @Override
    public void resume() { }

    @Override
    public void pause() { }

    @Override
    public void destroy() { }

    @Override
    public void handleError(Throwable exception) { }

    public V getView() {
        return null == viewReference ? null : viewReference.get();
    }

    public boolean isViewAttached() {
        return null != viewReference && null != viewReference.get();
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException
            extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(View) before requesting data to the Presenter");
        }
    }
}
