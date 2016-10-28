package xyz.chaisong.newsexp.base;

import android.webkit.WebView;

import xyz.chaisong.lib.LibConfig;
import xyz.chaisong.lib.app.BaseApplication;
import xyz.chaisong.newsexp.BuildConfig;

/**
 * Created by song on 16/10/27.
 */

public class UIApplication extends BaseApplication {

    @Override
    public void onCreate() {
        LibConfig.init(BuildConfig.DEBUG, this);
        super.onCreate();
        //debug模式下一些调试初始化
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
    }
}
