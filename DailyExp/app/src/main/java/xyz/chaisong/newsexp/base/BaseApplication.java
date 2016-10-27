package xyz.chaisong.newsexp.base;

import android.app.Application;

import xyz.chaisong.newsexp.util.LocalDisplay;

/**
 * Created by song on 16/10/27.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LocalDisplay.init(this);
    }
}
