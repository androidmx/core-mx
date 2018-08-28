package mx.gigigo.core.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.gigigo.core.mvp.BaseFragment;


/**
 * Created by israel on 23/01/18.
 */

public class BinderFragment<T> extends BaseFragment {

    public ViewDataBinding binder;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        View view = binder.getRoot();
        this.onBindView(view);
        return view;
    }

    public T getBinder(){
        return (T)binder;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onInitializeUIComponents() {}

    @Override
    protected void onInitializeMembers() {}

    @Deprecated
    @Override
    protected void onBindView(View view) {}

    @Deprecated
    @Override
    protected void onUnbindView() {}
}
