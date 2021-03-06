package mx.gigigo.core.retrofitextensions;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author JG - December 28, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public class ServiceClientFactory {

    public static <T> T createService(ServiceClient serviceClient,
                                      Class<T> classType) {
        return createService(serviceClient,
                classType,
                0);
    }

    public static <T> T createService(ServiceClient serviceClient,
                                      Class<T> classType,
                                      int endpointIndex) {

        String endpoint = serviceClient.getEndpointByIndex(endpointIndex);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(endpoint)
                .client(serviceClient.getOkHttpClient());

        if(serviceClient.hasConverterFactories()) {
            for(Converter.Factory factory : serviceClient.getConverterFactories()) {
                retrofitBuilder.addConverterFactory(factory);
            }
        }

        if(serviceClient.hasAdapterFactories()) {
            for(CallAdapter.Factory factory : serviceClient.getAdapterFactories()) {
                retrofitBuilder.addCallAdapterFactory(factory);
            }
        }

        return retrofitBuilder.build()
                .create(classType);
    }
}
