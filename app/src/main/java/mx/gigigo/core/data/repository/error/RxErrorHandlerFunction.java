package mx.gigigo.core.data.repository.error;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import mx.gigigo.core.rxextensions.ErrorHandlerFunction;
import mx.gigigo.core.rxextensions.ResponseError;
import mx.gigigo.core.rxextensions.ResponseState;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author VT - January 25, 2018.
 * @version 0.0.1
 * @since 0.0.1
 */
public class RxErrorHandlerFunction<T, E extends ResponseError>
        extends ErrorHandlerFunction<Response>
        implements Function<Throwable, SingleSource<? extends T>> {

    private final Class<E> errorClass;
    private final Context context;

    public RxErrorHandlerFunction(Context context, Class<E> errorClass){
        this.context = context;
        this.errorClass = errorClass;
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
                return Single.error(getResponseState(response));
            }
        }
        return Single.error(throwable);
    }

    @Override
    public ResponseState getResponseState(Response response) throws IOException {
        if(null == response)
            throw new NullPointerException("Response must not be null");

        Gson gson = new Gson();
        String errorMessage = null;

        /*if(null != response.errorBody() *//*&& null != response.errorBody().string()*//*) {
            String jsonError = response.errorBody().string();
            ResponseError responseError = gson.fromJson(jsonError, errorClass);

            if(responseError != null && responseError.hasErrorMessage()){
                errorMessage = responseError.getError();
            }
        }*/


        if(null == errorMessage) {
            errorMessage = new HttpErrorHandler(context).getErrorByHttpCode(response.code());

        }

        return  new ResponseState(errorMessage, response.code());
    }



}
