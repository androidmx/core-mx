package mx.gigigo.core.retrofitextensions;

/**
 * Created by isarael.cortes on 8/28/18.
 */

public interface Network {
    boolean isConnected();
    int connectivityType();
    boolean isReachable(String host, int timeout);
}
