package com.yc.net.net;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		//屏幕录入对象
		Scanner sc=new Scanner(System.in);
		Socket server=new Socket("172.20.81.176",8888);
		System.out.println("成功连接服务器");
		InetAddress address=server.getInetAddress();
		System.out.println("服务器端的主机地址："+address.getHostAddress());
		System.out.println("服务器的IP地址："+Arrays.toString(address.getAddress()));
		
		InputStream in=server.getInputStream();
		OutputStream out=server.getOutputStream();
		
		Thread t1=new Thread(){
			
			public void run(){
				boolean running=true;
				while(running){
					System.out.println("我(客户端)说：");
					String msg=sc.nextLine();
					try {
						out.write(msg.getBytes());
						if(msg.startsWith("文件：")){
							String filename=msg.substring("文件：".length());
							sendFile(out,filename);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		t1.start();
		
		Thread t2=new Thread(){
			
			public void run(){
				boolean running=true;
				while(running){
					
					try {
						byte[] buffer=new byte[1024];
						int count=in.read(buffer);
						String msg=new String(buffer,0,count);
						if(msg.startsWith("文件：")){
							System.out.println("55555");
							String filename=msg.substring("文件：".length());
							filename=filename.substring(filename.lastIndexOf("/")+1);
							saveFile(in,filename);
							System.out.println("5");
						}else{
							System.out.println("服务器说："+msg);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		t2.start();
		t1.join();
		t2.join();
		/*
		out.write("hello 张三".getBytes());
		
		byte [] buffer=new byte[1024];
		int count=in.read(buffer);
		System.out.println("服务器说："+new String(buffer,0,count));
		*/
		sc.close();
		server.close();
	}
	static void saveFile(InputStream in,String filename) throws IOException{
		FileOutputStream fos=new FileOutputStream(filename);
		
		try {
			byte [] buffer=new byte[1024];
			int count;
			while((count=in.read(buffer))>0){
				System.out.println(count);
				fos.write(buffer,0,count);
				System.out.println("hongse");
			}
		} finally{
			fos.close();
		}
		System.out.println("ggg");
		System.out.println("文件保存成功！"+"d:/"+filename);
	}
	static void sendFile(OutputStream out,String filename) throws IOException{
		FileInputStream fis=new FileInputStream(filename);
		
		try {
			byte [] buffer=new byte[1024];
			int count;
			while((count=fis.read(buffer))>0){
				out.write(buffer,0,count);
			}
		} finally{
			fis.close();
		}
		
		System.out.println("文件发送成功！"+"d:/"+filename);
	}

}
