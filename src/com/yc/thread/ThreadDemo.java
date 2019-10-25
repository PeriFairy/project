package com.yc.thread;

public class ThreadDemo {
	
	public static void main(String[] args) throws InterruptedException {
		Thread mainThread=Thread.currentThread();
		
		System.out.println("线程ID(唯一):"+mainThread.getId());
		
		System.out.println("线程名称："+mainThread.getName());
		
		System.out.println("线程优先级(10最高，1最低，5默认："+mainThread.getPriority());
		
		Thread.sleep(2000);//休眠 单位 毫秒
		
		System.out.println("线程是否是守护线程（精灵线程）："+mainThread.isDaemon());
		
		System.out.println("线程是否处于活动状态："+mainThread.isInterrupted());
		
		Thread.sleep(2000);
		
		MyThread1 mt1=new MyThread1();
		//在启动线程前启动优先级
		mt1.setPriority(10);
		//运行线程必须执行start方法
		mt1.start();//第一个线程
		
		MyThread2 mt2=new MyThread2();
		Thread t=new Thread(mt2,"多线程");
		t.start();//第二个线程
	}

}

/**
 * 创建线程的两种方法：继承Thread类，实现Runnable接口。
 * @author 姿
 *
 */
class MyThread1 extends Thread{

	@Override
	public void run() {
		for(int i=0;i<100000;i++){
			System.out.println(Thread.currentThread().getName()+":"+i);
		}
	}
}

class MyThread2 extends A implements Runnable{

	@Override
	public void run() {
		for(int i=0;i<100000;i++){
			System.out.println(Thread.currentThread().getName()+":"+i);
		}
	}
	
}



class A{
	
}















