package mx.gigigo.core.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author JG - December 13, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class MvpFragment<V extends View, P extends Presenter<V>>
        extends BaseFragment {

    protected P presenter;
    protected abstract P createPresenter();

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(null == presenter) presenter = createPresenter();

        if(null == presenter) throw new NullPointerException("The presenter must not be null.");

        if(!(this instanceof View))
            throw new MvpFragmentNotImplementedException();

        presenter.attachView((V) this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(null != presenter) presenter.detachView();
    }

    public static class MvpFragmentNotImplementedException
            extends RuntimeException {
        public MvpFragmentNotImplementedException() {
            super("The MvpFragment must implement View. This is required by the presenter.");
        }
    }
}
