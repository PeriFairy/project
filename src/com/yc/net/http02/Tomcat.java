package com.yc.net.http02;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Tomcat {
	
	public static void main(String[] args) throws IOException {
		Tomcat tomcat=new Tomcat();
		tomcat.startup();
	}
	
	/**
	 * 启动命令
	 * @throws IOException
	 */
	public void startup() throws IOException{
		ServerSocket server=new ServerSocket(8080);
		boolean running=true;
		while(running){
			Socket socket=server.accept();
			new Thread(){
				public void run(){
					Processer processer=new Processer();
					processer.process(socket);
					
				}
			}.start();;
		}
		server.close();
	}
	
	public void shutdown(){
		
	}

}
