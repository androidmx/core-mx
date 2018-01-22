package mx.gigigo.core.domain.usecase;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.domain.repository.ListUsersRepository;
import mx.gigigo.core.rxmvp.UseCase;

/**
 * Created by Gigio on 18/01/18.
 */

public class GetDetailUserUseCase extends UseCase<User, Integer> {
    private ListUsersRepository repository;

    public GetDetailUserUseCase(ListUsersRepository repository,
                                Scheduler excecutedThread,
                                Scheduler uiThread){
        super(excecutedThread,  uiThread);
        this.repository = repository;
    }


    @Override
    protected Observable<User> createObservableUseCase(Integer parameters) {
        return repository.getUserDetail(parameters);
    }
}
