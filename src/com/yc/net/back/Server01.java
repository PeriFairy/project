package com.yc.net.back;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class Server01 {
	private Scanner sc=new Scanner(System.in);
	DataInputStream dis;
	DataOutputStream dos;
	public void start() throws  IOException{
		Socket server=new Socket("127.0.0.1",8888);
		System.out.println("成功连接服务器！！！");
		InetAddress address=server.getInetAddress();
		System.out.println("服务器的主机地址："+address.getHostAddress());
		System.out.println("服务器的IP地址："+Arrays.toString(address.getAddress()));
		
		InputStream in=server.getInputStream();
		OutputStream out=server.getOutputStream();
		
		dis=new DataInputStream(in);
		dos=new DataOutputStream(out);
		
	}

}
