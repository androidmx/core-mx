package mx.gigigo.core.retrofitextensions;

import android.content.Context;
import android.support.annotation.StringRes;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author VT - January 25, 2018.
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class IHttpErrorHandling{
    public static final int DEFAULT_CODE = 0;

    private final Context context;

    private Map<Integer, String> mapCodes;

    public IHttpErrorHandling(Context context){
        this.context = context;
        mapCodes = new HashMap<>();

        try {
            put(HttpURLConnection.HTTP_BAD_REQUEST, R.string.message_http_default);
            put(HttpURLConnection.HTTP_UNAUTHORIZED, R.string.message_http_default);
            put(HttpURLConnection.HTTP_FORBIDDEN, R.string.message_http_default);
            put(HttpURLConnection.HTTP_NOT_FOUND, R.string.message_http_default);
            put(HttpURLConnection.HTTP_BAD_METHOD, R.string.message_http_default);
            put(HttpURLConnection.HTTP_BAD_GATEWAY, R.string.message_http_default);
            put(DEFAULT_CODE, R.string.message_http_default);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setMapCodes(HashMap<Integer, String> mapCodes) {
        this.mapCodes = mapCodes;
    }

    public String getErrorByHttpCode(int code){
        //return mapCodes.get(code);
        return getStringFromId(code);
    }

    public void put(Integer key, @StringRes Integer value) {
        mapCodes.put(key, getStringFromId(value));
    }

    public void put(Integer key, String value) {
        mapCodes.put(key, value);
    }

    private String getStringFromId(Integer value) {
        return context.getString(value,
                "string",
                context.getPackageName());
    }


//
//    public String getErrorHttpDefault(){
//        return Resources.getSystem().getString(R.string.message_http_default);
//    }
//
//    public String getErrorHttpBadRequest( ) {
//        return getStringFromresources("message_http_400");
//
//    }
//
//    public String getErrorHttpUnauthorized(){
//        return getStringFromresources("message_http_401");
//    }
//
//    public String getErrorHttpForbidden(){
//        return getStringFromresources("message_http_403");
//    }
//
//    public String getErrorHttpNotFound(){
//        return getStringFromresources("message_http_404");
//    }
//
//
//    public String getErrorHttpNotAllowed(){
//        return getStringFromresources("message_http_405");
//    }
//
//    public String getErrorBadGateway(){
//        return getStringFromresources("message_http_502");
//    }
//
//
//    public String getStringFromresources(String name){
//        try{
//            return context.getString(context.getResources().getIdentifier(name,
//                    "string", context.getPackageName()));
//        }catch (Exception e){
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//
//
//    public String getErrorByHttpCode(int code){
//        if(mapCodes == null) return "";
//
//        for (Integer o : mapCodes.keySet()) {
//            if (code == o) {
//                try {
//                    Method method = IHttpErrorHandling.class.getMethod(mapCodes.get(code));
//                    return (String) method.invoke(this);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
//        }
//         return "";
//    }


}


