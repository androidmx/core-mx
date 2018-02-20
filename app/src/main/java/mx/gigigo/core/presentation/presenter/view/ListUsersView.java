package mx.gigigo.core.presentation.presenter.view;

import java.util.List;

import mx.gigigo.core.presentation.model.UserModel;
import mx.gigigo.core.mvp.View;

/**
 * @author JG - December 13, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ListUsersView
        extends View {
    void onFetchPeopleSuccess(List<UserModel> userModels);
    void onEmptyResult();
    void showProgress(boolean active);
    void showError(Throwable exception);
}
