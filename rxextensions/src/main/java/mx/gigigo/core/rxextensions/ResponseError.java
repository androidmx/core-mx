package mx.gigigo.core.rxextensions;

/**
 * @author VT - January 25, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ResponseError {
    String getError();
    boolean hasErrorMessage();
}
