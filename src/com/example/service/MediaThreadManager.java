package com.example.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MediaThreadManager {
	private static ThreadPoolExecutor pool;
	private static SwtEmbeddedMediaPlayer embeddedMediaPlayer;
	private static BlockingQueue queue;
	
	static {
		queue = new  ArrayBlockingQueue<Runnable>(1);
		pool = new ThreadPoolExecutor(1,  1, 0, TimeUnit.SECONDS, queue ) ;
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy ());
        System.out.println("pool的哈希码："+pool.hashCode());
	}
	
	public MediaThreadManager(SwtEmbeddedMediaPlayer embeddedMediaPlayers) {
		embeddedMediaPlayer=embeddedMediaPlayers;
		queue = new  ArrayBlockingQueue<Runnable>(1);
		pool = new ThreadPoolExecutor(1,  1, 0, TimeUnit.SECONDS, queue ) ;
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy ());
	}


	public static void excute(Runnable runnable){
		System.out.println(Thread.currentThread().getName());
		pool.execute(runnable);
		System.out.println("线程已经结束");
	}
}
