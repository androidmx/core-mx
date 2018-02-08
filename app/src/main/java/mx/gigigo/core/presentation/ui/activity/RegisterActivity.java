package mx.gigigo.core.presentation.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zhuinden.simplestack.BackstackDelegate;
import com.zhuinden.simplestack.HistoryBuilder;
import com.zhuinden.simplestack.StateChange;
import com.zhuinden.simplestack.StateChanger;

import mx.gigigo.core.R;
import mx.gigigo.core.presentation.ui.fragment.FragmentStateChanger;
import mx.gigigo.core.presentation.ui.fragment.MvpBindingFragment;
import mx.gigigo.core.presentation.ui.fragment.detail.DetailKey;
import mx.gigigo.core.presentation.ui.fragment.login.RegisterKey;

public class RegisterActivity extends CoreBaseActvity implements StateChanger {
    public static final String TAG = RegisterActivity.class.getName();
    private int type;
    public static final String TYPE = "type";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreateBase(Bundle savedInstanceState) {
        super.onCreateBase(savedInstanceState);
        backstackDelegate = new BackstackDelegate(null);
        backstackDelegate.onCreate(savedInstanceState, getLastNonConfigurationInstance(), HistoryBuilder.single(RegisterKey.create(type)));
        backstackDelegate.registerForLifecycleCallbacks(this);
        fragmentStateChanger = new FragmentStateChanger(getSupportFragmentManager(), R.id.container);
        backstackDelegate.setStateChanger(this);
    }



    @Override
    protected void onRestoreExtras(Bundle arguments) {
        super.onRestoreExtras(arguments);

        type = arguments.getInt(TYPE);
    }


    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return backstackDelegate.onRetainCustomNonConfigurationInstance();
    }

    @Override
    public void onBackPressed() {
        if(!backstackDelegate.onBackPressed()){
            super.onBackPressed();
        }
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if(TAG.equals(name)){
            return this;
        }
        return super.getSystemService(name);
    }

    @Override
    public void handleStateChange(@NonNull StateChange stateChange, @NonNull Callback completionCallback) {
        if(stateChange.topNewState().equals(stateChange.topPreviousState())){
            completionCallback.stateChangeComplete();
            return;
        }
        fragmentStateChanger.handleStateChange(stateChange);
        completionCallback.stateChangeComplete();
    }
}
