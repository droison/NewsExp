package xyz.chaisong.newsexp.feed;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.chaisong.newsexp.R;
import xyz.chaisong.newsexp.base.BaseFragment;

/**
 * Created by song on 16/10/27.
 */

public class FeedBaseFragment extends BaseFragment {

    @Bind(R.id.textview)
    TextView textView;

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
        textView.setText("text");
    }
}
