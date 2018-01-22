package mx.gigigo.core.presentation.ui.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.TextureView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mx.gigigo.core.presentation.ui.RootApp;

/**
 * Created by Gigio on 22/01/18.
 */

public class ImageGallery {
    public static String DATE_FORMAT = "yyyyMMdd_HHmmss";
    public static int QUALITY_PHOTO_COMPRESS = 100;


    private int qualityCompressIamge;

    public int getQualityCompressIamge() {
        return qualityCompressIamge;
    }

    public void setQualityCompressIamge(int qualityCompressIamge) {
        this.qualityCompressIamge = qualityCompressIamge;
    }

    public File createimageGallery(){
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File galleryFolder = new File(storageDirectory, "People" );
        if(!galleryFolder.exists()){
            boolean wasCreated = galleryFolder.mkdirs();
            if(!wasCreated){
                Log.e("", "");
            }
        }
        return galleryFolder;
    }

    private File createImageFileJPG(File galleryFolder) throws IOException{
        String timeStamp = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(new Date());
        String imageFileName = "image_" + timeStamp + "_";
        return File.createTempFile(imageFileName, ".jpg", galleryFolder);
    }


    private void saveFotoOnGallery(TextureView textureView){
        FileOutputStream outputStream = null;
        try{
          outputStream = new FileOutputStream( createImageFileJPG(createimageGallery()));
          textureView.getBitmap().compress(Bitmap.CompressFormat.PNG,
                  qualityCompressIamge == 0 ? QUALITY_PHOTO_COMPRESS : qualityCompressIamge, outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(outputStream != null){
                    outputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
