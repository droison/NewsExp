package xyz.chaisong.newsexp.util;

import android.text.TextUtils;
import android.util.Log;

import xyz.chaisong.newsexp.BuildConfig;

/**
 * Created by song on 15/5/11.
 */
public class QLog {
    public static QLogCallBack callBack;

    public static void time(String tag) {
        i(tag, "current second time:" + System.currentTimeMillis());
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) Log.i(tag, msg);
        if (callBack != null) {
            callBack.info(tag, msg);
        }
    }

    public static void i(String tag, String msg, Object... args) {
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        i(tag, msg);
    }


    public static void d(String message) {
        if (BuildConfig.DEBUG)
            Logger.d(message);
    }

    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG)
            Logger.d(tag, message);
    }

    public static void d(String message, int methodCount) {
        if (BuildConfig.DEBUG)
            Logger.d(message, methodCount);
    }

    public static void d(String tag, String message, int methodCount) {
        if (BuildConfig.DEBUG)
            Logger.d(tag, message, methodCount);
    }

    public static void json(String message) {
        if (BuildConfig.DEBUG)
            Logger.json(message);
    }

    public static void json(String tag, String message) {
        if (BuildConfig.DEBUG)
            Logger.json(tag, message);
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Object... args) {
        if (BuildConfig.DEBUG) {
            if (args.length > 0) {
                msg = String.format(msg, args);
            }
            w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.w(tag, msg, throwable);
    }


    public static void e(String tag, String msg) {
        if (TextUtils.isEmpty(msg))
            return;
        Log.e(tag, msg);
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
        Log.e(tag, msg, throwable);
        if (callBack != null) {
            callBack.error(tag, msg, throwable);
        }
    }

    //info和error会进行回调
    public interface QLogCallBack {
        void info(String tag, String msg);

        void error(String tag, String msg);

        void error(String tag, String msg, Throwable throwable);
    }
}
