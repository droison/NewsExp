package xyz.chaisong.lib.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by song on 15/5/8.
 */
public abstract class ThreadExecutor {

    /**
     * 线程池框架执行
     */
    private static ExecutorService executor = null;

    /**
     *线程数目
     */
    private static int nThreads = 3;

    /**
     * 初始化
     */
    static {
        executor = Executors.newFixedThreadPool(nThreads);
    }

    /**
     * 执行类
     *
     * @param t
     *            线程
     */
    public static void execute(Runnable t) {
        executor.execute(t);
    }
}

