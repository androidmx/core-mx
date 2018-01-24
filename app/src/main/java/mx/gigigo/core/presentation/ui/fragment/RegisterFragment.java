package mx.gigigo.core.presentation.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.gigigo.core.R;
import mx.gigigo.core.presentation.presenter.RegisterUserPresenter;
import mx.gigigo.core.presentation.presenter.view.RegisterUserView;
import mx.gigigo.core.rxmvp.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends MvpBindingFragment<RegisterUserView, RegisterUserPresenter> {


    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();;
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onInitializeUIComponents() {

    }

    @Override
    protected void onInitializeMembers() {

    }

    @Override
    protected void onBindView(View root) {

    }

    @Override
    protected void onUnbindView() {

    }


    @Override
    protected RegisterUserPresenter createPresenter() {
        return ;
    }
}
