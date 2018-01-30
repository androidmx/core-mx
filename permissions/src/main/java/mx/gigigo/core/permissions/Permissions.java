package mx.gigigo.core.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * @author Omar Bacilio - January 16, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public class Permissions implements RequestPermissionRationale.UserResponse {

    private static final String TAG = Permissions.class.getSimpleName();

    private final Context context;
    private Fragment fragment;
    private PermissionsResult permissionsResult;
    private RequestPermissionRationale requestPermissionRationale;

    private String[] requestedPermissions;
    private int requestCode;
    private boolean isFragment = false;
    private ShowRequestPermissionRationale showRequestPermissionRationale;
    private String dialogExplanationTitle = "";
    private String dialogExplanationMessage = "";
    private String dialogExplanationOkButtonText = "";
    private String dialogExplanationCancelButtonText = "";
    private AlertDialog alertDialog;

    public Permissions(Builder builder) {
        this.context = builder.context;
        this.fragment = builder.fragment;
        this.isFragment = fragment != null;
        this.permissionsResult = builder.permissionsResult;
        this.requestPermissionRationale = builder.requestPermissionRationale;

        this.dialogExplanationTitle = builder.dialogExplanationTitle;
        this.dialogExplanationMessage = builder.dialogExplanationMessage;
        this.dialogExplanationOkButtonText = builder.dialogExplanationOkButtonText;
        this.dialogExplanationCancelButtonText = builder.dialogExplanationCancelButtonText;
    }

    public void check(@NonNull String[] permissions,
                      int requestCode,
                      ShowRequestPermissionRationale showRequestPermissionRationale, AlertDialog alertDialog ) {

        if (permissions == null || permissions.length == 0) {
            if (permissionsResult != null) {
                permissionsResult.onPermissionsDenied(requestCode);
            }

            return;
        }

        validatePermissions(permissions, requestCode, showRequestPermissionRationale, alertDialog);
    }

    public void check(@NonNull String[] permissions, int requestCode, AlertDialog alertDialog) {
        this.check(permissions, requestCode, ShowRequestPermissionRationale.NONE, alertDialog);
    }

    private void validatePermissions(String[] permissions,
                                     int requestCode,
                                     ShowRequestPermissionRationale showRequestPermissionRationale, AlertDialog alertDialog) {
        this.requestCode = requestCode;
        this.requestedPermissions = permissions;
        this.showRequestPermissionRationale = showRequestPermissionRationale;

        if (hasAllPermissions(permissions)) {

            if (permissionsResult != null)
                permissionsResult.onPermissionsGranted(requestCode);

            return;
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[0]) &&
                (showRequestPermissionRationale == ShowRequestPermissionRationale.AT_START ||
                        showRequestPermissionRationale == ShowRequestPermissionRationale.BOTH)) {

            if (requestPermissionRationale != null)
                requestPermissionRationale.showRequestPermissionRationale(requestCode, this);
            else
                if(alertDialog == null)
                    showRequestPermissionRationaleAlert();
                else
                    alertDialog.show();

        } else {
            requestPermissions();
        }
    }

    private void requestPermissions() {
        if (fragment != null) {
            fragment.requestPermissions(requestedPermissions, requestCode);
        } else {
            ActivityCompat.requestPermissions(getActivity(), requestedPermissions, requestCode);
        }
    }

    private boolean hasAllPermissions(String[] permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (!hasPermissionGrated(permissions[i])) {
                return false;
            }
        }

        return true;
    }

    private boolean hasPermissionGrated(String permissionName) {
        int permissionCheck = ContextCompat.checkSelfPermission(context, permissionName);

        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && this.requestCode == requestCode) {

            int permissionDeniedPosition = allPermissionRequestedGranted(grantResults);

            if (permissionsResult == null)
                return;

            if (permissionDeniedPosition == -1) {
                permissionsResult.onPermissionsGranted(requestCode);
                return;
            }

            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    requestedPermissions[permissionDeniedPosition])) {
                permissionsResult.onPermissionsDeniedPermanently(requestCode);
                return;
            }

            if (showRequestPermissionRationale == ShowRequestPermissionRationale.AT_END ||
                    showRequestPermissionRationale == ShowRequestPermissionRationale.BOTH) {
                if (requestPermissionRationale != null)
                    requestPermissionRationale.showRequestPermissionRationale(requestCode, this);
                else
                    showRequestPermissionRationaleAlert();
            } else {
                permissionsResult.onPermissionsDenied(requestCode);
            }


        }
    }

    private int allPermissionRequestedGranted(int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return i;
            }
        }

        return -1;
    }

    private void showRequestPermissionRationaleAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(!dialogExplanationTitle.isEmpty() ? dialogExplanationTitle :
                        context.getString(R.string.permissions_dialog_title_default, "string", context.getPackageName()))
                .setMessage(!dialogExplanationMessage.isEmpty() ? dialogExplanationMessage :
                        context.getString(R.string.permissions_dialog_message_default, "string", context.getPackageName()))
                .setPositiveButton(!dialogExplanationOkButtonText.isEmpty() ?
                        dialogExplanationOkButtonText :
                        context.getString(R.string.permissions_dialog_positive_button, "string", context.getPackageName()),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermissions();
                    }
                })
                .setNegativeButton(!dialogExplanationCancelButtonText.isEmpty() ?
                                dialogExplanationCancelButtonText :
                                context.getString(R.string.permissions_dialog_negative_button, "string", context.getPackageName()),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (permissionsResult != null)
                                    permissionsResult.onPermissionsDenied(requestCode);
                            }
                        })
                .create();
        alertDialog.show();
    }

    public AlertDialog showRequestPermissionRationaleAlertCustom(String title, String message, String positiveButton, String negativeButton) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title != null && !title.isEmpty() ? title :
                        context.getString(R.string.permissions_dialog_title_default, "string", context.getPackageName()))
                .setMessage(message != null && !message.isEmpty() ? message:
                        context.getString(R.string.permissions_dialog_message_default, "string", context.getPackageName()))
                .setPositiveButton(positiveButton != null && !positiveButton.isEmpty() ? positiveButton:
                                context.getString(R.string.permissions_dialog_positive_button, "string", context.getPackageName()),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestPermissions();
                            }
                        })
                .setNegativeButton(negativeButton != null && !negativeButton.isEmpty() ? negativeButton:
                                context.getString(R.string.permissions_dialog_negative_button, "string", context.getPackageName()),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (permissionsResult != null)
                                    permissionsResult.onPermissionsDenied(requestCode);
                            }
                        })
                .create();
        return alertDialog;
    }

    private Activity getActivity() {
        return isFragment ? fragment.getActivity() : (Activity) context;
    }

    @Override
    public void accepted(int requestCode) {
        requestPermissions();
    }

    @Override
    public void canceled(int requestCode) {
        if (permissionsResult != null)
            permissionsResult.onPermissionsDenied(requestCode);
    }

    public static class Builder {
        private Context context;
        private Fragment fragment;
        private PermissionsResult permissionsResult;
        private RequestPermissionRationale requestPermissionRationale;
        private String dialogExplanationTitle = "";
        private String dialogExplanationMessage = "";
        private String dialogExplanationOkButtonText = "";
        private String dialogExplanationCancelButtonText = "";

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setFragment(Fragment fragment) {
            this.fragment = fragment;
            return this;
        }

        public Builder setPermissionsResult(PermissionsResult permissionsResult) {
            this.permissionsResult = permissionsResult;
            return this;
        }

        public Builder setRequestPermissionRationale(
                RequestPermissionRationale requestPermissionRationale) {
            this.requestPermissionRationale = requestPermissionRationale;
            return this;
        }

        public Builder setDialogTitle(@NonNull String title) {
            this.dialogExplanationTitle = title;
            return this;
        }

        public Builder setDialogMessage(@NonNull String message) {
            this.dialogExplanationMessage = message;
            return this;
        }

        public Builder setDialogOkButtonText(@NonNull String okButtonText) {
            this.dialogExplanationOkButtonText = okButtonText;
            return this;
        }

        public Builder setDialogCancelButtonText(@NonNull String cancelButtonText) {
            this.dialogExplanationCancelButtonText = cancelButtonText;
            return this;
        }

        public Permissions build() {
            return new Permissions(this);
        }
    }
}
