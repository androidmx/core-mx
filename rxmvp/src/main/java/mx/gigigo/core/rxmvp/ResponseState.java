package mx.gigigo.core.rxmvp;

/**
 * @author Omar Bacilio - January 11, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class ResponseState
        extends Throwable {

    private int code;

    public ResponseState(String message) {
        super(message);
    }

    public ResponseState(String message, int code) {
        this(message);
        this.setCode(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEntryMessage() {
        return "Error " + String.valueOf(getCode()) + " - " + getMessage();
    }
}
