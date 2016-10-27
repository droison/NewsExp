package xyz.chaisong.newsexp.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.chaisong.newsexp.base.lifecycle.IComponentContainer;
import xyz.chaisong.newsexp.base.lifecycle.LifeCycleComponent;
import xyz.chaisong.newsexp.base.lifecycle.LifeCycleComponentManager;

/**
 * Created by song on 16/10/27.
 */

public abstract class BaseFragment extends Fragment implements IComponentContainer{
    private View contentView = null;

    protected final String TAG = getClass().getSimpleName();

    private LifeCycleComponentManager mComponentContainer = new LifeCycleComponentManager();

    protected abstract int getLayoutId();

    protected void initDatas() {}

    protected abstract void initViews(View view);

    //template method
    protected void busReigister(){ }

    //template method
    protected void busUnRegister(){ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == contentView) {
            contentView = inflater.inflate(getLayoutId(), container, false);
            initViews(contentView);
        }
        busReigister();
        return contentView;
    }

    @Override
    public void onDestroyView() {
        ((ViewGroup) contentView.getParent()).removeView(contentView);
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mComponentContainer.onBecomeVisible();
    }

    @Override
    public void onStop() {
        super.onStop();
        mComponentContainer.onBecomeInvisible();
    }

    @Override
    public void onDestroy() {
        busUnRegister();
        super.onDestroy();
        mComponentContainer.onDestroy();
    }

    /**
     * ===========================================================
     * Implements {@link IComponentContainer}
     * ===========================================================
     */
    @Override
    public void addComponent(LifeCycleComponent component) {
        mComponentContainer.addComponent(component);
    }
}
