package mx.gigigo.core.recyclerextensions;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author AG - January 15, 2018
 * @author JG - January 15, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class RecyclerHeaderFooterAdapter<T, H extends ViewHolderAdapter<T>>
        extends RecyclerAdapter<T> {

    private static final int TYPE_HEADER = Integer.MAX_VALUE;
    private static final int TYPE_FOOTER = Integer.MAX_VALUE - 1;
    private static final int ITEM_MAX_TYPE = Integer.MAX_VALUE - 2;

    private ViewHolderAdapter<T> headerViewHolder;
    private ViewHolderAdapter<T> footerViewHolder;

    public void setHeaderView(RecyclerView parent, @LayoutRes int resourceId){
        this.setHeaderView(getView(parent, resourceId));
    }

    public void setFooterView(RecyclerView parent, @LayoutRes int resourceId){
        this.setFooterView(getView(parent, resourceId));
    }

    public void setHeaderView(View header){
        if (headerViewHolder == null || header != headerViewHolder.itemView) {
            headerViewHolder = new ViewHolderAdapter<>(header);
            notifyDataSetChanged();
        }
    }

    public void setFooterView(View foot){
        if (footerViewHolder == null || foot != footerViewHolder.itemView) {
            footerViewHolder = new ViewHolderAdapter<>(foot);
            notifyDataSetChanged();
        }
    }

    public void removeHeader() {
        if (headerViewHolder != null){
            headerViewHolder = null;
            notifyDataSetChanged();
        }
    }

    public void removeFooter() {
        if (footerViewHolder != null){
            footerViewHolder = null;
            notifyDataSetChanged();
        }
    }

    private boolean isHeader(int position){
        return hasHeader() && position == 0;
    }

    private boolean isFooter(int position){
        return hasFooter() && position == getDataItemCount() + (hasHeader() ? 1 : 0);
    }

    private int itemPositionInData(int rvPosition){
        return rvPosition - (hasHeader() ? 1 : 0);
    }

    private int itemPositionInRV(int dataPosition){
        return dataPosition + (hasHeader() ? 1 : 0);
    }

    public void onItemInserted(int itemPosition) {
        notifyItemInserted(itemPositionInRV(itemPosition));
    }

    public void onItemRemoved(int itemPosition) {
        notifyItemRemoved(itemPositionInRV(itemPosition));
    }

    public void onItemChanged(int itemPosition) {
        notifyItemChanged(itemPositionInRV(itemPosition));
    }

    @Override
    public int getItemCount() {
        int itemCount = getDataItemCount();
        if (hasHeader()) {
            itemCount += 1;
        }
        if (hasFooter()) {
            itemCount += 1;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return TYPE_HEADER;
        }
        if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        int dataItemType = getDataItemType(itemPositionInData(position));
        if (dataItemType > ITEM_MAX_TYPE) {
            throw new IllegalStateException("getDataItemType() must be less than " + ITEM_MAX_TYPE + ".");
        }
        return dataItemType;
    }

    @Override
    public void clear() {
        if (headerViewHolder != null){
            headerViewHolder = null;
        }

        if (footerViewHolder != null){
            footerViewHolder = null;
        }
        super.clear();
    }

    public int getDataItemCount() {
        return super.getItemCount();
    }

    public int getDataItemType(int position){
        return 0;
    }

    public boolean hasHeader(){
        return headerViewHolder != null;
    }

    public boolean hasFooter(){
        return footerViewHolder != null;
    }

    @Override
    public ViewHolderAdapter<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return headerViewHolder;
        } else if (viewType == TYPE_FOOTER) {
            return footerViewHolder;
        }

        return onCreateViewHolderHeaderFooter(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolderAdapter<T> holder, int position) {
        if (!isEmpty() && !isHeader(position) && !isFooter(position)) {
            holder.onBindViewHolder(items().get(itemPositionInData(position)));
        }
    }

    public abstract H onCreateViewHolderHeaderFooter(ViewGroup parent, int viewType);
}