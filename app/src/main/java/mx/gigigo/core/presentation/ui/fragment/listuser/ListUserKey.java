package mx.gigigo.core.presentation.ui.fragment.listuser;

import android.os.Parcel;

import com.google.auto.value.AutoValue;

import mx.gigigo.core.presentation.application.BaseKey;
import mx.gigigo.core.rxmvp.BaseFragment;

/**
 * @version 0.0.1
 * @autor Gigio on 08/02/18.
 * @since 0.0.1
 */
@AutoValue
public abstract class ListUserKey extends BaseKey {

    public static ListUserKey create(){
        return new AutoValue_ListUserKey();
    }

    @Override
    public BaseFragment createFragment() {
        return new ListUsersFragment();
    }

}
