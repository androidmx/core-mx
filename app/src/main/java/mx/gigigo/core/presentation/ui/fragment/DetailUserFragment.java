package mx.gigigo.core.presentation.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.gigigo.core.R;
import mx.gigigo.core.data.RestApi;
import mx.gigigo.core.data.repository.UserRepository;
import mx.gigigo.core.data.repository.mapper.UserEntityToUserTransform;
import mx.gigigo.core.domain.usecase.GetDetailUserUseCase;
import mx.gigigo.core.presentation.model.UserViewModel;
import mx.gigigo.core.presentation.model.mapper.UserToUserViewModel;
import mx.gigigo.core.presentation.presenter.DetailUserPresenter;
import mx.gigigo.core.presentation.presenter.view.DetailUserView;
import mx.gigigo.core.presentation.ui.activity.CameraActivity;
import mx.gigigo.core.presentation.ui.activity.DetailUserActivity;
import mx.gigigo.core.presentation.ui.utils.CameraUtils;
import mx.gigigo.core.retrofitextensions.ServiceClient;
import mx.gigigo.core.retrofitextensions.ServiceClientFactory;
import mx.gigigo.core.rxmvp.MvpFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DetailUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailUserFragment extends MvpBindingFragment<DetailUserView, DetailUserPresenter>
        implements DetailUserView {
    public static String USER_ID = "user_id";

    @BindView(R.id.image_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_last_name)
    TextView tvLastName;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;

    private CameraUtils cameraUtils;

    private int idUser;

    public static DetailUserFragment newInstance(int param1) {
        DetailUserFragment fragment = new DetailUserFragment();
        Bundle args = new Bundle();
        args.putInt(USER_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected DetailUserPresenter createPresenter() {
        RestApi api = ServiceClientFactory.createService(ServiceClient.getDefault(), RestApi.class);
        UserRepository userRepository = new UserRepository(api, new UserEntityToUserTransform());

        GetDetailUserUseCase userUseCase = new GetDetailUserUseCase(userRepository, Schedulers.io(),
                AndroidSchedulers.mainThread());
        UserToUserViewModel userToUserViewModel =  new UserToUserViewModel();
        DetailUserPresenter presenter = new DetailUserPresenter(userUseCase, userToUserViewModel);

        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_user;
    }

    @Override
    protected void onInitializeUIComponents() {
    }

    @Override
    protected void onInitializeMembers() {
        presenter.getUserDetail(idUser);

    }

    @Override
    protected void onRestoreExtras(Bundle arguments) {
        super.onRestoreExtras(arguments);

        idUser = arguments.getInt(USER_ID);

    }


    @Override
    public void onSuccessUserDetail(UserViewModel userViewModel) {
        Glide.with(getContext()).load(userViewModel.getAvatar())
                .into(ivAvatar);
        tvName.setText(userViewModel.getName());
        tvLastName.setText(userViewModel.getLastName());
    }

    @OnClick(R.id.iv_camera)
    public void onClickAction(){
        if(checkPermission()) {
            Intent intent = new Intent(getActivity(), CameraActivity.class);
            startActivity(intent);
        }else{
            //Request permissions
            ((DetailUserActivity)getActivity()).checkPermissions();
        }
    }

    @Override
    public void onSuccessUserUpdate(UserViewModel userViewModel) {

    }

    @Override
    public void onEmptyResult() {

    }

    @Override
    public void showProgress(boolean active) {

    }

    @Override
    public void showError(Throwable exception) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public boolean checkPermission(){
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            return false;
        }else{
            return true;
        }
    }
}
