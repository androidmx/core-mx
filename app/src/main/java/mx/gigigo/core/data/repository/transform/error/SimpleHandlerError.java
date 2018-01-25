package mx.gigigo.core.data.repository.transform.error;

import com.google.gson.annotations.SerializedName;

import mx.gigigo.core.rxmvp.ResponseError;

/**
 * Created by Gigio on 25/01/18.
 */

public class SimpleHandlerError implements ResponseError{

    @SerializedName("error")
    private String error;

    @Override
    public String getError() {
        return null;
    }

    @Override
    public boolean hasErrorMessage() {
        return false;
    }
}
