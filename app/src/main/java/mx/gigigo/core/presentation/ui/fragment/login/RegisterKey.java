package mx.gigigo.core.presentation.ui.fragment.login;

import com.google.auto.value.AutoValue;

import mx.gigigo.core.presentation.application.BaseKey;
import mx.gigigo.core.rxmvp.BaseFragment;

/**
 * @version 0.0.1
 * @autor Gigio on 08/02/18.
 * @since 0.0.1
 */
@AutoValue
public  abstract class RegisterKey extends BaseKey {
    public abstract  int type();

    public static RegisterKey create(int type){
        return new AutoValue_RegisterKey(type);
    }

    @Override
    public BaseFragment createFragment() {
        return new RegisterFragment();
    }
}
