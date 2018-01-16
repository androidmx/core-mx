package mx.gigigo.core.spextensions;

/**
 * @author JG - January 15, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
interface Storage {
    boolean settingExist(String key);
    <T> boolean put(String key, Class<T> type, T value, boolean replaceIfExist);
    <T> T get(String key, Class<T> type, T defaultValue);
    boolean delete(String key);
}
