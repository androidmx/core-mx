package mx.gigigo.core.presentation;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import mx.gigigo.core.BuildConfig;
import mx.gigigo.core.retrofitextensions.DefaultNetwork;
import mx.gigigo.core.retrofitextensions.Network;
import mx.gigigo.core.retrofitextensions.NetworkRequestInterceptor;
import mx.gigigo.core.retrofitextensions.ServiceClient;
import mx.gigigo.core.spextensions.SharedPreferencesExtensions;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author JG - January 04, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class RootApp
        extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferencesExtensions
                .builder(getApplicationContext())
                .loggable(BuildConfig.DEBUG)
                .setName("TestApp")
                .setMode(Context.MODE_PRIVATE)
                .build();

        initializeServiceClient();
    }

    private void initializeServiceClient() {
        LoggingInterceptor loggerInterceptor = new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();

        NetworkRequestInterceptor requestInterceptor =
                new DefaultNetworkRequestInterceptor(new DefaultNetwork(this));

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggerInterceptor)
                .addInterceptor(requestInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        ServiceClient.builder(client)
                .addEndpoint("https://reqres.in")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    private class DefaultNetworkRequestInterceptor
            extends NetworkRequestInterceptor {

        public DefaultNetworkRequestInterceptor(Network network) {
            super(network);
        }

        @Override
        protected Response interceptResponse(Chain chain) throws IOException {
            return chain.proceed(chain.request());
        }
    }
}
