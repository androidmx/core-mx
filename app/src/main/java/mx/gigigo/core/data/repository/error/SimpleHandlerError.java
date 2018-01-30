package mx.gigigo.core.data.repository.error;

import com.google.gson.annotations.SerializedName;

import mx.gigigo.core.rxextensions.ResponseError;


/**
 * Created by Gigio on 25/01/18.
 */

public class SimpleHandlerError implements ResponseError {

    @SerializedName("error")
    private String error;


    @Override
    public String getError() {
        return error;
    }

    @Override
    public boolean hasErrorMessage() {
        return error != null && !error.isEmpty();
    }
}
