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

import com.zhuinden.simplestack.BackstackDelegate;
import com.zhuinden.simplestack.HistoryBuilder;
import com.zhuinden.simplestack.StateChange;
import com.zhuinden.simplestack.StateChanger;

import mx.gigigo.core.R;
import mx.gigigo.core.permissions.Permissions;
import mx.gigigo.core.permissions.PermissionsResult;
import mx.gigigo.core.permissions.ShowRequestPermissionRationale;
import mx.gigigo.core.presentation.model.UserModel;
import mx.gigigo.core.presentation.ui.fragment.FragmentStateChanger;
import mx.gigigo.core.presentation.ui.fragment.MvpBindingFragment;
import mx.gigigo.core.presentation.ui.fragment.detail.DetailKey;
import mx.gigigo.core.presentation.ui.fragment.detail.DetailUserFragment;

public class DetailUserActivity extends CoreBaseActvity implements PermissionsResult, StateChanger {

    private static final String TAG = DetailUserActivity.class.getName();
    private final static int PERMISSIONS_REQUEST_CODE = 103;
    private final static String USER = "user";
    private final static String TAG_FRAGMENT = "detail";
    private UserModel user;
    private Permissions permissionsManager;

    BackstackDelegate backstackDelegate;
    FragmentStateChanger fragmentStateChanger;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_user;
    }

    @Override
    protected void onInitializeUIComponents() {

    }

    public BackstackDelegate getBackstackDelegate() {
        return backstackDelegate;
    }

    public void setBackstackDelegate(BackstackDelegate backstackDelegate) {
        this.backstackDelegate = backstackDelegate;
    }

    @Override
    protected void onInitializeMembers() {
        permissionsManager = new Permissions.Builder(this)
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
        if(permissionsManager != null){
            permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void checkPermissions(){
        String[] permissionRequired = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        permissionsManager.check(permissionRequired, PERMISSIONS_REQUEST_CODE,
                ShowRequestPermissionRationale.AT_END);

    }

    @Override
    protected void onCreateBase(Bundle savedInstanceState) {
        super.onCreateBase(savedInstanceState);
        backstackDelegate = new BackstackDelegate(null);
        backstackDelegate.onCreate(savedInstanceState, getLastNonConfigurationInstance(), HistoryBuilder.single(DetailKey.create(user.getId())));
        backstackDelegate.registerForLifecycleCallbacks(this);
        fragmentStateChanger = new FragmentStateChanger(getSupportFragmentManager(), R.id.container);
        backstackDelegate.setStateChanger(this);
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


    private void setSnackBarForSettings(){
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

    private void setIntentForSettingPermissions(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return backstackDelegate.onRetainCustomNonConfigurationInstance();
    }

    @Override
    public void onBackPressed() {
        if(!backstackDelegate.onBackPressed()){
            super.onBackPressed();
        }
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if(TAG.equals(name)){
            return this;
        }
        return super.getSystemService(name);
    }

    @Override
    public void handleStateChange(@NonNull StateChange stateChange, @NonNull Callback completionCallback) {
        if(stateChange.topNewState().equals(stateChange.topPreviousState())){
            completionCallback.stateChangeComplete();
            return;
        }
        fragmentStateChanger.handleStateChange(stateChange);
        completionCallback.stateChangeComplete();
    }
}
