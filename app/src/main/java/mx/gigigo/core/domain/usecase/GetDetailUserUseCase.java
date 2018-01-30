package mx.gigigo.core.domain.usecase;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.domain.repository.ListUsersRepository;
import mx.gigigo.core.rxextensions.SingleUseCase;

/**
 * Created by Gigio on 18/01/18.
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
