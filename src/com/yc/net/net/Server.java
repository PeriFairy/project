package com.yc.net.net;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Server {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		//屏幕录入对象
		Scanner scanner=new Scanner(System.in);
		
		//创建套接字服务器
		ServerSocket server=new ServerSocket(8888);
		System.out.println("服务器启动完成，监听端口：8888");
		//当前线程进入阻塞状态
		Socket client=server.accept();
		
		//获取网络地址对象
		InetAddress address=client.getInetAddress();
		System.out.println("客户端的主机地址："+address.getHostAddress());
		System.out.println("客户端的IP地址："+Arrays.toString(address.getAddress()));
		
		InputStream in=client.getInputStream();
		OutputStream out=client.getOutputStream();
		
		Thread t1=new Thread(){
			
			public void run(){
				boolean running=true;
				while(running){
					System.out.println("我(服务器)说：");
					String msg=scanner.nextLine();
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
						System.out.println("aaaa"+msg);
						if(msg.startsWith("文件：")){
							String filename=msg.substring("文件：".length());
							filename=filename.substring(filename.lastIndexOf("/")+1);
							saveFile(in,filename);
							System.out.println("666666");
						}else{
							System.out.println("客户端说："+msg);
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
		
		/*byte [] buffer =new byte[1024];
		int count=in.read(buffer);
		System.out.println("客户端说："+new String(buffer,0,count));
		
		out.write("李四".getBytes());*/
		
		scanner.close();
		client.close();
		server.close();
		
	}
	
	static void saveFile(InputStream in,String filename) throws IOException{
		FileOutputStream fos=new FileOutputStream(filename);
		
		try {
			byte [] buffer=new byte[1024];
			int count;
			while((count=in.read(buffer))>0){
				System.out.println("服务"+count);
				fos.write(buffer,0,count);
				System.out.println("hong");
			}
		} finally{
			fos.close();
		}
		System.out.println("dddde");
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
