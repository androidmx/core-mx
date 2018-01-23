package mx.gigigo.core.rxmvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author JG - December 19, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class MvpActivity<V extends mx.gigigo.core.rxmvp.View, P extends Presenter<V>>
        extends BaseActivity {

    protected P presenter;
    protected abstract P createPresenter();

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(null == presenter) presenter = createPresenter();

        if(null == presenter) throw new NullPointerException("The presenter must not be null.");

        if(!(this instanceof View))
            throw new MvpActivityNotImplementedException();

        presenter.attachView((V) this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(null != presenter) presenter.detachView();
    }

    public static class MvpActivityNotImplementedException
            extends RuntimeException {
        public MvpActivityNotImplementedException() {
            super("The MvpActivity must implement View. This is required by the presenter.");
        }
    }
}
