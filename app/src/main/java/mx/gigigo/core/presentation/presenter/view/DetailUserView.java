package mx.gigigo.core.presentation.presenter.view;


import mx.gigigo.core.presentation.model.UserModel;
import mx.gigigo.core.rxmvp.View;

/**
 * Created by Gigio on 17/01/18.
 */

public interface DetailUserView extends View {
    void onSuccessUserDetail(UserModel userViewModel);
    void onSuccessUserUpdate();
    void onEmptyResult();
    void showProgress(boolean active);
    void showError(Throwable exception);
}
