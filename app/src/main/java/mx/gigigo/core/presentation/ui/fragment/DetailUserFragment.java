package mx.gigigo.core.presentation.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.gigigo.core.R;
import mx.gigigo.core.data.RestApi;
import mx.gigigo.core.data.repository.UserRepository;
import mx.gigigo.core.data.repository.transform.UserEntityToUserTransform;
import mx.gigigo.core.domain.usecase.GetDetailUserUseCase;
import mx.gigigo.core.domain.usecase.UpdateUserCase;
import mx.gigigo.core.presentation.model.UserModel;
import mx.gigigo.core.presentation.model.transform.UserToUserViewModel;
import mx.gigigo.core.presentation.presenter.DetailUserPresenter;
import mx.gigigo.core.presentation.presenter.view.DetailUserView;
import mx.gigigo.core.presentation.ui.activity.CameraActivity;
import mx.gigigo.core.presentation.ui.activity.DetailUserActivity;
import mx.gigigo.core.retrofitextensions.ServiceClient;
import mx.gigigo.core.retrofitextensions.ServiceClientFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DetailUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailUserFragment extends MvpBindingFragment<DetailUserView, DetailUserPresenter>
        implements DetailUserView {
    public final static int CODE_RESULT_CAMERA = 109;
    public final static String USER_ID = "user_id";
    public final static String USER = "user";
    public final static String FILE_IMAGE = "image";

    @BindView(R.id.image_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.tv_last_name)
    EditText tvLastName;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.bt_save)
    Button save;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private UserModel userViewModel;


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
        UserRepository userRepository = new UserRepository(getContext(),
                api,
                new UserEntityToUserTransform());

        GetDetailUserUseCase userUseCase = new GetDetailUserUseCase(userRepository, Schedulers.io(),
                AndroidSchedulers.mainThread());
        UpdateUserCase updateUserCase = new UpdateUserCase(userRepository, Schedulers.io(),
                AndroidSchedulers.mainThread());
        UserToUserViewModel userToUserViewModel =  new UserToUserViewModel();
        return new DetailUserPresenter(userUseCase, userToUserViewModel, updateUserCase);
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
    public void onSuccessUserDetail(UserModel userViewModel) {
        Glide.with(getContext()).load(userViewModel.getAvatar())
                .into(ivAvatar);
        tvName.setText(userViewModel.getName());
        tvLastName.setText(userViewModel.getLastName());
        this.userViewModel = userViewModel;

    }

    @OnClick({R.id.iv_camera, R.id.bt_save})
    public void onClickAction(View view){
        if(view.getId() == R.id.iv_camera) {
            if (checkPermission()) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                intent.putExtra(USER, userViewModel);
                startActivityForResult(intent, CODE_RESULT_CAMERA);
            } else {
                //Request permissions
                ((DetailUserActivity) getActivity()).checkPermissions();
            }
        }else{
            if(userViewModel != null){
                if(!tvName.getText().toString().isEmpty())
                    userViewModel.setName(tvName.getText().toString());
                if(!tvLastName.getText().toString().isEmpty())
                    userViewModel.setLastName(tvLastName.getText().toString());
                presenter.getUserUpdate(userViewModel);

            }
        }
    }

    @Override
    public void onSuccessUserUpdate() {
        Toast.makeText(getContext(), getResources().getString(R.string.message_success_update),Toast.LENGTH_LONG).show();
        getActivity().finish();

    }

    @Override
    public void onEmptyResult() {

    }

    @Override
    public void showProgress(boolean active) {
        if(active) {
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(Throwable exception) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == CODE_RESULT_CAMERA){
            String filePath = data.getExtras().getString(FILE_IMAGE, "");
            File file = new File(filePath);
            if(file.exists()) {
                Glide.with(getContext()).load(Uri.fromFile(file)).into(ivAvatar);
            }

        }
    }

    public boolean checkPermission(){
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
