package mx.gigigo.core.presentation;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import mx.gigigo.core.BuildConfig;
import mx.gigigo.core.retrofitextensions.DefaultNetwork;
import mx.gigigo.core.retrofitextensions.Network;
import mx.gigigo.core.retrofitextensions.NetworkRequestInterceptor;
import mx.gigigo.core.retrofitextensions.SSLFactory;
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
public class RootApp extends Application implements SSLFactory.SSLBuildProvider{

    private static Context context;

    public static Context getAppContext(){
        return context;
    }
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
        context = getApplicationContext();
    }

    private void initializeServiceClient() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();

        LoggingInterceptor loggerInterceptor = ServiceClient.getLoggingInterceptor(Level.BODY);

        OkHttpClient.Builder builder = ServiceClient.getOkHttpClientBuilder()
                .addInterceptor(loggerInterceptor)
                .addInterceptor(ServiceClient.getNetworkRequestInterceptor(this));

        //Installa certificados SSL mediante la interfaz SSLFactory.SSLBuildProvider
        SSLFactory.installCertificades(builder, this);

        ServiceClient.builder(builder.build())
                .addEndpoint("https://reqres.in")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Override
    public InputStream getCertificadeInputStream() throws IOException {
        return getAssets().open("lalaenterprise-s.crt");
    }

    @Override
    public String getCertificadeEntryName() {
        return "your.domain.name.staging";
    }

}
