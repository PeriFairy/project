package com.yc.net.back;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
/**
 * 银行存取款业务
 * 
 * CS架构的程序
 * @author 姿
 *
 */
public class Server {
	
	private DataOutputStream dos;
	private BankDAO dao=new BankDAO();
	
	public static void main(String[] args) throws IOException {
		Server sr=new Server();
		sr.start();
	}
	
	
	
	public void start() throws IOException{
		//创建套接字服务器
		ServerSocket server=new ServerSocket(8888);
		System.out.println("服务器启动完成，监听端口：8888");
		
		boolean running=true;
		while(running){
			//当线程进入阻塞状态
			Socket client=server.accept();
			//创建线程处理业务
			new Thread(){
				@SuppressWarnings("deprecation")
				public void run(){
					//获取网络地址对象
						InetAddress address=client.getInetAddress();
						System.out.println("客户端的主机地址："+address.getHostAddress());
						System.out.println("客户端的IP地址："+Arrays.toString(address.getAddress()));
						try {
							InputStream in=client.getInputStream();
							OutputStream out=client.getOutputStream();
							dos =new DataOutputStream(out);
							
							boolean running=true;
							while(running){
								/**
								 * 业务约定==》协议
								 * 如果客户端发送一个命令：diposite，接受该命令所需要的参数
								 */
								DataInputStream dis=new DataInputStream(in);
								try{
									String command=dis.readUTF();
									switch(command){
										case "register":
											register(dis.readUTF(),dis.readUTF(),dis.readUTF());
											break;
										case "diposite":
											diposite(dis.readUTF(),dis.readFloat());
											break;
										case "withdraw":
											withdraw(dis.readUTF(),dis.readFloat());
											break;
										case "transfer":
											transfer(dis.readUTF(),dis.readUTF(),dis.readFloat());
											break;
									}
									/*System.out.println(command);
									String cardno=dis.readUTF();
									System.out.println(cardno);
									float money=dis.readFloat();
									System.out.println(money);*/
								}catch(EOFException e){
									break;
								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				
				}.start();;
			}
		
	}
	
	//存款
	public void diposite(String cardno,float money) throws IOException{
		try {
			dao.update(cardno, money);
			
			dos.writeUTF("存款成功");
			dos.flush();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//取款
	public void withdraw(String cardno,float money) throws IOException{
		try {
			dao.withdraw(cardno, money);
			
			dos.writeUTF("取款成功");
			dos.flush();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//转账
	public void transfer(String cardno01,String cardno02,float money) throws IOException{
		try {
			dao.transfer(cardno01,cardno02, money);
			dos.writeUTF("转账成功");
			dos.flush();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//开户
	public void register(String uname,String cardno,String pwd) throws IOException{
		try {
			dao.register(uname,cardno, pwd);
			dos.writeUTF("开户成功");
			dos.flush();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
