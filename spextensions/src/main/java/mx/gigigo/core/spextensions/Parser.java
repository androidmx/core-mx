package mx.gigigo.core.spextensions;

import java.lang.reflect.Type;

/**
 * @author JG - January 15, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
interface Parser {
    <T> String serialize(T data);
    <T> String serialize(T data, Type sourceType);
    <T> T deserialize(String json, Class<T> typeClass);
    <T> T deserialize(String json, Type type);
}
