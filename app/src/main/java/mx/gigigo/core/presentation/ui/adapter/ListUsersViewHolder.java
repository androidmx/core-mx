package mx.gigigo.core.presentation.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.gigigo.core.R;
import mx.gigigo.core.presentation.viewmodel.UserViewModel;
import mx.gigigo.core.recyclerextensions.ViewHolderAdapter;

/**
 * @author JG - January 05, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class ListUsersViewHolder
        extends ViewHolderAdapter<UserViewModel> {

    @BindView(R.id.image_view_avatar)
    ImageView imageViewAvatar;

    @BindView(R.id.text_view_name)
    TextView textViewName;

    public ListUsersViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(UserViewModel item) {
        super.onBindViewHolder(item);

        textViewName.setText(item.getName());

        Glide.with(getContext())
                .load(item.getAvatar())
                .into(imageViewAvatar);
    }
}
