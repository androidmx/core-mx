package mx.gigigo.core.presentation.presenter;

import io.reactivex.Observable;
import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.domain.usecase.GetDetailUserUseCase;
import mx.gigigo.core.presentation.model.mapper.UserToUserViewModel;
import mx.gigigo.core.presentation.presenter.view.DetailUserView;
import mx.gigigo.core.rxmvp.BasePresenter;
import mx.gigigo.core.rxmvp.UseCase;
import mx.gigigo.core.rxmvp.UseCaseObserver;

/**
 * Created by Gigio on 17/01/18.
 */

public class DetailUserPresenter extends BasePresenter<DetailUserView> {
    //Use case
    private GetDetailUserUseCase userUseCase;
    private UserToUserViewModel userToUserViewModel;

    public DetailUserPresenter(GetDetailUserUseCase userUseCase, UserToUserViewModel userToUserViewModel){
        this.userUseCase = userUseCase;
        this.userToUserViewModel = userToUserViewModel;
    }


    public void getUserDetail(int user_id){
        userUseCase.execute(new DetailUserObserver(), user_id);
    }


    private final class DetailUserObserver extends UseCaseObserver<User>{
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
        public void onNext(User user) {
            if(!isViewAttached()) return;
            getView().showProgress(false);
            if(user!= null)
                getView().onSuccessUserDetail(userToUserViewModel.transform(user));
            else
                getView().onEmptyResult();
        }
    }


}
