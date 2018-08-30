package mx.gigigo.core.retrofitextensions;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by isarael.cortes on 8/28/18.
 */

public class DefaultNetworkRequestInterceptor extends NetworkRequestInterceptor {

    public DefaultNetworkRequestInterceptor(Network network) {
        super(network);
    }

    @Override
    protected Response interceptResponse(Chain chain) throws IOException {
        return chain.proceed(chain.request());
    }
}