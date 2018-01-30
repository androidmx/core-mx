package mx.gigigo.core.presentation.ui.utils;

import android.view.View;
import android.widget.EditText;

/**
 * Created by Gigio on 25/01/18.
 */

public class ValidationsUtils {

    //Empty edittext
    public static boolean isEditTextEmpty(EditText editText){
        try{
            if(!editText.getText().toString().trim().isEmpty()){
                return false;
            }else{
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }

    //validate email format
    public static boolean isValidEmailFormat(EditText editText){
        String emailPattern  = "[a-zA-Z0-0._-]+@[a-z]+\\.+[a-z]+";
        if(editText!= null) {
            try {
                if (editText.getText().toString().matches(emailPattern)) {
                    return true;
                } else {
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }

    //Valid length
    public static boolean isValidLength(EditText editText, int minLength){
        if(editText != null){
            try{
                if(editText.getText().toString().length()>0 &&
                        editText.getText().toString().length() <= minLength){
                    return true;
                }else
                    return false;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }


}
