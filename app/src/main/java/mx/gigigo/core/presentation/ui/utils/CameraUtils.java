package mx.gigigo.core.presentation.ui.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;

import mx.gigigo.core.BuildConfig;

/**
 * Created by Gigio on 22/01/18.
 */

public class CameraUtils{
    private Context context;
    private CameraManager cameraManager;
    private int cameraFancing;
    private ICameraListener listener;
    private Size previewSize;
    private String cameraId;
    private HandlerThread handlerThread;
    private Handler handler;
    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSession;
    private TextureView textureView;
    private CaptureRequest captureRequest;

//    CameraCharacteristics.LENS_FACING_BACK;
    public CameraUtils(Context context, int cameraFancing, ICameraListener listener, TextureView textureView){
        this.context = context;
       setCameraFancing(cameraFancing);
        this.listener = listener;
        this.textureView = textureView;
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



    public TextureView.SurfaceTextureListener surfaceTextureListener =  new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            setupCamera();
            openCamera();
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
                    cameraManager.openCamera(this.cameraId, stateCallback, handler);
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


    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            CameraUtils.this.cameraDevice = cameraDevice;
            createPreviewSession();

        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            CameraUtils.this.cameraDevice.close();
            CameraUtils.this.cameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            CameraUtils.this.cameraDevice.close();
            CameraUtils.this.cameraDevice = null;
        }
    };

    //Better place to setting this methos is onstop
    public void closeCamera(){
        if(cameraCaptureSession != null){
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }
        if(cameraDevice != null){
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    public void closeBackgroundThread(){
        if(handlerThread != null){
            handlerThread.quitSafely();
            handlerThread = null;
        }
        if(handler != null){
            handler = null;
        }
    }

    //both methods give to ux common take a picture delay few milliseconds
    private void lock(){
        try {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                cameraCaptureSession.capture(captureRequest, null, handler);
            }else {
                // versión sdk 19
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void unlock(){
        try{
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                cameraCaptureSession.setRepeatingRequest(captureRequest, null, handler);
            }else{
                //version sdk 19
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void createPreviewSession(){
        try{
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
                surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
                Surface surface = new Surface(surfaceTexture);
                final CaptureRequest.Builder captureRequestBuild = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                captureRequestBuild.addTarget(surface);

                cameraDevice.createCaptureSession(Collections.singletonList(surface), new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                        if (cameraDevice == null) {
                            return;
                        }

                        try {
                            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                                captureRequest = captureRequestBuild.build();
                                CameraUtils.this.cameraCaptureSession = cameraCaptureSession;
                                CameraUtils.this.cameraCaptureSession.setRepeatingRequest(captureRequest, null, handler);
                            }else{
                                //api 19
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                    }
                }, handler);
            } else {
                //Api 19
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public interface ICameraListener{
        void surfaceavailable();

    }



}
