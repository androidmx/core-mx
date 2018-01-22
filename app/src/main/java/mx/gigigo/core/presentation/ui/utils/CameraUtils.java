package mx.gigigo.core.presentation.ui.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Size;
import android.view.TextureView;
import android.widget.TextView;

import mx.gigigo.core.BuildConfig;

/**
 * Created by Gigio on 22/01/18.
 */

public class CameraUtils implements TextureView.SurfaceTextureListener{
    private Context context;
    private CameraManager cameraManager;
    private int cameraFancing;
    private ICameraListener listener;
    private Size previewSize;
    private String cameraId;
    private boolean checkPermissions;
    private HandlerThread handlerThread;
    private Handler handler;
    private CameraDevice cameraDevice;

//    CameraCharacteristics.LENS_FACING_BACK;
    public CameraUtils(Context context, int cameraFancing, ICameraListener listener, boolean checkPermissions){
        this.context = context;
       setCameraFancing(cameraFancing);
        this.listener = listener;
    }


    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public void initCamera(){
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }

    public int getCameraFancing() {
        return cameraFancing;
    }

    public void setCameraFancing(int cameraFancing) {
        if(cameraFancing == 0){
            this.cameraFancing = CameraCharacteristics.LENS_FACING_BACK;
        }else {
            this.cameraFancing = cameraFancing;
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        setupCamera();
        openCamera();
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

    public void setupCamera(){
        try{
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                for (String cameraId : cameraManager.getCameraIdList()) {
                    CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                    if(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == cameraFancing){
                        StreamConfigurationMap streamConfigurationMap =
                                cameraCharacteristics.get(cameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                        previewSize = streamConfigurationMap.getOutputSizes(SurfaceTexture.class)[0];
                        this.cameraId = cameraId;
                    }
                }
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void openCamera(){
        try {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //cameraManager.openCamera(this.cameraId, this, handler);
                }
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initHandleThread(){
        handlerThread = new HandlerThread("camera_background_thread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

//    @Override
//    public void onOpened(@NonNull CameraDevice cameraDevice) {
//        this.cameraDevice = cameraDevice;
//        createPreviewSession();
//    }
//
//    @Override
//    public void onDisconnected(@NonNull CameraDevice cameraDevice) {
//        this.cameraDevice.close();
//        this.cameraDevice = null;
//    }
//
//    @Override
//    public void onError(@NonNull CameraDevice cameraDevice, int i) {
//        this.cameraDevice.close();
//        this.cameraDevice = null;
//    }

    public void createPreviewSession(){

    }


    public interface ICameraListener{
        void surfaceavailable();

    }



}
