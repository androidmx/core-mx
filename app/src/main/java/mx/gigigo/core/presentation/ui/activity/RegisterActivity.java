package mx.gigigo.core.presentation.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.gigigo.core.R;
import mx.gigigo.core.presentation.ui.fragment.MvpBindingFragment;
import mx.gigigo.core.presentation.ui.fragment.RegisterFragment;

public class RegisterActivity extends CoreBaseActvity {
    private int type;
    public static final String TYPE = "type";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onInitializeUIComponents() {
        initFragment();
        MvpBindingFragment fragment = RegisterFragment.newInstance(type);
        addFragment(R.id.container, fragment);
    }

    @Override
    protected void onRestoreExtras(Bundle arguments) {
        super.onRestoreExtras(arguments);

        type = arguments.getInt(TYPE);
    }
}
