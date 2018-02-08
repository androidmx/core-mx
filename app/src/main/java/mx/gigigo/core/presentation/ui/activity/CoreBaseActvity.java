package mx.gigigo.core.presentation.ui.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zhuinden.simplestack.BackstackDelegate;
import com.zhuinden.simplestack.StateChange;
import com.zhuinden.simplestack.StateChanger;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import mx.gigigo.core.presentation.ui.fragment.FragmentStateChanger;
import mx.gigigo.core.rxmvp.BaseActivity;

/**
 * Created by Gigio on 18/01/18.
 */

public class CoreBaseActvity extends BaseActivity implements StateChanger {
    private Unbinder unbinder;
    public FragmentManager fragmentManager;


    BackstackDelegate backstackDelegate;
    FragmentStateChanger fragmentStateChanger;

    public BackstackDelegate getBackstackDelegate() {
        return backstackDelegate;
    }

    public void setBackstackDelegate(BackstackDelegate backstackDelegate) {
        this.backstackDelegate = backstackDelegate;
    }

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
    protected void onCreateBase(Bundle savedInstanceState) {
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


    @Override
    public void handleStateChange(@NonNull StateChange stateChange, @NonNull Callback completionCallback) {

    }
}
