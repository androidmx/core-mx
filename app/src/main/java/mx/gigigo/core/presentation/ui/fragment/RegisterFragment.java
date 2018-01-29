package mx.gigigo.core.presentation.ui.fragment;


import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.gigigo.core.R;
import mx.gigigo.core.data.RestApi;
import mx.gigigo.core.data.repository.UserRepository;
import mx.gigigo.core.data.repository.transform.UserEntityToUserTransform;
import mx.gigigo.core.domain.usecase.RegisterUserCase;
import mx.gigigo.core.presentation.presenter.RegisterUserPresenter;
import mx.gigigo.core.presentation.presenter.view.RegisterUserView;
import mx.gigigo.core.presentation.ui.utils.ValidationsUtils;
import mx.gigigo.core.retrofitextensions.ServiceClient;
import mx.gigigo.core.retrofitextensions.ServiceClientFactory;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends MvpBindingFragment<RegisterUserView, RegisterUserPresenter>
        implements  RegisterUserView{
    public static final int MAX_PASSWORD_LENGTH = 8;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.edit1)
    TextInputLayout textInputLayoutEmail;
    @BindView(R.id.edit2)
    TextInputLayout textInputLayoutPassword;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }
    @Override
    protected void onInitializeUIComponents() {
    }
    @Override
    protected void onInitializeMembers() {
    }

    @OnClick(R.id.bt_signup)
    public void onClickActions(){
        boolean haveErrors = false;
        if(ValidationsUtils.isEditTextEmpty(etEmail)){
            haveErrors = true;
            textInputLayoutEmail.setError(getString(R.string.message_empty_email));
        }else if(!ValidationsUtils.isValidEmailFormat(etEmail)){
            haveErrors = true;
            textInputLayoutEmail.setError(getString(R.string.message_invalid_email));
        }else{
           textInputLayoutEmail.setError("");
        }

        if(ValidationsUtils.isEditTextEmpty(etPassword)){
            haveErrors = true;
            textInputLayoutPassword.setError(getString(R.string.message_empty_password));
        }else if(!ValidationsUtils.isValidLength(etPassword, MAX_PASSWORD_LENGTH)){
            haveErrors = true;
            textInputLayoutPassword.setError(getString(R.string.message_length_password));
        }else{
            textInputLayoutPassword.setError("");
        }

        if(!haveErrors)
            presenter.registerUser(etEmail.getText().toString(), etPassword.getText().toString());
    }

    @Override
    protected RegisterUserPresenter createPresenter() {
        RestApi restApi = ServiceClientFactory.createService(ServiceClient.getDefault(), RestApi.class);
        UserRepository userRepository = new UserRepository(getContext(),
                restApi,
                new UserEntityToUserTransform()
        );
        RegisterUserCase registerUserCase = new RegisterUserCase(userRepository, Schedulers.io(), AndroidSchedulers.mainThread());
        return new RegisterUserPresenter(registerUserCase);
    }

    @Override
    public void onSuccessUserRegister(String token) {
        Toast.makeText(getContext(), getResources().getString(R.string.register_message_success), Toast.LENGTH_LONG).show();
        getActivity().finish();

    }

    @Override
    public void onEmptyResult() {

    }

    @Override
    public void showProgress(boolean active) {

    }

    @Override
    public void showError(Throwable exception) {
        Toast.makeText(getActivity(), "" + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
