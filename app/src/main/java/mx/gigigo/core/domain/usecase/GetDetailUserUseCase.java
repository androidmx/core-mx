package mx.gigigo.core.domain.usecase;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.domain.repository.ListUsersRepository;
import mx.gigigo.core.rxmvp.SingleUseCase;
import mx.gigigo.core.rxmvp.UseCase;

/**
 * @autor Gigio on 18/01/18.
 * @version 0.0.1
 * @since 0.0.1
 */

public class GetDetailUserUseCase extends SingleUseCase<User, Integer> {
    private ListUsersRepository repository;

    public GetDetailUserUseCase(ListUsersRepository repository,
                                Scheduler excecutedThread,
                                Scheduler uiThread){
        super(excecutedThread,  uiThread);
        this.repository = repository;
    }


    @Override
    protected Single<User> createObservableUseCase(Integer parameters) {
        return repository.getUserDetail(parameters);
    }
}
