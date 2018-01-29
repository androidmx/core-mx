package mx.gigigo.core.presentation.ui.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zhuinden.simplestack.BackstackDelegate;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import mx.gigigo.core.R;
import mx.gigigo.core.presentation.ui.fragment.DetailUserFragment;
import mx.gigigo.core.rxmvp.BaseActivity;

/**
 * Created by Gigio on 18/01/18.
 */

public class CoreBaseActvity extends BaseActivity {
    private Unbinder unbinder;
    public FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    BackstackDelegate backstackDelegate;
    FragmentStateChanger fragmentStateChanger;


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
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onUnbindView() {
        if(unbinder != null){
            unbinder.unbind();
        }
    }
    public void initFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    public void addFragment(int container, Fragment fragment, String tag){
        fragmentTransaction.add(container, fragment, tag);
        fragmentTransaction.commit();
    }

    public void addFragment(int container, Fragment fragment){
        fragmentTransaction.add(container, fragment);
        fragmentTransaction.commit();
    }
}
