package xyz.chaisong.lib.app.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import xyz.chaisong.lib.log.CLog;
import xyz.chaisong.mmanagercenter.MManager;

/**
 * Created by song on 16/10/27.
 */

public class ActivityLifecycleMgr extends MManager implements Application.ActivityLifecycleCallbacks {

    public static final long CHECK_DELAY = 500;

    private static final String TAG = "ActivityLifecycleCallba";

    public interface Listener {

        public void onBecameForeground();

        public void onBecameBackground();

    }

    private boolean foreground = false, paused = true;
    private Handler handler = new Handler();
    private List<Listener> listeners = new CopyOnWriteArrayList<Listener>();
    private Runnable check;

    @Override
    public void onManagerInit(Context context) {
        super.onManagerInit(context);
        getManagerState().isManagerPersistent = true;
        Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(this);
    }

    public boolean isForeground() {
        return foreground;
    }

    public boolean isBackground() {
        return !foreground;
    }

    public void setForeground(boolean foreground) {
        this.foreground = foreground;
        logBackgroundState();
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        paused = false;
        boolean wasBackground = !foreground;
        setForeground(true);
        if (check != null)
            handler.removeCallbacks(check);

        if (wasBackground) {
            CLog.i(TAG, "went foreground");
            for (Listener l : listeners) {
                try {
                    l.onBecameForeground();
                } catch (Exception exc) {
                    CLog.e(TAG, "Listener threw exception!", exc);
                }
            }
        } else {
            CLog.i(TAG, "still foreground");
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        paused = true;

        if (check != null)
            handler.removeCallbacks(check);

        handler.postDelayed(check = new Runnable() {
            @Override
            public void run() {
                if (foreground && paused) {
                    setForeground(false);
                    CLog.i(TAG, "went background");
                    for (Listener l : listeners) {
                        try {
                            l.onBecameBackground();
                        } catch (Exception exc) {
                            CLog.e(TAG, "Listener threw exception!", exc);
                        }
                    }
                } else {
                    CLog.i(TAG, "still foreground");
                }
            }
        }, CHECK_DELAY);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    private void logBackgroundState() {
        CLog.w(TAG, "当前进入后台：" + isBackground());
    }
}