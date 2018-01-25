package mx.gigigo.core.data.repository.transform.error;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
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


    @Override
    public SingleSource<? extends T> apply(Throwable throwable) throws Exception {
        return null;
    }

    @Override
    public ResponseState getResponseState(Response response) throws IOException {
       
        return null;
    }
}
