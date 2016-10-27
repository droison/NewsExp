package xyz.chaisong.newsexp.base;

import android.app.Application;
import android.webkit.WebView;

import xyz.chaisong.mmanagercenter.MManagerCenter;
import xyz.chaisong.mmbus.MMBus;
import xyz.chaisong.newsexp.BuildConfig;
import xyz.chaisong.newsexp.base.lifecycle.ActivityLifecycleMgr;
import xyz.chaisong.newsexp.util.LocalDisplay;

import static xyz.chaisong.mmanagercenter.MManagerCenter.getManager;

/**
 * Created by song on 16/10/27.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LocalDisplay.init(this);
        //单例管理器初始化
        MManagerCenter.configDebug(BuildConfig.DEBUG);
        MManagerCenter.init(this);

        //LifecycleCallback监测初始化
        getManager(ActivityLifecycleMgr.class);

        //通知管理器初始化
        MMBus.isDebugMode = BuildConfig.DEBUG;

        //debug模式下一些调试初始化
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
    }
}
