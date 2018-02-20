package mx.gigigo.core.domain.usecase;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import mx.gigigo.core.domain.repository.ListUsersRepository;
import mx.gigigo.core.rxextensions.SingleUseCase;

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
