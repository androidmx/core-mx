package mx.gigigo.core.spextensions;

import android.content.Context;

import java.lang.reflect.Type;

/**
 * @author JG - January 12, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class SharedPreferencesExtensions {

    private static DefaultStorage storage;

    public static SharedPreferencesBuilder builder(Context context) {
        return new SharedPreferencesBuilder(context);
    }

    private SharedPreferencesExtensions(SharedPreferencesBuilder builder) {
        storage = new DefaultStorage(builder.context, builder.name, builder.mode, builder.isDebug);
    }

    public static boolean settingExist(String key) {
        return storage.settingExist(key);
    }

    public static <T> boolean put(String key, Type type, T value) {
        return SharedPreferencesExtensions.put(key, type, value, true);
    }

    public static <T> boolean put(String key, Type type, T value, boolean replaceIfExist) {
        return storage.put(key, type, value, replaceIfExist);
    }

    public static <T> T get(String key, Type type) {
        T defaultValue = DefaultStorage.DefaultValues.defaultValue(type);
        return SharedPreferencesExtensions.get(key, type, defaultValue);
    }

    public static <T> T get(String key, Type type, T defaultValue) {
        return storage.get(key, type, defaultValue);
    }

    public static boolean delete(String key) {
        return storage.delete(key);
    }

    public static class SharedPreferencesBuilder {
        private final Context context;
        private String name;
        private int mode;
        private boolean isDebug;

        public SharedPreferencesBuilder(Context context) {
            this.context = context;
        }

        public SharedPreferencesBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public SharedPreferencesBuilder setMode(int mode) {
            this.mode = mode;
            return this;
        }

        public SharedPreferencesBuilder loggable(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public SharedPreferencesExtensions build() {
            return new SharedPreferencesExtensions(this);
        }
    }
}
