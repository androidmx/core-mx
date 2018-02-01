package mx.gigigo.core.presentation.ui.fragment;

import android.os.Parcel;

import com.google.auto.value.AutoValue;

import mx.gigigo.core.R;
import mx.gigigo.core.rxmvp.BaseFragment;

/**
 * @version 0.0.1
 * @autor Gigio on 31/01/18.
 * @since 0.0.1
 */

public class DetailKey extends BaseKey {

    public static DetailKey create(){
        return new AutoValue_DetailKey(R.layout.fragment_detail_user);
    }


    @Override
    protected BaseFragment createFragment() {
        return DetailUserFragment.newInstance(2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
