package xyz.chaisong.newsexp.views.baserecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import xyz.chaisong.newsexp.base.lifecycle.LifeCycleComponent;

/**
 * Created by song on 16/8/16.
 */

public class Model {
    public abstract static class BaseViewHolder<T extends ItemData> extends RecyclerView.ViewHolder implements LifeCycleComponent {

        protected Context mContext;
        protected OnClickDelegate onClickDelegate;

        public BaseViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
        }

        public void setOnClickDelegate(OnClickDelegate onClickDelegate) {
            this.onClickDelegate = onClickDelegate;
        }

        public abstract void bindData(T data);

        public void onAttachedToWindow() { }

        public void onDetachedFromWindow() { }

        /********************** LifeCycleComponent ****************/

        /**
         * like {@link android.app.Activity#onResume}
         */
        @Override
        public void onBecomeVisible() {

        }

        /**
         * like {@link android.app.Activity#onStop}
         */
        @Override
        public void onBecomeInvisible() {

        }

        /**
         * like {@link android.app.Activity#onDestroy}
         */
        @Override
        public void onDestroy() {

        }
    }

    public static abstract class ItemData {

    }

    public interface OnClickDelegate {
        /**
         *
         * @param viewHolder 事件所属的viewHolder
         * @param view 事件所属的view
         * @param type 为标记当前事件由那个view发起,调用方自行设计,0表示itemView
         * @param param 事件传参
         */
        void onClick(BaseViewHolder viewHolder, View view, int type, Object param);
    }
}
