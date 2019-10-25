package com.yc.net.http04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Tomcat {
	
	public static void main(String[] args) {
		Tomcat tomcat=new Tomcat();
		
	}
	
	public void start() throws IOException{
		ServerSocket server=new ServerSocket(8080);
		boolean running=true;
		while(running){
			Socket socket=server.accept();
			new Thread(){
				public void run(){
					Processer processer=new Processer();
					processer.process(socket);
				}
			}.start();
		}
	}
	
	public void shutdown(){
		
	}

}
