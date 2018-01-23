package mx.gigigo.core.data.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import mx.gigigo.core.data.RestApi;
import mx.gigigo.core.data.entity.ListUsersResponse;
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
}
