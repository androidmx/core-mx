package mx.gigigo.core.domain.usecase;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import mx.gigigo.core.data.entity.UserEntity;
import mx.gigigo.core.data.repository.transform.UserEntityToUserTransform;
import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.domain.repository.ListUsersRepository;
import mx.gigigo.core.presentation.viewmodel.UserViewModel;
import mx.gigigo.core.presentation.viewmodel.transform.UserToUserViewModel;
import mx.gigigo.core.rxmvp.SingleUseCase;

/**
 * Created by Gigio on 24/01/18.
 */

public class UpdateUserCase extends SingleUseCase<String, UserViewModel> {
    private ListUsersRepository repository;
    public UpdateUserCase(ListUsersRepository repository,
                          Scheduler excecutedThread,
                          Scheduler uiThread){
        super(excecutedThread, uiThread);
        this.repository =repository;
    }

    @Override
    protected Single<String> createObservableUseCase(UserViewModel parameters) {
        User user = new UserToUserViewModel().reverseTransform(parameters);
        UserEntity userEntity = new UserEntityToUserTransform().reverseTransform(user);
        return repository.updateUser(userEntity);
    }
}
