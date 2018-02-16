package mx.gigigo.core.rxextensions;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for values transform
 *
 * @param <T1> The input value type
 * @param <T2> The output value type
 *
 * @author JG - December 19, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class Transform<T1, T2> {

    /**
     * Transforms the input value in the output value specified
     * @param value input value type
     * @return the output value type if valid; otherwise null
     */
    public abstract T2 transform(T1 value);

    /**
     * Transforms the output value in the input value specified
     * @param value output value type
     * @return the input value type if valid; otherwise null
     */
    public abstract T1 reverseTransform(T2 value);

    /**
     * Transforms the input values in the output values specified
     * @param values input values type
     * @return the output values type if valid; otherwise null
     */
    public List<T2> transform(List<T1> values) {
        if(null == values || values.isEmpty()) return null;

        List<T2> returnValues = new ArrayList<>(values.size());
        for (T1 value : values) {
            returnValues.add(transform(value));
        }
        return returnValues;
    }

    /**
     * Transforms the output values in the input values specified
     * @param values output values type
     * @return the input values type if valid; otherwise null
     */
    public List<T1> reverseTransform(List<T2> values) {
        if(null == values || values.isEmpty()) return null;

        List<T1> returnValues = new ArrayList<>(values.size());
        for (T2 value : values) {
            returnValues.add(reverseTransform(value));
        }
        return returnValues;
    }
}
