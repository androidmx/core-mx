package mx.gigigo.core.domain.repository.error;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import mx.gigigo.core.rxextensions.HttpErrorHandling;
import mx.gigigo.core.rxextensions.ResponseError;
import mx.gigigo.core.rxextensions.ResponseState;
import mx.gigigo.core.rxextensions.SourceErrorFunction;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author JG - February 16, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class CompletableErrorHandler<E extends ResponseError>
        extends SourceErrorFunction<E, CompletableSource> {

    public CompletableErrorHandler(HttpErrorHandling httpErrorHandling, Class<E> errorClass) {
        super(httpErrorHandling, errorClass);
    }

    @Override
    public CompletableSource apply(Throwable throwable) throws Exception {
        if(throwable instanceof HttpException){
            HttpException httpException = (HttpException) throwable;
            if(httpException.response() != null){
                Response response = httpException.response();
                if(response.code() == HttpURLConnection.HTTP_UNAUTHORIZED){
                    return Completable.error(new UnauthorizedException());
                }

                if(null != response.errorBody() ) {
                    String errorBody = response.errorBody().string();
                    return Completable.error(getResponseState(errorBody, response.code()));
                }
            }
        }

        return Completable.error(throwable);
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
