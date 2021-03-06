package mx.gigigo.core.domain.repository;

import java.util.List;

import io.reactivex.Single;
import mx.gigigo.core.data.entity.UserEntity;
import mx.gigigo.core.domain.model.User;

/**
 * @author JG - January 04, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ListUsersRepository {

    Single<User> getUserDetail(int user_id);

    Single<User> updateUser(UserEntity userEntity);

    //Observable<List<User>> getListUser(int page, int perPage);
    Single<List<User>> getListUser(int page, int perPage);

    Single<String> registerUser(String email, String password);

    Single<String> loginUser(String email, String password);

}
