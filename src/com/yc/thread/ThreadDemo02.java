package com.yc.thread;

/**
 * 线程分类以及运行特性
1.	父线程：主线程会等待所有的子线程执行完之后再终止，守护的子线程例外
2.	子线程
3.	守护线程（精灵线程）
 * @author 姿
 *
 */
public class ThreadDemo02 {
	
	public static void main(String[] args) throws InterruptedException {
		
		//匿名线程类
		Thread t1=new Thread("多线程"){

			@Override
			public void run() {
				for(int i=0;i<10000;i++){
					System.out.println(Thread.currentThread().getName()+":"+i);
				}
			}
		};
		
		t1.setDaemon(true);//设置守护线程
		t1.start();
		Thread.sleep(100);
		System.out.println("主线程执行完毕");
		
	}
}
