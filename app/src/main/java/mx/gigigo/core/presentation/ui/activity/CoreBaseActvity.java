package mx.gigigo.core.presentation.ui.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import mx.gigigo.core.R;
import mx.gigigo.core.presentation.ui.fragment.DetailUserFragment;
import mx.gigigo.core.rxmvp.BaseActivity;

/**
 * Created by Gigio on 18/01/18.
 */

public class CoreBaseActvity extends BaseActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onInitializeUIComponents() {

    }

    @Override
    protected void onInitializeMembers() {

    }

    @Override
    protected void onBindView() {

    }

    @Override
    protected void onUnbindView() {

    }
    public void initFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    public void addFragment(int container, Fragment fragment){
        fragmentTransaction.add(container, fragment);
        fragmentTransaction.commit();
    }
}