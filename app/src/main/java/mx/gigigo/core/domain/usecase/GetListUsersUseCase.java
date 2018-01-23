package mx.gigigo.core.domain.usecase;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.domain.repository.ListUsersRepository;
import mx.gigigo.core.rxmvp.ObservableUseCase;
import mx.gigigo.core.rxmvp.SingleUseCase;
import mx.gigigo.core.rxmvp.UseCase;

/**
 * @author JG - December 19, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public class GetListUsersUseCase
        //extends ObservableUseCase<List<User>, GetListUsersUseCase.Params> {
        extends SingleUseCase<List<User>, GetListUsersUseCase.Params> {

    private final ListUsersRepository repository;

    public GetListUsersUseCase(ListUsersRepository repository,
                               Scheduler executorThread,
                               Scheduler uiThread) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    @Override
    protected Single<List<User>> createObservableUseCase(Params parameters) {
        return repository.getListUser(parameters.page, parameters.perPage);
    }

    public static final class Params {
        private final int page;
        private final int perPage;

        public Params(int page, int perPage) {
            this.page = page;
            this.perPage = perPage;
        }

        public static Params forPage(int page, int perPage) {
            return new Params(page, perPage);
        }
    }
}
