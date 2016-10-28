package xyz.chaisong.lib;

import android.content.Context;

import xyz.chaisong.lib.network.NetUtil;
import xyz.chaisong.lib.util.LocalDisplay;
import xyz.chaisong.lib.util.ToastUtil;

/**
 * Created by song on 16/7/7.
 */
public class LibConfig {
    public static boolean DEBUG = false;
    public static Context ApplicationContext;
    public static void init(boolean debug, Context applicationContext) {
        DEBUG = debug;
        ApplicationContext = applicationContext;
        NetUtil.init(applicationContext);
        LocalDisplay.init(applicationContext);

        ToastUtil.defaultContext = applicationContext;
    }
}
