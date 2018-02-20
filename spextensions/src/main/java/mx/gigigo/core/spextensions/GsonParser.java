package mx.gigigo.core.spextensions;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * @author JG - January 15, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class GsonParser
        implements Parser {

    private static final String TAG = GsonParser.class.getSimpleName();

    private final Gson gson;

    public GsonParser(Gson gson) {
        this.gson = gson;
    }

    public <T> String serialize(T data) {
        return serialize(data, data.getClass());
    }

    public <T> String serialize(T data, Type sourceType) {
        String json;

        try {
            json = gson.toJson(data, sourceType);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            json = null;
        }

        return json;
    }

    public <T> T deserialize(String json, Class<T> typeClass) {
        return deserialize(json,  (Type) typeClass);
    }

    public <T> T deserialize(String json, Type type) {
        T data;

        try {
            data = gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            data = null;
        }

        return data;
    }
}
