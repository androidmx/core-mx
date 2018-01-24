package mx.gigigo.core.domain.repository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import mx.gigigo.core.data.entity.UserEntity;
import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.presentation.viewmodel.UserViewModel;
import mx.gigigo.core.rxmvp.Repository;

/**
 * @author JG - January 04, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ListUsersRepository
        extends Repository {

    Single<User> getUserDetail(int user_id);

    Single<String> updateUser(UserEntity userEntity);

    //Observable<List<User>> getListUser(int page, int perPage);
    Single<List<User>> getListUser(int page, int perPage);

}
