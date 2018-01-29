package mx.gigigo.core.data.repository.error;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import mx.gigigo.core.presentation.RootApp;
import mx.gigigo.core.rxmvp.ErrorHandlerFunction;
import mx.gigigo.core.rxmvp.ResponseError;
import mx.gigigo.core.rxmvp.ResponseState;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by Gigio on 25/01/18.
 */

public class RxErrorHandlerFunction<T, E extends ResponseError>
        extends  ErrorHandlerFunction<Response>
        implements Function<Throwable, SingleSource<? extends T>> {

    private final Class<E> errorClass;

    public RxErrorHandlerFunction(Class<E> errorClass){
        this.errorClass = errorClass;
    }
    @Override
    public SingleSource<? extends T> apply(Throwable throwable) throws Exception {
        if(throwable instanceof HttpException){
            HttpException httpException = (HttpException) throwable;
            if(httpException != null && httpException.response() != null){
                Response response = httpException.response();
                if(response.code() == HttpURLConnection.HTTP_UNAUTHORIZED){
                    return Single.error(new UnauthorizedException());
                }
                return Single.error(getResponseState(response));
            }
        }
        return Single.error(throwable);
    }

    @Override
    public ResponseState getResponseState(Response response) throws IOException {
        Gson gson = new Gson();
        String  errorMessage;

        String jsonError = response.errorBody().string();
        ResponseError responseError = gson.fromJson(jsonError, errorClass);

        if(responseError != null && responseError.hasErrorMessage()){
            errorMessage = responseError.getError();
        }else{
            errorMessage = new HttpErrorHandler(RootApp.getAppContext()).getErrorByHttpCode(response.code()).toString();
        }

        return  new ResponseState(errorMessage, response.code() );
    }
}
