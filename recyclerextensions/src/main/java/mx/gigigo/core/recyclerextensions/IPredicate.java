package mx.gigigo.core.recyclerextensions;

import android.support.annotation.Nullable;

/**
 * @author JG - December 29, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public interface IPredicate<T> {
    boolean apply(@Nullable T input);
}
