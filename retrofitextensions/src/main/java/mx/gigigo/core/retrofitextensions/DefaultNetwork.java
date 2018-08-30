package mx.gigigo.core.retrofitextensions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by isarael.cortes on 8/28/18.
 */

public class DefaultNetwork extends BroadcastReceiver
        implements Network {

    private static final String TAG = DefaultNetwork.class.getSimpleName();

    public static final int TYPE_NONE = -1;

    private ConnectivityManager connectivityManager;

    public DefaultNetwork(Context context) {
        connectivityManager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public void onReceive(Context context, Intent intent) { }

    @Override
    public boolean isConnected() {
        NetworkInfo activeNetwork = connectivityManager != null
                ? connectivityManager.getActiveNetworkInfo()
                : null;

        return null != activeNetwork && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public int connectivityType() {
        NetworkInfo activeNetwork = connectivityManager != null
                ? connectivityManager.getActiveNetworkInfo()
                : null;

        return null != activeNetwork ?
                activeNetwork.getType() :
                TYPE_NONE;
    }

    @Override
    public boolean isReachable(String host, int timeout) {
        if(!isConnected()) return false;

        boolean reachable;

        try {
            reachable = InetAddress.getByName(host).isReachable(timeout);
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            reachable = false;
        }

        return reachable;
    }
}