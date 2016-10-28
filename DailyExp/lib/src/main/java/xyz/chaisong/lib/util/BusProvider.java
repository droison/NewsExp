package xyz.chaisong.lib.util;

import android.content.Context;

import xyz.chaisong.lib.LibConfig;
import xyz.chaisong.mmbus.IMMBus;
import xyz.chaisong.mmbus.MMBus;

/**
 * Created with Android Studio.
 * User: Felix
 * Date: 10/22/15
 * Time: 5:54 PM
 * QQ:74104
 * Email:lovejiuwei@gmail.com
 */
public final class BusProvider {

    private static MMBus mmBus;

    public static IMMBus getBus() {
        if (LibConfig.DEBUG) {
            return xyz.chaisong.mmbus.aidl.BusProvider.getBus();
        } else {
            if (mmBus == null) {
                mmBus = new MMBus();
            }
            return mmBus;
        }
    }

    public static void create(Context context){
        if (LibConfig.DEBUG)
            xyz.chaisong.mmbus.aidl.BusProvider.create(context);
    }

    public static void exit(Context context){
        if (LibConfig.DEBUG)
            xyz.chaisong.mmbus.aidl.BusProvider.exit(context);
    }
}