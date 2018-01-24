package mx.gigigo.core.domain.usecase;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import mx.gigigo.core.data.entity.UserEntity;
import mx.gigigo.core.data.repository.transform.UserEntityToUserTransform;
import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.domain.repository.ListUsersRepository;
import mx.gigigo.core.presentation.model.UserModel;
import mx.gigigo.core.presentation.model.transform.UserToUserViewModel;
import mx.gigigo.core.rxmvp.SingleUseCase;

/**
 * Created by Gigio on 24/01/18.
 */

public class RegisterUserCase  extends SingleUseCase<String, UserModel> {
    private ListUsersRepository repository;

    public RegisterUserCase(ListUsersRepository repository, Scheduler executedThread, Scheduler uiThread){
        super(executedThread, uiThread);
        this.repository = repository;
    }


    @Override
    protected Single<String> createObservableUseCase(UserModel parameters) {
        User user = new UserToUserViewModel().reverseTransform(parameters);
        UserEntity entity = new UserEntityToUserTransform().reverseTransform(user);
        return repository.registerUser(entity.getEmail(), entity.getPassword());
    }
}
