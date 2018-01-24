package mx.gigigo.core.presentation.presenter;

import io.reactivex.Observable;
import io.reactivex.Single;
import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.domain.usecase.GetDetailUserUseCase;
import mx.gigigo.core.domain.usecase.UpdateUserCase;
import mx.gigigo.core.presentation.presenter.view.DetailUserView;
import mx.gigigo.core.presentation.viewmodel.UserViewModel;
import mx.gigigo.core.presentation.viewmodel.transform.UserToUserViewModel;
import mx.gigigo.core.rxmvp.BasePresenter;
import mx.gigigo.core.rxmvp.SingleCaseObserver;
import mx.gigigo.core.rxmvp.UseCase;

/**
 * Created by Gigio on 17/01/18.
 */

public class DetailUserPresenter extends BasePresenter<DetailUserView> {
    //Use case
    private GetDetailUserUseCase userUseCaseDetail;
    private UserToUserViewModel userToUserViewModel;
    private UpdateUserCase updateUserCase;


    public DetailUserPresenter(GetDetailUserUseCase userUseCase, UserToUserViewModel userToUserViewModel,
                               UpdateUserCase updateUserCase){
        this.userUseCaseDetail = userUseCase;
        this.userToUserViewModel = userToUserViewModel;
        this.updateUserCase = updateUserCase;
    }


    public void getUserDetail(int user_id){
        userUseCaseDetail.execute(new DetailUserObserver(), user_id);
    }

    public void getUserUpdate(UserViewModel user){
        updateUserCase.execute(new UserUpdateObserver(), user);
    }

    private final class UserUpdateObserver extends SingleCaseObserver<String>{
        @Override
        public void onSuccess(String s) {
            if(!isViewAttached()) return;
            getView().showProgress(false);
            getView().onSuccessUserUpdate();


        }

        @Override
        public void onError(Throwable e) {
            if(!isViewAttached()) return;
            getView().showProgress(false);
            getView().showError(e);
        }
    };



    private final class DetailUserObserver extends SingleCaseObserver<User> {
        @Override
        public void onError(Throwable e) {

            if(!isViewAttached()) return;
            getView().showProgress(false);
            getView().showError(e);
        }

        @Override
        public void onSuccess(User user) {
            if(!isViewAttached()) return;
            getView().showProgress(false);
            if(user!= null)
                getView().onSuccessUserDetail(userToUserViewModel.transform(user));
            else
                getView().onEmptyResult();
        }
    }


}
