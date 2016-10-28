package xyz.chaisong.lib.baserecyclerview;

import android.view.ViewGroup;

/**
 * Created by song on 16/8/16.
 */
public interface IViewHolderGenerator<T extends Model.ItemData, VH extends Model.BaseViewHolder<T>> {
    VH buildViewHolder(ViewGroup parent, int itemType);
    int getItemType(T baseData);
}
