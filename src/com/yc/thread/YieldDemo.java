package com.yc.thread;

public class YieldDemo {

	
	public static void main(String[] args) {
		
		new Thread("写代码"){

			@Override
			public void run() {
				long start=System.currentTimeMillis();
				for(int i=0;i<10000;i++){
					Math.random();
				}
				long time=System.currentTimeMillis()-start;
				System.out.println(Thread.currentThread().getName()+":"+time);
			}
		}.start();
		
		new Thread("多线程"){

			@Override
			public void run() {
				long start=System.currentTimeMillis();
				for(int i=0;i<10000;i++){
					Math.random();
					//执行让渡
					Thread.yield();
				}
				long time=System.currentTimeMillis()-start;
				System.out.println(Thread.currentThread().getName()+":"+time);
			}
		}.start();
		
	}
}
