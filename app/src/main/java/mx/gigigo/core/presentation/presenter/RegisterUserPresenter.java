package mx.gigigo.core.presentation.presenter;

import mx.gigigo.core.domain.usecase.LoginUserCase;
import mx.gigigo.core.domain.usecase.RegisterUserCase;
import mx.gigigo.core.presentation.presenter.view.RegisterUserView;
import mx.gigigo.core.rxmvp.BasePresenter;
import mx.gigigo.core.rxmvp.SingleCaseObserver;

/**
 * Created by Gigio on 24/01/18.
 */

public class RegisterUserPresenter extends BasePresenter<RegisterUserView> {
    private RegisterUserCase registerUserCase;
    private LoginUserCase loginUserCase;

    public RegisterUserPresenter(RegisterUserCase registerUserCase){
        this.registerUserCase = registerUserCase;
    }

    public RegisterUserPresenter(RegisterUserCase registerUserCase, LoginUserCase loginUserCase){
        this.registerUserCase = registerUserCase;
        this.loginUserCase = loginUserCase;
    }

    public void registerUser(String email, String password){
        RegisterUserCase.Params params = new RegisterUserCase.Params("", password);
        registerUserCase.execute(new RegisterObserver(), params);
    }

    public void loginUser(String email, String password){
        LoginUserCase.Params params = new LoginUserCase.Params(email, password);
        loginUserCase.execute(new RegisterObserver(), params);
    }


    private final class RegisterObserver extends SingleCaseObserver<String>{
        @Override
        public void onSuccess(String s) {
            if(!isViewAttached()) return;
            getView().showProgress(false);
            if(s.isEmpty()){
                getView().onEmptyResult();
            }else {
                getView().onSuccessUserRegister(s);
            }
        }

        @Override
        public void onError(Throwable e) {
            if(!isViewAttached()) return;
            getView().showProgress(false);
            getView().showError(e);
        }
    }

    private final class LoginObserver extends SingleCaseObserver<String>{

        @Override
        public void onSuccess(String message) {
            if(!isViewAttached()) return;
            getView().showProgress(false);
            if(message.isEmpty()){
                getView().onEmptyResult();
            }else{
                getView().onSuccessUserLogin(message);
            }
        }

        @Override
        public void onError(Throwable e) {
            if(!isViewAttached()) return;
            getView().showProgress(false);
            getView().showError(e);
        }
    }



}
