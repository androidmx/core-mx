package mx.gigigo.core.retrofitextensions;

import android.content.Context;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * Created by isarael.cortes on 8/28/18.
 */

public class ServiceClient {
    private final List<String> endpoints;
    private final List<Converter.Factory> converterFactories;
    private final List<CallAdapter.Factory> adapterFactories;
    private OkHttpClient client;

    private static volatile ServiceClient defaultInstance;

    private static final ServiceClientBuilder DEFAULT_BUILDER
            = new ServiceClientBuilder(getDefaultClient());

    private static OkHttpClient getDefaultClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpClient.Builder getOkHttpClientBuilder() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);
    }

    public static NetworkRequestInterceptor getNetworkRequestInterceptor(Context context){
        NetworkRequestInterceptor networkRequestInterceptor =
                new DefaultNetworkRequestInterceptor(new DefaultNetwork(context));
        return networkRequestInterceptor;
    }

    public static LoggingInterceptor getLoggingBasicInterceptor(){
        return getLoggingInterceptor(Level.BASIC);
    }

    public static LoggingInterceptor getLoggingHeaderInterceptor(){
        return getLoggingInterceptor(Level.HEADERS);
    }

    public static LoggingInterceptor getLoggingBodyInterceptor(){
        return getLoggingInterceptor(Level.BODY);
    }

    public static LoggingInterceptor getLoggingInterceptor(Level level){
        LoggingInterceptor loggerInterceptor = new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(level)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .build();
        return loggerInterceptor;
    }

    public static ServiceClient getDefault() {
        if (defaultInstance == null) {
            synchronized (ServiceClient.class) {
                if (defaultInstance == null) {
                    defaultInstance = new ServiceClient();
                }
            }
        }

        return defaultInstance;
    }

    public static ServiceClientBuilder builder(OkHttpClient client) {
        return new ServiceClientBuilder(client);
    }

    private ServiceClient() {
        this(DEFAULT_BUILDER);
    }

    private ServiceClient(ServiceClientBuilder builder) {
        endpoints = builder.endpoints;
        converterFactories = builder.converterFactories;
        adapterFactories = builder.adapterFactories;
        client = builder.client;
    }

    public String getEndpointByIndex(int index) {
        if(null != endpoints && !endpoints.isEmpty() && index < endpoints.size()) {
            return endpoints.get(index);
        }
        return null;
    }

    public List<String> getEndpoints() {
        return endpoints;
    }

    public List<Converter.Factory> getConverterFactories() {
        return converterFactories;
    }

    public boolean hasConverterFactories() {
        return null != converterFactories && !converterFactories.isEmpty();
    }

    public List<CallAdapter.Factory> getAdapterFactories() {
        return adapterFactories;
    }

    public boolean hasAdapterFactories() {
        return null != adapterFactories && !adapterFactories.isEmpty();
    }

    public OkHttpClient getOkHttpClient() {
        return client;
    }

    public static class ServiceClientBuilder {
        private final List<String> endpoints = new ArrayList<>();
        private final List<Converter.Factory> converterFactories = new ArrayList<>();
        private final List<CallAdapter.Factory> adapterFactories = new ArrayList<>();

        private final OkHttpClient client;

        ServiceClientBuilder(OkHttpClient client) {
            this.client = client;
        }

        public ServiceClientBuilder addEndpoint(String endpoint) {
            endpoints.add(endpoint);
            return this;
        }

        public ServiceClientBuilder addConverterFactory(Converter.Factory factory) {
            converterFactories.add(factory);
            return this;
        }

        public ServiceClientBuilder addCallAdapterFactory(CallAdapter.Factory factory) {
            adapterFactories.add(factory);
            return this;
        }

        public ServiceClient build() {
            if(null != ServiceClient.defaultInstance) {
                throw new ServiceClientException();
            }

            ServiceClient.defaultInstance = new ServiceClient(this);

            return ServiceClient.defaultInstance;
        }

        public static class ServiceClientException
                extends RuntimeException {
            public ServiceClientException() {
                super("Default service client instance already exists.");
            }
        }
    }
}