package mx.gigigo.core.presentation.ui.fragment.detail;


import android.os.Bundle;

import com.google.auto.value.AutoValue;

import mx.gigigo.core.R;
import mx.gigigo.core.presentation.application.BaseKey;
import mx.gigigo.core.rxmvp.BaseFragment;

/**
 * @version 0.0.1
 * @autor Gigio on 31/01/18.
 * @since 0.0.1
 */
@AutoValue
public abstract class DetailKey extends BaseKey{
    public abstract  int userId();

    public static DetailKey create(int userId){
        return new AutoValue_DetailKey(userId);
    }

    @Override
    public BaseFragment createFragment() {
        return new DetailUserFragment();
    }
}
