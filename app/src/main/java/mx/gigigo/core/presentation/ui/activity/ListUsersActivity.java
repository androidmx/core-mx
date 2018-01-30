package mx.gigigo.core.presentation.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mx.gigigo.core.R;
import mx.gigigo.core.presentation.ui.fragment.DetailUserFragment;
import mx.gigigo.core.presentation.ui.fragment.ListUsersFragment;
import mx.gigigo.core.presentation.ui.fragment.MvpBindingFragment;


public class ListUsersActivity extends CoreBaseActvity {
    private static final String TAG_FRAGMENT = "detail";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_users;
    }

    @Override
    protected void onInitializeUIComponents() {
        initFragment();
        MvpBindingFragment fragment = (MvpBindingFragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT);

        if(fragment == null) {
            fragment = ListUsersFragment.newInstance();
            addFragment(R.id.container, fragment, TAG_FRAGMENT);
        }
    }
}
