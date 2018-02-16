package mx.gigigo.core.rxextensions;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author VT - January 25, 2018.
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class HttpErrorHandling {
    public static final int DEFAULT_CODE = 0;

    private Map<Integer, String> mapCodes;

    private static final String HTTP_DEFAULT
            = "The specific HTTP request has been interrupted";
    private static final String HTTP_400
            = "Bad Request - The server could not understand the request due to invalid syntax.";
    private static final String HTTP_401
            = "Unauthorized - Authentication is needed to get requested response.";
    private static final String HTTP_403
            = "Forbidden - Client does not have access rights to the content so server is rejecting to give proper response.";
    private static final String HTTP_404
            = "Not Found - Server can not find requested resource.";
    private static final String HTTP_405
            = "Method Not Allowed - The request method is known by the server but has been disabled and cannot be used.";
    private static final String HTTP_502
            = "Bad Gateway - The server was acting as a gateway or proxy and received an invalid response from the upstream server.";


    public HttpErrorHandling() {
        mapCodes = new HashMap<>();

        put(HttpURLConnection.HTTP_BAD_REQUEST, HTTP_400);
        put(HttpURLConnection.HTTP_UNAUTHORIZED, HTTP_401);
        put(HttpURLConnection.HTTP_FORBIDDEN, HTTP_403);
        put(HttpURLConnection.HTTP_NOT_FOUND, HTTP_404);
        put(HttpURLConnection.HTTP_BAD_METHOD, HTTP_405);
        put(HttpURLConnection.HTTP_BAD_GATEWAY, HTTP_502);
        put(DEFAULT_CODE, HTTP_DEFAULT);
    }

    public void set(Map<Integer, String> mapCodes) {
        this.mapCodes = mapCodes;
    }

    public String getErrorByHttpCode(int code){
        try {
            return mapCodes.get(code);
        } catch (Exception e){
            return HTTP_DEFAULT;
        }
    }

    public void put(Integer key, String value) {
        mapCodes.put(key, value);
    }
}


