package mx.gigigo.core.retrofitextensions;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author JG - December 28, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class NetworkRequestInterceptor
        implements Interceptor {

    private final Network network;

    public NetworkRequestInterceptor(Network network) {
        this.network = network;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(null != network && network.isConnected()) {
            return interceptResponse(chain);
        } else {
            throw new NetworkException();
        }
    }

    public static class NetworkException
            extends IOException {
        public NetworkException() {
            super("Parece que su conexión a Internet está desactivada." +
                    "\n\nPor favor, enciéndala y vuelva a intentarlo.");
        }
    }

    protected abstract Response interceptResponse(Chain chain) throws IOException;
}
