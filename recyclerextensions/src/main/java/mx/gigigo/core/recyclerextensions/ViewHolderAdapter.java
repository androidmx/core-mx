package mx.gigigo.core.recyclerextensions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author JG - December 29, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public class ViewHolderAdapter<T>
        extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener  {

    private T item;
    private Context context;

    protected ViewHolderAdapter.OnItemClickListener<T> itemClickListener;
    protected ViewHolderAdapter.OnItemLongClickListener<T> itemLongClickListener;

    public ViewHolderAdapter(View itemView) {
        super(itemView);
        this.context = itemView.getContext();

        itemView.setOnClickListener(this);
    }

    public void onBindViewHolder(T item) {
        if(null == item) {
            throw new NullPointerException("Item must not be null");
        } else {
            this.item = item;
        }
    }

    public T getItem() {
        return this.item;
    }

    public Context getContext() {
        return this.context;
    }
    
    public void setItemClickListener(ViewHolderAdapter.OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(ViewHolderAdapter.OnItemLongClickListener<T> itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    @Override
    public void onClick(View v) {
        if(null != itemClickListener) {
            itemClickListener.onItemClick(getItem());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(null == itemLongClickListener) return false;

        return itemLongClickListener.onItemLongClick(getItem());
    }


    public interface OnItemClickListener<T> {
        void onItemClick(T item);
    }

    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(T item);
    }
}
