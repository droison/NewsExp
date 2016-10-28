package xyz.chaisong.lib.baserecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xyz.chaisong.lib.app.lifecycle.LifeCycleComponent;

/**
 * Created by song on 16/8/16.
 */
public class LinearRecyclerView<T extends Model.ItemData, VH extends Model.BaseViewHolder<T>> extends BaseRecyclerView implements IDataHandler<T>, LifeCycleComponent, View.OnClickListener, View.OnLongClickListener, Model.OnClickDelegate {
    private static final int MIN_INTERVAL_CLICK_TIME = 100;

    private ResourceList<T> mDataResources = new ResourceList<>();

    private IViewHolderGenerator<T, VH> mGenerator;

    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnItemLongClickListener;
    private OnItemSubViewClickListener<T> mOnItemSubViewClickListener;

    protected ArrayList<VH> mVisibleItem = new ArrayList<>();

    private long mLastClickTime;

    private int mLastBindPosition; //统计需要,看到的最后一个位置

    protected Adapter<VH> mAdapter = new Adapter<VH>() {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            VH viewHolder = mGenerator.buildViewHolder(parent, viewType);
            viewHolder.setOnClickDelegate(LinearRecyclerView.this);
            if (null != mOnItemClickListener) {
                viewHolder.itemView.setOnClickListener(LinearRecyclerView.this);
            }
            if (null != mOnItemLongClickListener) {
                viewHolder.itemView.setOnLongClickListener(LinearRecyclerView.this);
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.bindData(mDataResources.get(position));
            mLastBindPosition = Math.max(mLastBindPosition, position);
        }

        @Override
        public int getItemViewType(int position) {
            return mGenerator.getItemType(mDataResources.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataResources.size();
        }

        @Override
        public void onViewAttachedToWindow(VH holder) {
            mVisibleItem.add(holder);
            holder.onAttachedToWindow();
        }

        @Override
        public void onViewDetachedFromWindow(VH holder) {
            mVisibleItem.remove(holder);
            holder.onDetachedFromWindow();
        }
    };


    public LinearRecyclerView(Context context) {
        this(context, null);
    }

    public LinearRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        setAdapter(mAdapter);
    }

    public ResourceList<T> getDataResources() {
        return mDataResources;
    }

    public IViewHolderGenerator<T, VH> getGenerator() {
        return mGenerator;
    }

    public void setGenerator(IViewHolderGenerator<T, VH> mGenerator) {
        this.mGenerator = mGenerator;
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        this.mOnItemLongClickListener = listener;
    }

    public void setOnItemSubViewClickListener(OnItemSubViewClickListener<T> mOnItemSubViewClickListener) {
        this.mOnItemSubViewClickListener = mOnItemSubViewClickListener;
    }

    public int getLastBindPosition() {
        return mLastBindPosition;
    }

    /********************** IDataHandler ****************/
    @Override
    public void add(@NonNull T model) {
        int curCount = mDataResources.size();
        mDataResources.add(model);
        mAdapter.notifyItemRangeInserted(curCount, 1);
    }

    @Override
    public void add(@NonNull T model, int index) {
        if (index < 0)
            return;

        int curCount = mDataResources.size();
        if (curCount >= index) {
            mDataResources.add(index, model);
            mAdapter.notifyItemRangeInserted(index, 1);
        } else {
            mDataResources.add(model);
            mAdapter.notifyItemRangeInserted(curCount, 1);
        }
    }

    @Override
    public void add(@NonNull List<T> models) {
        if (models.size() == 0)
            return;

        int curCount = mDataResources.size();
        mDataResources.addAll(models);
        mAdapter.notifyItemRangeInserted(curCount, models.size());
    }

    @Override
    public void add(@NonNull List<T> models, int index) {
        if (index < 0 || models.size() == 0)
            return;

        int curCount = mDataResources.size();
        if (curCount >= index) {
            mDataResources.addAll(index, models);
            mAdapter.notifyItemRangeInserted(index, models.size());
        } else {
            mDataResources.addAll(models);
            mAdapter.notifyItemRangeInserted(curCount, models.size());
        }
    }

    @Override
    public void remove(int index) {
        int curCount = mDataResources.size();
        if (curCount > index) {
            mDataResources.remove(index);
            mAdapter.notifyItemRemoved(index);
        }
    }

    @Override
    public void remove(int index, int count) {
        int curCount = mDataResources.size();
        if (curCount > index) {
            if (count + index > curCount) {
                count = curCount - index;
            }
            mDataResources.removeRange(index, count);
            mAdapter.notifyItemRangeRemoved(index, count);
        }
    }

    @Override
    public void remove(@NonNull T model) {
        int position = -1;
        for (int i = 0; i < mDataResources.size(); i++) {
            if (model == mDataResources.get(i)) {
                position = i;
                break;
            }
        }
        if (position != -1) {
            mDataResources.remove(position);
            mAdapter.notifyItemRemoved(position);
        }
    }

    @Override
    public void setDatas(List<T> models) {
        mDataResources.clear();
        mDataResources.addAll(models);
        mAdapter.notifyDataSetChanged();
    }

    protected List<T> getDatas() {
        return mDataResources;
    }

    @Override
    public void clear() {
        mDataResources.clear();
        mAdapter.notifyDataSetChanged();
    }

    /********************** LifeCycleComponent ****************/

    /**
     * like {@link android.app.Activity#onResume}
     */
    @Override
    public void onBecomeVisible() {
        for (LifeCycleComponent component : mVisibleItem) {
            component.onBecomeVisible();
        }
    }

    /**
     * like {@link android.app.Activity#onStop}
     */
    @Override
    public void onBecomeInvisible() {
        for (LifeCycleComponent component : mVisibleItem) {
            component.onBecomeInvisible();
        }
    }

    /**
     * like {@link android.app.Activity#onDestroy}
     */
    @Override
    public void onDestroy() {
        for (LifeCycleComponent component : mVisibleItem) {
            component.onDestroy();
        }
    }

    /********************** Custom Class ****************/
    public static class ResourceList<E> extends ArrayList<E> {
        @Override
        public void removeRange(int fromIndex, int toIndex) {
            super.removeRange(fromIndex, toIndex);
        }
    }

    @Override
    public void onClick(View v) {
        long curTime = System.currentTimeMillis();
        if (null != mOnItemClickListener && curTime - mLastClickTime > MIN_INTERVAL_CLICK_TIME) {
            mLastClickTime = curTime;
            int posi = this.getChildAdapterPosition(v) - getHeaderViewsCount();
            mOnItemClickListener.onItemClick(this, v, posi, mDataResources.get(posi));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        long curTime = System.currentTimeMillis();
        if (null != mOnItemClickListener && curTime - mLastClickTime > MIN_INTERVAL_CLICK_TIME) {
            mLastClickTime = curTime;
            int posi = this.getChildAdapterPosition(v) - getHeaderViewsCount();
            return mOnItemLongClickListener.onItemLongClick(this, v, posi, mDataResources.get(posi));
        }
        return false;
    }

    @Override
    public void onClick(Model.BaseViewHolder viewHolder, View view, int type, Object param) {
        if (type == 0)
        {
            onClick(viewHolder.itemView);
        } else if (mOnItemSubViewClickListener != null) {
            int posi = viewHolder.getLayoutPosition() - getHeaderViewsCount();
            mOnItemSubViewClickListener.onItemSubViewClick(view, posi, mDataResources.get(posi), type, param);
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(LinearRecyclerView recyclerView, View view, int position, T model);
    }

    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(LinearRecyclerView recyclerView, View view, int position, T model);
    }

    public interface OnItemSubViewClickListener<T> {
        void onItemSubViewClick(View view, int position, T model, int type, Object param);
    }
}
