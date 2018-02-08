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
import mx.gigigo.core.presentation.ui.fragment.listuser.ListUserKey;
import mx.gigigo.core.presentation.ui.fragment.listuser.ListUsersFragment;


public class ListUsersActivity extends CoreBaseActvity implements StateChanger {
    private static final String TAG = ListUsersActivity.class.getName();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_users;
    }

    @Override
    protected void onCreateBase(Bundle savedInstanceState) {
        super.onCreateBase(savedInstanceState);
        backstackDelegate = new BackstackDelegate(null);
        backstackDelegate.onCreate(savedInstanceState, getLastNonConfigurationInstance(), HistoryBuilder.single(ListUserKey.create()));
        backstackDelegate.registerForLifecycleCallbacks(this);
        fragmentStateChanger = new FragmentStateChanger(getSupportFragmentManager(), R.id.container);
        backstackDelegate.setStateChanger(this);

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
