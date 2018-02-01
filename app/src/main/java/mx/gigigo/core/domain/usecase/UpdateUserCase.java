package mx.gigigo.core.domain.usecase;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import mx.gigigo.core.data.entity.UserEntity;
import mx.gigigo.core.data.repository.transform.UserEntityToUserTransform;
import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.domain.repository.ListUsersRepository;
import mx.gigigo.core.presentation.model.UserModel;
import mx.gigigo.core.presentation.model.transform.UserToUserViewModel;
import mx.gigigo.core.rxmvp.SingleUseCase;

/**
 * @autor Gigio on 24/01/18.
 * @version 0.0.1
 * @since 0.0.1
 */

public class UpdateUserCase extends SingleUseCase<User, UserModel> {
    private ListUsersRepository repository;
    public UpdateUserCase(ListUsersRepository repository,
                          Scheduler excecutedThread,
                          Scheduler uiThread){
        super(excecutedThread, uiThread);
        this.repository =repository;
    }

    @Override
    protected Single<User> createObservableUseCase(UserModel parameters) {
        User user = new UserToUserViewModel().reverseTransform(parameters);
        UserEntity userEntity = new UserEntityToUserTransform().reverseTransform(user);
        return repository.updateUser(userEntity);
    }
}
