package mx.gigigo.core.presentation.ui.activity;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.TextureView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mx.gigigo.core.R;
import mx.gigigo.core.presentation.ui.utils.CameraUtils;
import mx.gigigo.core.rxmvp.BaseActivity;

public class CameraActivity extends BaseActivity implements CameraUtils.ICameraListener{
    @BindView(R.id.texture_view)
    TextureView textureView;

    private CameraUtils cameraUtils;
    private Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void onInitializeUIComponents() {

    }

    @Override
    protected void onInitializeMembers() {
        cameraUtils = new CameraUtils(this, CameraCharacteristics.LENS_FACING_BACK,this,  textureView );

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //cameraUtils.initHandleThread();
        if(textureView.isAvailable()){
            cameraUtils.setupCamera();
            cameraUtils.openCamera();
        }else{
            textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                    cameraUtils.openCamera();
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                    return false;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        cameraUtils.closeCamera();
        cameraUtils.closeBackgroundThread();
    }

    @Override
    public void surfaceavailable() {

    }

    public TextureView.SurfaceTextureListener surfaceTextureListener =  new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            cameraUtils.setupCamera();
            cameraUtils.openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
            Log.i(CameraUtils.class.getName(), "onSurface");
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            Log.i(CameraUtils.class.getName(), "onSurfaceTextureDestroyed");
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            Log.i(CameraUtils.class.getName(), "onSurfaceUpdate");

        }
    };
}
