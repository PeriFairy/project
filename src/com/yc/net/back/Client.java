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

public class Client {
	
	private Scanner sc=new Scanner(System.in);
	DataInputStream dis;
	DataOutputStream dos;
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		
		new Client().start();
	}
	
	public void start() throws UnknownHostException, IOException{
		Socket server=new Socket("172.20.81.176",8888);
		System.out.println("成功连接服务器！！！");
		InetAddress address=server.getInetAddress();
		System.out.println("服务器端的主机地址："+address.getHostAddress());
		System.out.println("服务器的IP地址："+Arrays.toString(address.getAddress()));
		
		InputStream in=server.getInputStream();
		OutputStream out=server.getOutputStream();
		
		dis =new DataInputStream(in);
		dos=new DataOutputStream(out);
		
		boolean running=true;
		while(running){
			System.out.println("***************************************");
			System.out.println("*                1.开户                                             *");
			System.out.println("*                2.存款                                             *");
			System.out.println("*                3.取款                                             *");
			System.out.println("*                4.转账                                             *");
			System.out.println("*                0.退出                                             *");
			System.out.println("请输入");
			String command=sc.nextLine();
			switch(command){
				case "1":
					register();
					break;
				case "2":
					diposite();
					break;
				case "3":
					withdraw();
					break;
				case "4":
					transfer();
					break;
				case "0":
					System.out.println("bye bye");
					running=false;
			}
		}
		server.close();
		sc.close();
	}
	
	//转账
	private void transfer() throws IOException {
		System.out.println("请输入转账账号：");
		String cardno01=sc.nextLine();
		System.out.println("请输入转入账号：");
		String cardno02=sc.nextLine();
		System.out.println("请输入金额：");
		Float money=sc.nextFloat();
		dos.writeUTF("transfer");
		dos.writeUTF(cardno01);
		dos.writeUTF(cardno02);
		dos.writeFloat(money);
		String ret=dis.readUTF();
		System.out.println(ret);
	}
	
	//取款
	private void withdraw() throws IOException {
		System.out.println("请输入用户账号：");
		String cardno=sc.nextLine();
		System.out.println("请输入取款金额：");
		float money=sc.nextFloat();
		dos.writeUTF("withdraw");
		dos.writeUTF(cardno);
		dos.writeFloat(money);
		dos.flush();
		String ret=dis.readUTF();
		System.out.println(ret);
	}
	
	//存款
	private void diposite() throws IOException {
		System.out.println("请输入用户账号：");
		String cardno=sc.nextLine();
		System.out.println("请输入存款金额");
		float money=sc.nextFloat();
		dos.writeUTF("diposite");
		dos.writeUTF(cardno);
		dos.writeFloat(money);
		dos.flush();
		String ret=dis.readUTF();
		System.out.println(ret);
	}
	
	//注册
	private void register() throws IOException {
		System.out.println("请输入用户名");
		String uname=sc.nextLine();
		String accountid=System.currentTimeMillis()+"";
		System.out.println("请输入密码");
		String pwd=sc.nextLine();
		dos.writeUTF("register");
		dos.writeUTF(uname);
		dos.writeUTF(accountid);
		dos.writeUTF(pwd);
		String ret=dis.readUTF();
		System.out.println(ret);
		
	}
	

}
