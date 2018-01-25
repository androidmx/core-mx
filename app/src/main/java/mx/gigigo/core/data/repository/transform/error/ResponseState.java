package mx.gigigo.core.data.repository.transform.error;

/**
 * @author Omar Bacilio - January 11, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class ResponseState extends Throwable {

    private int code;
    private Object object;

    public ResponseState(String message) {
        super(message);
    }

    public ResponseState(String message, int code) {
        this(message);
        this.setCode(code);
    }

    public ResponseState(String message, int code, Object object) {
        this(message);
        this.setCode(code);
        this.setObject(object);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public String getEntryMessage() {
        return String.format("Error %1$d - %2$s", getCode(), getMessage());
    }
}
