package mx.gigigo.core.data;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import mx.gigigo.core.data.entity.ListUsersResponse;
import mx.gigigo.core.data.entity.UserEntity;
import mx.gigigo.core.data.entity.base.UserResponse;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author JG - January 04, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public interface RestApi {
    @GET("/api/users")
        //Observable<ListUsersResponse> getListUsers(@QueryMap(encoded = true) Map<String, String> options);
    Single<ListUsersResponse> getListUsers(@QueryMap(encoded = true) Map<String, String> options);


    //get detail user
    @GET("/api/users/{id_user}")
    Observable<UserResponse> getDetailUser(@Path("id_user") int id_user);

    //update user
    @PUT("/api/users/{id_user}")
    Observable<UserResponse> updateInfoUser(@Query("user") UserEntity userEntity);


}
