package com.example.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationTaskManager {
	
	private Runnable runnable;
	//下载任务线程池
	private static void test(){
		ExecutorService executor=Executors.newFixedThreadPool(3);
		Thread t=new Thread(new Runnable() {
			public void run() {
				try {
                  Thread.sleep(5000);
                  System.out.println("Current:"+Thread.currentThread().getName());
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
			}
		});
		executor.execute(t);
//		for(int i=1;i<5;i++){
//			final int task=i;
//			executor.execute(new Runnable() {
//				
//				@Override
//				public void run() {
//					Thread.currentThread().setName(task+"");
//					System.out.println("Current:"+Thread.currentThread().getName());
//					try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//				}
//			});
//		}
	}
	
	public static void main(String[] args) {
		test();
	}
}
