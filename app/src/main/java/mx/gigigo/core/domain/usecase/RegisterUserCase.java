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

public class RegisterUserCase  extends SingleUseCase<String, RegisterUserCase.Params> {
    private ListUsersRepository repository;

    public RegisterUserCase(ListUsersRepository repository, Scheduler executedThread, Scheduler uiThread){
        super(executedThread, uiThread);
        this.repository = repository;
    }


    @Override
    protected Single<String> createObservableUseCase(Params parameters) {
        return repository.registerUser(parameters.getEmail(), parameters.getPassword());
    }


    public static class Params{
        private String email;
        private String password;

        public Params(String email, String password){
            this.email = email;
            this.password = password;
        }
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
