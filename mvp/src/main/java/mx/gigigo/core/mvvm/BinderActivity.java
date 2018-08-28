package mx.gigigo.core.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by israel on 23/01/18.
 */

public abstract class BinderActivity<T> extends AppCompatActivity {

    ViewDataBinding binder;

    protected int getLayoutId() { return 0; }
    protected void onInitializeUIComponents() {}
    protected void onInitializeMembers() {}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, getLayoutId());

        onInitializeMembers();
        onInitializeUIComponents();
    }

    public T getBinder() {
        return (T)binder;
    }

    public void replaceFragment(int container, Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment, fragment.getClass().getName());
        fragmentTransaction.commit();
    }

}
