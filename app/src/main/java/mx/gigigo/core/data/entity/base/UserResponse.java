package mx.gigigo.core.data.entity.base;

import android.hardware.camera2.params.StreamConfigurationMap;

import com.google.gson.annotations.SerializedName;

import mx.gigigo.core.data.entity.UserEntity;

/**
 * Created by Gigio on 18/01/18.
 */

public class UserResponse {
    @SerializedName("data")
    private UserEntity user;


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


}
