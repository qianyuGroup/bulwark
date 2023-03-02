package com.qianyu.bulwark.common.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * bulwark的线程池
 *
 * @author junlong.njl
 * 创建时间 2023-02-28
 */
public class BulwarkServerThreadPool {
    private static final String THREAD_NAME_PREFIX = "bulwark-server-thread-";
    private static final AtomicInteger THREAD_NUMBER = new AtomicInteger(1);
    /**
     * //todo 后续线程信息改为可配置
     * 目前：core 16，max 32，queue 500
     */
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 32, 300L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(500), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, THREAD_NAME_PREFIX+THREAD_NUMBER.getAndAdd(1));
            return thread;
        }
    }, new ThreadPoolExecutor.CallerRunsPolicy());




}
