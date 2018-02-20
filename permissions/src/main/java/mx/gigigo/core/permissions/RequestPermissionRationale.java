package mx.gigigo.core.permissions;

/**
 * @author Omar Bacilio - January 17, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public interface RequestPermissionRationale {
    void showRequestPermissionRationale(int requestCode, UserResponse userResponse);

    interface UserResponse {
        void accepted(int requestCode);

        void canceled(int requestCode);
    }
}
