package xyz.chaisong.newsexp.feed;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.chaisong.lib.app.BaseFragment;
import xyz.chaisong.newsexp.R;
import xyz.chaisong.lib.baserecyclerview.LinearRecyclerView;

/**
 * Created by song on 16/10/27.
 */

public class FeedBaseFragment extends BaseFragment implements FeedBaseContract.View{

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.feedView)
    LinearRecyclerView feedView;

    FeedBaseContract.Presenter mPresenter;

    @Override
    protected void initDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fg_feedbase;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void setPresenter(FeedBaseContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public boolean isViewDestroyed() {
        return feedView == null || getActivity().isDestroyed();
    }
}
