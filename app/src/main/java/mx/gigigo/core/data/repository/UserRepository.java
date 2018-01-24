package mx.gigigo.core.data.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import mx.gigigo.core.data.RestApi;
import mx.gigigo.core.data.entity.ListUsersResponse;
import mx.gigigo.core.data.entity.UserEntity;
import mx.gigigo.core.data.entity.base.UpdateResponse;
import mx.gigigo.core.data.entity.base.UserResponse;

import mx.gigigo.core.data.repository.transform.UserEntityToUserTransform;

import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.domain.repository.ListUsersRepository;

/**
 * @author JG - December 19, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public class UserRepository
        implements ListUsersRepository {

    private final RestApi api;
    private final UserEntityToUserTransform userMapper;

    public UserRepository(RestApi api,
                          UserEntityToUserTransform userMapper) {
        this.api = api;
        this.userMapper = userMapper;
    }

    @Override
    public Single<List<User>> getListUser(int page, int perPage) {
        Map<String, String> data = new HashMap<>();
        data.put("page", String.valueOf(page));
        data.put("per_page", String.valueOf(perPage));

        final Single<ListUsersResponse> response = api.getListUsers(data);

        return response.map(new Function<ListUsersResponse, List<User>>() {
            @Override
            public List<User> apply(ListUsersResponse listUsersResponse) throws Exception {
                if(null != listUsersResponse && listUsersResponse.hasData()) {
                    return userMapper.transform(listUsersResponse.getData());
                } else {
                    return new ArrayList<>();
                }
            }
        }).onErrorReturn(new Function<Throwable, List<User>>() {
            @Override
            public List<User> apply(Throwable throwable) throws Exception {
                return null;
            }
        });
    }

    @Override
    public Single<User> getUserDetail(int user_id) {
        Single<UserResponse> response = api.getDetailUser(user_id);
        return response.map(new Function<UserResponse, User>() {
            @Override
            public User apply(UserResponse userEntity) throws Exception {
                return userMapper.transform(userEntity.getUser());
            }
        }).onErrorReturn(new Function<Throwable, User>() {
            @Override
            public User apply(Throwable throwable) throws Exception {
                return null;
            }
        });
    }

    @Override
    public Single<String> updateUser(UserEntity userEntity) {
        Single<UpdateResponse> response = api.updateInfoUser(userEntity.getId(), userEntity);
        return response.map(new Function<UpdateResponse, String>() {
            @Override
            public String apply(UpdateResponse response) throws Exception {
                Log.i("" ,  response.getUpdateAt());
                return response.getUpdateAt();
            }
        }).onErrorReturn(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) throws Exception {
                return null;
            }
        });
    }


}
