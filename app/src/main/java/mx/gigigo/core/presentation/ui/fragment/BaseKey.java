package mx.gigigo.core.presentation.ui.fragment;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import mx.gigigo.core.rxmvp.BaseFragment;

/**
 * @version 0.0.1
 * @autor Gigio on 31/01/18.
 * @since 0.0.1
 */

public abstract  class BaseKey implements Key{
    public String getFragmentTag(){
        return toString();
    }

    public final BaseFragment newFragment(){
        BaseFragment fragment = createFragment();
        Bundle bundle = fragment.getArguments();
        if(bundle == null){
            bundle = new Bundle();
        }
        bundle.putParcelable("KEY", this);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected abstract BaseFragment createFragment();

}
