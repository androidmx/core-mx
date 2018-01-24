package mx.gigigo.core.data.entity.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gigio on 24/01/18.
 */

public class UpdateResponse {
    @SerializedName("updatedAt")
    @Expose
    private String updateAt;

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
