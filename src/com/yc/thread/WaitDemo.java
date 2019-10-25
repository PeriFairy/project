package com.yc.thread;

public class WaitDemo {
	
	public static void main(String[] args) throws InterruptedException {
		MyRunnable mr=new MyRunnable();
		for(int  i=0;i<100;i++){
			new Thread(mr,"线程"+i).start();
		}
		
		
		Thread.sleep(1100);
		
		//同步代码块
		synchronized (mr) {
			//通过49线程继续执行，注意：之前执行的是mr的wait，所有这里也是执行mr的notify
			mr.notify();
		}
		
		System.out.println(mr.count);
		/*
		Thread t1=new Thread(mr,"武松");
		Thread t2=new Thread(mr,"鲁达");
		Thread t3=new Thread(mr,"吴用");
		Thread t4=new Thread(mr,"宋江");
		Thread t5=new Thread(mr,"林冲");
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();*/
		
	}
	
	
}

class MyRunnable implements Runnable{
	
	int count=100;
	@Override
	/**
	 * synchronized表示该代码只能被一个线程用
	 */
	public synchronized void run() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if("线程49".equals(Thread.currentThread().getName())){
			System.out.println("=======================线程49=====================在此等待");
			try {
				wait();//Object的方法
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(Thread.currentThread().getName()+" : "+ count--);
		
	}
	
}
