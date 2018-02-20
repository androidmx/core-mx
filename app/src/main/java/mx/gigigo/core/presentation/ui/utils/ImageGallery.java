package mx.gigigo.core.presentation.ui.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.view.TextureView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Gigio on 22/01/18.
 */

public class ImageGallery {
    public static String DATE_FORMAT = "yyyyMMdd_HHmmss";
    public static int QUALITY_PHOTO_COMPRESS = 100;

    private int qualityCompressIamge;
    private String formatImage;
    private String nameSubdirectory;
    private String nameFile;
    private File galleryFolder;
    private File fileTemp;

    public int getQualityCompressIamge() {
        return qualityCompressIamge;
    }

    public void setQualityCompressIamge(int qualityCompressIamge) {
        this.qualityCompressIamge = qualityCompressIamge;
    }

    public String getFormatImage() {
        return formatImage == null ? ".jpg" : formatImage;
    }

    public void setFormatImage(String formatImage) {
        this.formatImage = formatImage;
    }

    public String getNameSubdirectory() {
        return nameSubdirectory == null ? "photos" : nameSubdirectory;
    }

    public void setNameSubdirectory(String nameSubdirectory) {
        this.nameSubdirectory = nameSubdirectory;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public File getGalleryFolder() {
        return galleryFolder;
    }

    public File getFileTemp() {
        return fileTemp;
    }

    public void setFileTemp(File fileTemp) {
        this.fileTemp = fileTemp;
    }

    public void setGalleryFolder(File galleryFolder) {
        this.galleryFolder = galleryFolder;
    }

    public File createimageGallery(){
        //With external memory
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        galleryFolder = new File(storageDirectory,  getNameSubdirectory());
        if(!galleryFolder.exists()){
            if(!galleryFolder.mkdirs()){
                File mediastorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
                if(!mediastorageDir.exists()){
                    if(!mediastorageDir.mkdirs()){
                        return  null;
                    }else{
                        galleryFolder = mediastorageDir;
                    }
                }
            }
        }
        return galleryFolder;
    }

    private File createImageFile(File galleryFolder) throws IOException{
        String timeStamp = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(new Date());
        nameFile ="image_" + timeStamp + "_" ;
        fileTemp = File.createTempFile(nameFile, getFormatImage() , galleryFolder);
        return fileTemp;
    }


    public void saveFotoOnGallery(TextureView textureView){
        FileOutputStream outputStream = null;
        try{
          outputStream = new FileOutputStream( createImageFile(createimageGallery()));
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
