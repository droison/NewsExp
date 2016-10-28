package xyz.chaisong.lib.network;

/**
 * Created by song on 16/4/17.
 */
public interface RequestCmd {
    RequestCmd setRequestInvoker(Object invoker);
    void cancel();
}
