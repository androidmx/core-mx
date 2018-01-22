package mx.gigigo.core.data.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import mx.gigigo.core.data.RestApi;
import mx.gigigo.core.data.entity.ListUsersResponse;
import mx.gigigo.core.data.entity.base.UserResponse;
import mx.gigigo.core.data.repository.mapper.UserEntityToUserTransform;
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
    public Observable<List<User>> getListUser(int page, int perPage) {
        Map<String, String> data = new HashMap<>();
        data.put("page", String.valueOf(page));
        data.put("per_page", String.valueOf(perPage));

        final Observable<ListUsersResponse> response = api.getListUsers(data);

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


//        return Observable.create(new ObservableOnSubscribe<List<User>>() {
//            @Override
//            public void subscribe(ObservableEmitter<List<User>> emitter) throws Exception {
//                ListUsersResponse listUsersResponse = response.blockingSingle();
//
//                if(null != listUsersResponse && listUsersResponse.hasData()) {
//                    List<User> values = userMapper.transform(listUsersResponse.getData());
//
//                    if (null != values) {
//                        emitter.onNext(values);
//                        emitter.onComplete();
//                    }
//                } else {
//                    emitter.onComplete();
//                }
//            }
//        });



/*
        return response.transform(new Function<ListUsersResponse, List<User>>() {
            @Override
            public List<User> apply(ListUsersResponse listUsersResponse) throws Exception {
                if(null != listUsersResponse && listUsersResponse.hasData()) {
                    return userMapper.transform(listUsersResponse.getData());
                } else {
                    return null;
                }
            }
        });
        */
    }

    public Observable<ListUsersResponse> getListUser2(int page, int perPage) {
        Map<String, String> data = new HashMap<>();
        data.put("page", String.valueOf(page));
        data.put("per_page", String.valueOf(perPage));

        Observable<ListUsersResponse> response = api.getListUsers(data);


        return response;
    }

    @Override
    public Observable<User> getUserDetail(int user_id) {
        Observable<UserResponse> response = api.getDetailUser(user_id);
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


}
