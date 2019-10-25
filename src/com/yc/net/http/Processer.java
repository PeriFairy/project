package com.yc.net.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Processer {
	
	public void process(Socket socket){
		InputStream in;
		OutputStream out;
		try {
			in=socket.getInputStream();
			out=socket.getOutputStream();
			byte [] buffer=new byte[1024];
			int count=in.read(buffer);
			String content=new String(buffer,0,count);
			System.out.println(content);
			//解析请求报文（暂未实现）
			parseRequest(content);
			HttpServletRequest request=parseRequest(content);
			/**
			 * /index.html
			 * 
			 */
			
			String suffix=request.getRequestURL().substring(
					request.getRequestURL().lastIndexOf(".")+1);
			String contentType;
			
			switch(suffix){
				case "js":
					contentType="application/x-javascript";
					break;
				case "css":
					contentType="text/css";
					break;
				case "jpg":
					contentType="image/jpeg";
					break;
				case "bmg":
					contentType="image/bmg";
					break;
				case "gif":
					contentType="image/gif";
					break;
				case "png":
					contentType="image/png";
					break;
				default:
					contentType="text/html";		
			}
			
			String responseStr="HTTP/1.1 200 OK\r\n";
			responseStr+="Content-Type: "+contentType+"\r\n";
			responseStr+="\r\n";//CRLF 空行
//			responseStr+="<h1>hello world</h1>";
			out.write(responseStr.getBytes());
			
			String rootPath=("D:/photo/");
			String filePath=request.getRequestURL();
			//判断文件是否存在
			String disPath=rootPath+filePath;
			if(new File(disPath).exists()==false){
				disPath=rootPath+"404.html";
			}
			FileInputStream fis=new FileInputStream(disPath);
			
			//向浏览器发送报文
			while((count=fis.read(buffer))>0){
				out.write(buffer,0,count);
			}
			fis.close();
			//根据请求的路径返回对应的文件html
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//解析请求报文
		//给与对应的响应
		
	}

	private HttpServletRequest parseRequest(String content) {
		HttpServletRequest request=new HttpServletRequest(content);
		return request;
	}

}
