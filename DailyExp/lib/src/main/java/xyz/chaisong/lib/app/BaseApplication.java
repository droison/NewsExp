package xyz.chaisong.lib.app;

import android.app.Application;

import xyz.chaisong.lib.LibConfig;
import xyz.chaisong.lib.app.lifecycle.ActivityLifecycleMgr;
import xyz.chaisong.lib.util.LocalDisplay;
import xyz.chaisong.mmanagercenter.MManagerCenter;
import xyz.chaisong.mmbus.MMBus;

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
        MManagerCenter.configDebug(LibConfig.DEBUG);
        MManagerCenter.init(this);

        //LifecycleCallback监测初始化
        getManager(ActivityLifecycleMgr.class);

        //通知管理器初始化
        MMBus.isDebugMode = LibConfig.DEBUG;
    }
}
