package mx.gigigo.core.domain.repository.error;

import com.google.gson.annotations.SerializedName;

import mx.gigigo.core.rxextensions.ResponseError;

/**
 * Created by vt on 25/01/18.
 */
public class ServerError
        implements ResponseError {

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
