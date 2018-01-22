package mx.gigigo.core.presentation.presenter.view;


import mx.gigigo.core.presentation.model.UserViewModel;
import mx.gigigo.core.rxmvp.View;

/**
 * Created by Gigio on 17/01/18.
 */

public interface DetailUserView extends View {
    void onSuccessUserDetail(UserViewModel userViewModel);
    void onSuccessUserUpdate(UserViewModel userViewModel);
    void onEmptyResult();
    void showProgress(boolean active);
    void showError(Throwable exception);
}
