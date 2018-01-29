package mx.gigigo.core.presentation.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mx.gigigo.core.R;
import mx.gigigo.core.rxmvp.BaseActivity;

public class LoginActivity extends CoreBaseActvity {
    public static final String TYPE = "type";
    public static final int TYPE_LOGIN = 1;

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
    public void onClickView(View view){
       if(view.getId() == R.id.tv_login_email){
           Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
           intent.putExtra(TYPE, TYPE_LOGIN);
           startActivity(intent);
       }else{
           Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
           startActivity(intent);
       }
    }


}
