package mx.gigigo.core.presentation.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.gigigo.core.R;
import mx.gigigo.core.data.RestApi;
import mx.gigigo.core.data.repository.UserRepository;
import mx.gigigo.core.data.repository.transform.UserEntityToUserTransform;
import mx.gigigo.core.domain.usecase.RegisterUserCase;
import mx.gigigo.core.presentation.model.transform.UserToUserViewModel;
import mx.gigigo.core.presentation.presenter.RegisterUserPresenter;
import mx.gigigo.core.presentation.presenter.view.RegisterUserView;
import mx.gigigo.core.retrofitextensions.ServiceClient;
import mx.gigigo.core.retrofitextensions.ServiceClientFactory;
import mx.gigigo.core.rxmvp.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends MvpBindingFragment<RegisterUserView, RegisterUserPresenter> implements  RegisterUserView{


    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();;
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onInitializeUIComponents() {

    }

    @Override
    protected void onInitializeMembers() {

    }

    @Override
    protected void onBindView(View root) {

    }

    @Override
    protected void onUnbindView() {

    }


    @Override
    protected RegisterUserPresenter createPresenter() {
        RestApi restApi = ServiceClientFactory.createService(ServiceClient.getDefault(), RestApi.class);
        UserRepository userRepository = new UserRepository(restApi, new UserEntityToUserTransform());
        RegisterUserCase registerUserCase = new RegisterUserCase(userRepository, Schedulers.io(), AndroidSchedulers.mainThread());

        return new RegisterUserPresenter(registerUserCase, new UserToUserViewModel());
    }

    @Override
    public void onSuccessUserRegister(String token) {

    }

    @Override
    public void onEmptyResult() {

    }

    @Override
    public void showProgress(boolean active) {

    }

    @Override
    public void showError(Throwable exception) {

    }
}
