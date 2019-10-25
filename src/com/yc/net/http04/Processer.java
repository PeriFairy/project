package com.yc.net.http04;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Processer {
	
	//定义servlet容器
	private HashMap<String,HttpServlet> servletContainer=new HashMap<>();
	
	{
		
	}
	
	public void process(Socket socket) {
		InputStream in;
		OutputStream out;
		try {
			in=socket.getInputStream();
			out=socket.getOutputStream();
			//请求报文内容
			byte [] buffer=new byte[1024];
			int count;
			count=in.read(buffer);
			String content=new String(buffer,0,count);
			System.out.println(content);
			
			//解析请求报文
			HttpServletRequest request=parseRequest(content);
			HttpServletResponse response=new HttpServletResponse(request,out);
			String rootPath=("D:/photo");
			String webPath=request.getRequestURL();
			
			String disPath=rootPath+webPath;
			if(new File(disPath).exists()==true){
				
			}else if(servletContainer.containsKey(webPath)){
				HttpServlet servlet=servletContainer.get(webPath);
				servlet.service(request, response);
			}else{
				response.setStatus(404, "Not Found");
				request.setRequestURL("/404.html");
			}
			response.commit();
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
	}

	private HttpServletRequest parseRequest(String content) {
		HttpServletRequest request=new HttpServletRequest(content);
		return request;
	}

}
