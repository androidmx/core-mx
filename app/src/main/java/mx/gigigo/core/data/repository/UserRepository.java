package mx.gigigo.core.data.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import mx.gigigo.core.data.RestApi;
import mx.gigigo.core.data.entity.ListUsersResponse;
import mx.gigigo.core.data.entity.UserEntity;
import mx.gigigo.core.data.entity.base.LoginResponse;
import mx.gigigo.core.data.entity.base.UserResponse;
import mx.gigigo.core.data.repository.transform.UserEntityToUserTransform;
import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.domain.repository.ListUsersRepository;
import mx.gigigo.core.domain.repository.error.HttpErrorHandler;
import mx.gigigo.core.domain.repository.error.ServerError;
import mx.gigigo.core.domain.repository.error.SingleErrorHandler;

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
                if (null != listUsersResponse && listUsersResponse.hasData()) {
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
    public Single<String> registerUser(String email, String password) {
        Single<LoginResponse> response = api.registerUser(email, password);
        return response.map(new Function<LoginResponse, String>() {
            @Override
            public String apply(LoginResponse loginResponse) throws Exception {
                return loginResponse.getToken();
            }
        }).onErrorResumeNext(
                new SingleErrorHandler<String, ServerError>(new HttpErrorHandler(), ServerError.class));
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
    public Single<User> updateUser(UserEntity userEntity) {
        Single<UserResponse> response = api.updateInfoUser(userEntity.getId(), userEntity);
        return response.map(new Function<UserResponse, User>() {
            @Override
            public User apply(UserResponse response) throws Exception {
                return userMapper.transform(response.getUser());
            }

        }).onErrorReturn(new Function<Throwable, User>() {
            @Override
            public User apply(Throwable throwable) throws Exception {
                return null;
            }
        });
    }

    @Override
    public Single<String> loginUser(String email, String password) {
        Single<LoginResponse> response = api.loginUser(email, password);
        return response.map(new Function<LoginResponse, String>() {
            @Override
            public String apply(LoginResponse loginResponse) throws Exception {
                return loginResponse.getToken();
            }
        }).onErrorResumeNext(new SingleErrorHandler<String, ServerError>(
                new HttpErrorHandler(), ServerError.class));
    }


}
