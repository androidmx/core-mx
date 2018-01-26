package mx.gigigo.core.retrofitextensions;

import android.content.Context;
import android.content.res.Resources;

import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Juan Godínez Vera - 5/9/2017.
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class IHttpErrorHandling{
    public static final int DEFAULT_CODE = 0;
    private HashMap<Integer, String> mapCodes;
    private Context context;

    public IHttpErrorHandling(Context context){
        this.context = context;
        mapCodes = new HashMap<Integer, String>();
        try {
            mapCodes.put(HttpURLConnection.HTTP_BAD_REQUEST, "getErrorHttpBadRequest");
            mapCodes.put(HttpURLConnection.HTTP_UNAUTHORIZED, "getErrorHttpUnauthorized");
            mapCodes.put(HttpURLConnection.HTTP_FORBIDDEN, "getErrorHttpForbidden");
            mapCodes.put(HttpURLConnection.HTTP_NOT_FOUND,"getErrorHttpNotFound");
            mapCodes.put(HttpURLConnection.HTTP_BAD_METHOD,"getErrorHttpNotAllowed");
            mapCodes.put(HttpURLConnection.HTTP_BAD_GATEWAY,"getErrorBadGateway");
            mapCodes.put(DEFAULT_CODE,"getErrorHttpDefault");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public HashMap<Integer, String> getMapCodes() {
        return mapCodes;
    }

    public void setMapCodes(HashMap<Integer, String> mapCodes) {
        this.mapCodes = mapCodes;
    }

    public String getErrorHttpDefault(){
        return Resources.getSystem().getString(R.string.message_http_default);
    }

    public String getErrorHttpBadRequest( ) {
        return getStringFromresources("message_http_400");

    }

    public String getErrorHttpUnauthorized(){
        return getStringFromresources("message_http_401");
    }

    public String getErrorHttpForbidden(){
        return getStringFromresources("message_http_403");
    }

    public String getErrorHttpNotFound(){
        return getStringFromresources("message_http_404");
    }


    public String getErrorHttpNotAllowed(){
        return getStringFromresources("message_http_405");
    }

    public String getErrorBadGateway(){
        return getStringFromresources("message_http_502");
    }


    public String getStringFromresources(String name){
        try{
            return context.getString(context.getResources().getIdentifier(name,
                    "string", context.getPackageName()));
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }



    public String getErrorByHttpCode(int code){
        if(mapCodes == null) return "";

        for (Object o : mapCodes.keySet()) {
            if (code == (int) o) {
                try {
                    Method method = IHttpErrorHandling.class.getMethod(mapCodes.get(code));
                    return (String) method.invoke(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
         return "";
    }


}


