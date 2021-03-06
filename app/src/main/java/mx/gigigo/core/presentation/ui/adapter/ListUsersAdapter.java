package mx.gigigo.core.presentation.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import mx.gigigo.core.R;
import mx.gigigo.core.presentation.model.UserModel;
import mx.gigigo.core.recyclerextensions.RecyclerHeaderFooterAdapter;
import mx.gigigo.core.recyclerextensions.ViewHolderAdapter;

/**
 * @author JG - January 05, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class ListUsersAdapter
        extends RecyclerHeaderFooterAdapter<UserModel, ViewHolderAdapter<UserModel>> {

    private ViewHolderAdapter.OnItemClickListener<UserModel> listener;

    public ListUsersAdapter(ViewHolderAdapter.OnItemClickListener<UserModel> listener){
        this.listener = listener;
    }

    @Override
    public ViewHolderAdapter<UserModel> onCreateViewHolderHeaderFooter(ViewGroup parent, int viewType) {
        View view = getView(parent, R.layout.item_user);
        ListUsersViewHolder viewHolder = new ListUsersViewHolder(view);
        viewHolder.setItemClickListener(listener);
        return viewHolder;
    }
}
