package mx.gigigo.core.presentation.application;

import android.app.Fragment;
import android.os.Parcelable;

import mx.gigigo.core.rxmvp.BaseFragment;

/**
 * @version 0.0.1
 * @autor Gigio on 01/02/18.
 * @since 0.0.1
 */

public interface Key extends Parcelable {
    BaseFragment createFragment();
    String getFragmentTag();
}
