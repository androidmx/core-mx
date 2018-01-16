package mx.gigigo.core.rxmvp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JG - December 19, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class Mapper<T1, T2> {
    public abstract T2 map(T1 value);

    public abstract T1 reverseMap(T2 value);

    public List<T2> map(List<T1> values) {
        if(null == values || values.isEmpty()) return null;

        List<T2> returnValues = new ArrayList<>(values.size());
        for (T1 value : values) {
            returnValues.add(map(value));
        }
        return returnValues;
    }

    public List<T1> reverseMap(List<T2> values) {
        if(null == values || values.isEmpty()) return null;

        List<T1> returnValues = new ArrayList<>(values.size());
        for (T2 value : values) {
            returnValues.add(reverseMap(value));
        }
        return returnValues;
    }
}
