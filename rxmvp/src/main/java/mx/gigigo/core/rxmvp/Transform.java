package mx.gigigo.core.rxmvp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JG - December 19, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class Transform<T1, T2> {
    public abstract T2 transform(T1 value);

    public abstract T1 reverseTransform(T2 value);

    public List<T2> transform(List<T1> values) {
        if(null == values || values.isEmpty()) return null;

        List<T2> returnValues = new ArrayList<>(values.size());
        for (T1 value : values) {
            returnValues.add(transform(value));
        }
        return returnValues;
    }

    public List<T1> reverseTransform(List<T2> values) {
        if(null == values || values.isEmpty()) return null;

        List<T1> returnValues = new ArrayList<>(values.size());
        for (T2 value : values) {
            returnValues.add(reverseTransform(value));
        }
        return returnValues;
    }
}
