package mx.gigigo.core.presentation.presenter;

import mx.gigigo.core.domain.usecase.RegisterUserCase;
import mx.gigigo.core.presentation.model.transform.UserToUserViewModel;
import mx.gigigo.core.presentation.presenter.view.RegisterUserView;
import mx.gigigo.core.rxmvp.BasePresenter;

/**
 * Created by Gigio on 24/01/18.
 */

public class RegisterUserPresenter extends BasePresenter<RegisterUserView> {
    private RegisterUserCase registerUserCase;
    private UserToUserViewModel userToUserViewModel;

    public RegisterUserPresenter(RegisterUserCase registerUserCase,
                                 UserToUserViewModel userToUserViewModel){
        this.registerUserCase = registerUserCase;
        this.userToUserViewModel = userToUserViewModel;
    }

    public void registerUser(){

    }


}
