package xyz.chaisong.lib.network;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

import xyz.chaisong.lib.network.response.RespBaseMeta;

/**
 * @author song on 15/9/19.
 */
public class DeliveryExecutor {
    private final Executor mResponsePoster;

    private static DeliveryExecutor single;

    public static synchronized DeliveryExecutor getInstance() {
        if (single == null) {
            single = new DeliveryExecutor((new Handler(Looper.getMainLooper())));
        }
        return single;
    }

    private DeliveryExecutor(final Handler handler) {
        mResponsePoster = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    public <T extends RespBaseMeta> void postMainThread(Runnable runnable) {
        mResponsePoster.execute(runnable);
    }
}
