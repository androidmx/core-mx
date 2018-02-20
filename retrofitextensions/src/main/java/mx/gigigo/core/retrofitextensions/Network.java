package mx.gigigo.core.retrofitextensions;

/**
 * @author JG - December 28, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Network {
    boolean isConnected();
    int connectivityType();
    boolean isReachable(String host, int timeout);
}
