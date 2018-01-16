package mx.gigigo.core.rxmvp;

/**
 * @author JG - December 13, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Presenter<V extends View> {

    void attachView(V view);

    void detachView();

    void resume();

    void pause();

    void destroy();

    void handleError(Throwable exception);
}
