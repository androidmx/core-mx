package mx.gigigo.core.permissions;

/**
 * @author Omar Bacilio - June 7, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public interface PermissionsResult {
    void onPermissionsGranted(int requestCode);

    void onPermissionsDenied(int requestCode);

    void onPermissionsDeniedPermanently(int requestCode);
}
