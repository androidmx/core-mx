package mx.gigigo.core.domain.repository.error;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import mx.gigigo.core.rxextensions.HttpErrorHandling;
import mx.gigigo.core.rxextensions.ResponseError;
import mx.gigigo.core.rxextensions.ResponseState;
import mx.gigigo.core.rxextensions.SourceErrorFunction;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author JG - February 15, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class SingleErrorHandler<T, E extends ResponseError>
        extends SourceErrorFunction<E, SingleSource<? extends T>> {

    public SingleErrorHandler(HttpErrorHandling httpErrorHandling, Class<E> errorClass) {
        super(httpErrorHandling, errorClass);
    }

    @Override
    public SingleSource<? extends T> apply(Throwable throwable) throws Exception {
        if(throwable instanceof HttpException){
            HttpException httpException = (HttpException) throwable;
            if(httpException.response() != null){
                Response response = httpException.response();
                if(response.code() == HttpURLConnection.HTTP_UNAUTHORIZED){
                    return Single.error(new UnauthorizedException());
                }

                if(null != response.errorBody() ) {
                    String errorBody = response.errorBody().string();
                    return Single.error(getResponseState(errorBody, response.code()));
                }
            }
        }

        return Single.error(throwable);
    }

    @Override
    protected ResponseState getResponseState(String errorBody, int code) throws IOException {
        Gson gson = new Gson();
        String errorMessage = null;

        ResponseError responseError = gson.fromJson(errorBody, errorClass);

        if(responseError != null && responseError.hasErrorMessage()){
            errorMessage = responseError.getError();
        }

        if(null == errorMessage) {
            errorMessage = httpErrorHandling.getErrorByHttpCode(code);

        }

        return  new ResponseState(errorMessage, code);
    }
}
