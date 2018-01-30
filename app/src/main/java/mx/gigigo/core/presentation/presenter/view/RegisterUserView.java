package mx.gigigo.core.presentation.presenter.view;

import mx.gigigo.core.rxmvp.View;

/**
 * Created by Gigio on 24/01/18.
 */

public interface RegisterUserView extends View {
    void onSuccessUserRegister(String token);
    void onSuccessUserLogin(String token);
    void onEmptyResult();
    void showProgress(boolean active);
    void showError(Throwable exception);
}
