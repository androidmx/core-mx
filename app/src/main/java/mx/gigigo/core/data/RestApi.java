package mx.gigigo.core.data;

import java.util.Map;

import io.reactivex.Single;
import mx.gigigo.core.data.entity.ListUsersResponse;
import mx.gigigo.core.data.entity.UserEntity;
import mx.gigigo.core.data.entity.base.LoginResponse;
import mx.gigigo.core.data.entity.base.UserResponse;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Single<UserResponse> getDetailUser(@Path("id_user") int id_user);

    //update user
    @PUT("/api/users/{id_user}")
    Single<UserResponse> updateInfoUser(@Path("id_user") int idUser,
                                         @Query("user") UserEntity userEntity);
    //RegisterUser
    @POST("/api/register")
    Single<String> registerUser(@Query("user") UserEntity userEntity);

    //LoginUser
    @POST("/api/login")
    Single<String> loginUser(@Query("user") UserEntity userEntity);

}
