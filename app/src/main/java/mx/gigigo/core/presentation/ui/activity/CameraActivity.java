package mx.gigigo.core.presentation.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraMetadata;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import com.zhuinden.simplestack.BackstackDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mx.gigigo.core.R;
import mx.gigigo.core.presentation.model.UserModel;
import mx.gigigo.core.presentation.ui.fragment.FragmentStateChanger;
import mx.gigigo.core.presentation.ui.utils.CameraUtils;
import mx.gigigo.core.presentation.ui.utils.ImageGallery;
import mx.gigigo.core.rxmvp.BaseActivity;

public class CameraActivity extends BaseActivity {
    public static String USER = "user";
    public final static int CODE_RESULT_CAMERA = 109;
    public final static String FILE_IMAGE = "image";
    @BindView(R.id.textureview)
    TextureView textureView;
    @BindView(R.id.button_photo)
    Button bt_Photo;

    private CameraUtils cameraUtils;
    private Unbinder unbinder;
    private ImageGallery imageGallery;
    private UserModel userViewModel;

    private BackstackDelegate backstackDelegate;
    private FragmentStateChanger fragmentStateChanger;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void onInitializeUIComponents() {}

    @Override
    protected void onInitializeMembers() {
        cameraUtils = new CameraUtils(this, CameraCharacteristics.LENS_FACING_BACK,  textureView );
        imageGallery = new ImageGallery();
    }

    @Override
    protected void onBindView() {
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onUnbindView() {
        if(null != unbinder) unbinder.unbind();
    }

    @Override
    protected void onCreateBase(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraUtils.initHandleThread();
        if(textureView.isAvailable()){
            cameraUtils.setupCamera();
            cameraUtils.openCamera();
        }else{
            textureView.setSurfaceTextureListener(cameraUtils.surfaceTextureListener);
        }
    }

    @Override
    protected void onRestoreExtras(Bundle arguments) {
        super.onRestoreExtras(arguments);
        if(arguments.getSerializable(USER) != null){
            userViewModel = (UserModel) arguments.getSerializable(USER);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        cameraUtils.closeCamera();
        cameraUtils.closeBackgroundThread();
    }

    @OnClick(R.id.button_photo)
    public void onClickAction(){
        cameraUtils.lock();
        if(userViewModel != null)
            imageGallery.setNameFile(userViewModel.getId().toString());
        imageGallery.createimageGallery();
        try{
            imageGallery.saveFotoOnGallery(textureView);
        }catch (Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent(CameraActivity.this, DetailUserActivity.class);
        String pathFile = imageGallery.getFileTemp().getPath();
        intent.putExtra(FILE_IMAGE, pathFile);
        setResult(CODE_RESULT_CAMERA,intent);
        finish();
    }

}
