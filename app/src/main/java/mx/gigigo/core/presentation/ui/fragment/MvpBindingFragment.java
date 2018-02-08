package mx.gigigo.core.presentation.ui.fragment;


import butterknife.ButterKnife;
import butterknife.Unbinder;
import mx.gigigo.core.mvp.MvpFragment;
import mx.gigigo.core.mvp.Presenter;
import mx.gigigo.core.mvp.View;

/**
 * @author JG - January 05, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class MvpBindingFragment<V extends View, P extends Presenter<V>>
        extends MvpFragment<V, P> {

    private Unbinder unbinder;

    @Override
    protected void onBindView(android.view.View root) {
        unbinder = ButterKnife.bind(this, root);
    }

    @Override
    protected void onUnbindView() {
        if(null != unbinder) unbinder.unbind();
    }
}
