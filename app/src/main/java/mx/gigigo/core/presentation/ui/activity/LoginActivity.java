package mx.gigigo.core.presentation.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mx.gigigo.core.R;

public class LoginActivity extends CoreBaseActvity {

    public static int TYPE_LOGIN_REGISTER = 0;
    public static final int TYPE_LOGIN = 1;
    public static final int TYPE_REGISTER = 2;

    @BindView(R.id.tv_login_email)
    TextView tvLoginEmail;

    @BindView(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onInitializeUIComponents() {

    }

    @Override
    protected void onInitializeMembers() {

    }

    @OnClick({R.id.tv_login_email, R.id.tv_register})
    public void onClickView(View view) {
        TYPE_LOGIN_REGISTER = view.getId() == R.id.tv_login_email ? TYPE_LOGIN : TYPE_REGISTER;
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
