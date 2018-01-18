package mx.gigigo.core.presentation.presenter;

import android.support.annotation.NonNull;


import java.util.List;

import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.domain.usecase.GetListUsersUseCase;
import mx.gigigo.core.presentation.model.mapper.UserToUserViewModel;
import mx.gigigo.core.presentation.presenter.view.ListUsersView;
import mx.gigigo.core.rxmvp.BasePresenter;
import mx.gigigo.core.rxmvp.UseCaseObserver;

/**
 * @author JG - December 13, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public class ListUsersPresenter
        extends BasePresenter<ListUsersView> {

    private final GetListUsersUseCase getListUsersUseCase;
    private final UserToUserViewModel userViewModelMapper;

    public ListUsersPresenter(@NonNull GetListUsersUseCase getListUsersUseCase,
                              @NonNull UserToUserViewModel userViewModelMapper) {
        this.getListUsersUseCase = getListUsersUseCase;
        this.userViewModelMapper = userViewModelMapper;
    }

    public void getUsers(int page, int perPage) {
        GetListUsersUseCase.Params params = GetListUsersUseCase.Params.forPage(page, perPage);
        getListUsersUseCase.execute(new UserListObserver(), params);
    }


    @Override
    public void destroy() {
        super.destroy();

        getListUsersUseCase.dispose();
    }

    private final class UserListObserver
            extends UseCaseObserver<List<User>> {

        @Override
        public void onComplete() {
            if(!isViewAttached()) return;

            getView().showProgress(false);
        }

        @Override
        public void onError(Throwable e) {
            if(!isViewAttached()) return;

            getView().showProgress(false);
            getView().showError(e);
        }

        @Override
        public void onNext(List<User> users) {
            super.onNext(users);

            if(!isViewAttached()) return;

            if(null != users && !users.isEmpty()) {
                getView().onFetchPeopleSuccess(userViewModelMapper.transform(users));
            } else {
                getView().onEmptyResult();
            }
        }
    }
}
