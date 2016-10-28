package xyz.chaisong.lib.log;

import android.text.TextUtils;

import xyz.chaisong.lib.LibConfig;

/**
 * Created by song on 15/5/11.
 */
public class CLog {

    private static String TAG = "xyz.chaisong.lib";

    public static CLogCallBack callBack;

    //info日志，非debug不打印，回调到callback
    public static void time(String tag) {
        i(tag, "current second time:" + System.currentTimeMillis());
    }

    public static void i(String tag, String msg, Object... args) {
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        i(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (LibConfig.DEBUG) android.util.Log.i(tag, msg);
        if (callBack != null) {
            callBack.info(tag, msg);
        }
    }

    //debug日志、json日志  仅在debug下打印，不回调
    public static void d(String message) {
        if (LibConfig.DEBUG) android.util.Log.d(TAG, message);
    }

    public static void d(String tag, String message) {
        if (LibConfig.DEBUG)
            android.util.Log.d(tag, message);
    }

    public static void json(String message) {
        if (LibConfig.DEBUG)
            Logger.json(message);
    }

    public static void json(String tag, String message) {
        if (LibConfig.DEBUG)
            Logger.json(tag, message);
    }

    //warning日志，非debug不打印，回调到callback
    public static void w(String tag, String msg, Object... args) {
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable throwable) {
        if (LibConfig.DEBUG)
            android.util.Log.w(tag, msg, throwable);
        if (callBack != null) {
            callBack.warning(tag, msg, throwable);
        }
    }

    public static void w(String tag, String msg) {
        if (LibConfig.DEBUG)
            android.util.Log.w(tag, msg);
        if (callBack != null) {
            callBack.warning(tag, msg);
        }
    }

    //error日志，任何情况都打印，回调到callback
    public static void e(String tag, String msg) {
        if (TextUtils.isEmpty(msg))
            return;
        android.util.Log.e(tag, msg);
        if (callBack != null) {
            callBack.error(tag, msg);
        }
    }

    public static void e(String tag, String msg, Object... args) {
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable throwable) {
        android.util.Log.e(tag, msg, throwable);
        if (callBack != null) {
            callBack.error(tag, msg, throwable);
        }
    }

    //info和error会进行回调
    public interface CLogCallBack {
        void info(String tag, String msg);

        void warning(String tag, String msg);

        void warning(String tag, String msg, Throwable throwable);

        void error(String tag, String msg);

        void error(String tag, String msg, Throwable throwable);
    }
}
