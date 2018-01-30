package mx.gigigo.core.presentation.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.gigigo.core.R;
import mx.gigigo.core.data.RestApi;
import mx.gigigo.core.data.repository.UserRepository;
import mx.gigigo.core.data.repository.transform.UserEntityToUserTransform;
import mx.gigigo.core.domain.usecase.GetListUsersUseCase;

import mx.gigigo.core.presentation.model.UserModel;
import mx.gigigo.core.presentation.model.transform.UserToUserViewModel;

import mx.gigigo.core.presentation.presenter.ListUsersPresenter;
import mx.gigigo.core.presentation.presenter.view.ListUsersView;
import mx.gigigo.core.presentation.ui.activity.DetailUserActivity;
import mx.gigigo.core.presentation.ui.adapter.ListUsersAdapter;
import mx.gigigo.core.recyclerextensions.EndlessScrollListener;
import mx.gigigo.core.recyclerextensions.ViewHolderAdapter;
import mx.gigigo.core.retrofitextensions.ServiceClient;
import mx.gigigo.core.retrofitextensions.ServiceClientFactory;
import mx.gigigo.core.spextensions.SharedPreferencesExtensions;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListUsersFragment
        extends MvpBindingFragment<ListUsersView, ListUsersPresenter>
        implements ListUsersView {
    public static final String LIST_USERS_KEY = "LISTA_USUARIOS";
    public static final String USER_KEY = "user_key";
    public static String USER = "user";

    private static final int PAGE = 1;
    private static final int PER_PAGE = 10;

    @BindView(R.id.swipe_refresh_layout_list_users)
    SwipeRefreshLayout refreshLayoutListUsers;

    @BindView(R.id.recycler_view_list_users)
    RecyclerView recyclerViewListUsers;

    private boolean isRefreshing;
    private ListUsersAdapter adapter;

    //region BaseFragment members

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_users;
    }

    @Override
    protected void onInitializeMembers() {
        adapter = new ListUsersAdapter(onItemClickListener);

        refreshLayoutListUsers.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent);
        refreshLayoutListUsers.setOnRefreshListener(refreshListener);

        presenter.getUsers(PAGE, PER_PAGE);
    }

    @Override
    protected void onInitializeUIComponents() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        recyclerViewListUsers.setLayoutManager(layoutManager);
        recyclerViewListUsers.setHasFixedSize(true);
        recyclerViewListUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewListUsers.setAdapter(adapter);
        recyclerViewListUsers.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.getUsers(page + 1, PER_PAGE);
            }

            @Override
            public void onHide() { }

            @Override
            public void onShow() { }
        });



    }

    //endregion

    //region ListUsersView members

    @Override
    public void showProgress(boolean active) {

    }

    @Override
    public void onFetchPeopleSuccess(List<UserModel> userModels) {
        Type typeOfList = new TypeToken<List<UserModel>>(){}.getType();

        boolean savedListUser = SharedPreferencesExtensions.put(LIST_USERS_KEY,
                typeOfList,
                userModels);

        UserModel userModel = userModels.get(0);

        boolean savedUser = SharedPreferencesExtensions.put(USER_KEY,
                UserModel.class,
                userModel);

        adapter.setHeaderView(recyclerViewListUsers, R.layout.template_item);
        adapter.setFooterView(recyclerViewListUsers, R.layout.template_item);

        onRefreshCompleted();
        if(adapter.isEmpty()) {
            adapter.set(userModels);
        } else {
            adapter.addRange(userModels);
        }
    }

    @Override
    public void onEmptyResult() {
        onRefreshCompleted();
        Toast.makeText(getContext(), "No se encontraron resultados.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable exception) {
        onRefreshCompleted();
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    //endregion

    //region MvpFragment members

    @Override
    protected ListUsersPresenter createPresenter() {
        RestApi api = ServiceClientFactory.createService(ServiceClient.getDefault(), RestApi.class);
        UserRepository repository = new UserRepository(getContext(),
                api,
                new UserEntityToUserTransform());

        GetListUsersUseCase getListUsersUseCase = new GetListUsersUseCase(repository,
                Schedulers.io(),
                AndroidSchedulers.mainThread());
        UserToUserViewModel userViewModelMapper = new UserToUserViewModel();
        return new ListUsersPresenter(getListUsersUseCase, userViewModelMapper);
    }

    //endregion

    private void onRefreshCompleted() {
        if(isRefreshing) {
            isRefreshing = false;
        }
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshLayoutListUsers.setRefreshing(false);
            if (!isRefreshing) {
                adapter.clear();
                isRefreshing = true;
                presenter.getUsers(PAGE, PER_PAGE);
            }
        }
    };

    private final ViewHolderAdapter.OnItemClickListener<UserModel> onItemClickListener = new ViewHolderAdapter.OnItemClickListener<UserModel>() {
        @Override
        public void onItemClick(UserModel item) {
            Intent intent = new Intent(getActivity(), DetailUserActivity.class );
            intent.putExtra(USER, item);
            startActivity(intent);
        }
    };
}
