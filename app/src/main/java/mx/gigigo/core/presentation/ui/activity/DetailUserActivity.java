package mx.gigigo.core.presentation.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import mx.gigigo.core.R;
import mx.gigigo.core.permissions.Permissions;
import mx.gigigo.core.permissions.PermissionsResult;
import mx.gigigo.core.permissions.ShowRequestPermissionRationale;
import mx.gigigo.core.presentation.model.UserModel;
import mx.gigigo.core.presentation.ui.fragment.DetailUserFragment;
import mx.gigigo.core.presentation.ui.fragment.MvpBindingFragment;
import mx.gigigo.core.rxmvp.BaseFragment;

public class DetailUserActivity extends CoreBaseActvity implements PermissionsResult {
    public static int PERMISSIONS_REQUEST_CAMERA = 103;
    public static int PERMISSIONS_REQUEST_READ = 105;
    public static String USER = "user";
    private UserModel user;
    private Permissions permissionsCustom;
    private String[] permissionRequired;
    private String[] permissionsRead;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_user;
    }

    @Override
    protected void onInitializeUIComponents() {
        initFragment();
        MvpBindingFragment fragment = DetailUserFragment.newInstance(user.getId());
        addFragment(R.id.container, fragment);

    }

    @Override
    protected void onInitializeMembers() {
        permissionsCustom = new Permissions.Builder(this)
                .setPermissionsResult(this)
                .setDialogTitle(getResources().getString(R.string.dialog_title_permission))
                .build();

        checkPermissions();
    }

    @Override
    protected void onRestoreExtras(Bundle arguments) {
        super.onRestoreExtras(arguments);
        user = (UserModel) arguments.getSerializable(USER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(permissions != null){
            permissionsCustom.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void checkPermissions(){
        permissionRequired =new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        permissionsCustom.check(permissionRequired, PERMISSIONS_REQUEST_CAMERA,
                ShowRequestPermissionRationale.AT_END);

    }

    public void checkPermissionsLocale(){

        permissionsRead = new String[]{
                Manifest.permission.READ_CONTACTS
        };

        permissionsCustom.check(permissionsRead, PERMISSIONS_REQUEST_READ,
                ShowRequestPermissionRationale.AT_END);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        Log.i(DetailUserActivity.class.getName(), "Permissions Granted");
    }

    @Override
    public void onPermissionsDenied(int requestCode) {
        Log.i(DetailUserActivity.class.getName(), "Permissions denied");
    }

    @Override
    public void onPermissionsDeniedPermanently(int requestCode) {
        Log.i(DetailUserActivity.class.getName(), "Permissions denied permanent");
        setSnackBarForSettings();
    }


    public void setSnackBarForSettings(){
        Snackbar.make(findViewById(android.R.id.content),
                getResources().getString(R.string.snack_message_camera_persmissions),
                Snackbar.LENGTH_INDEFINITE).setAction(getResources().getString(R.string.snack_label_camera_setting)
                , new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setIntentForSettingPermissions();
                    }
                }).show();
    }

    public void setIntentForSettingPermissions(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }


}
