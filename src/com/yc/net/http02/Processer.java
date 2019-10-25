package com.yc.net.http02;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Processer {
	
	//定义servlet容器
	private HashMap<String,HttpServlet> servletContainer=new HashMap<>();
	
	{
		//添加一个Servlet
		servletContainer.put("/redirect.s", new RedirectServlet());
		// 添加一个Servlet 实现请求转发
		servletContainer.put("/forward.s", new ForwardServlet());
		
		servletContainer.put("/hello.s", new HelloServlet());
	}
	public void process(Socket socket){
		InputStream in;
		OutputStream out;
		try {
			in=socket.getInputStream();
			out=socket.getOutputStream();
			
			//请求报文内容
			byte [] buffer=new byte[1024];
			int count=in.read(buffer);
			String content=new String(buffer,0,count);
			System.out.println(content);
			//解析请求报文
			HttpServletRequest request=parseRequest(content);
			HttpServletResponse response=new HttpServletResponse(request,out);
			/**
			 * 静态请求：对应着一个html、js、css
			 * 动态请求：hello.s
			 * 非法404请求 即没有物理文件也没有虚拟地址
			 */
			
			//判断物理文件是否存在
			String rootPath=("D:/photo");
			String webPath=request.getRequestURL();
			//判断文件是否存在
			String disPath=rootPath+webPath;
			if(new File(disPath).exists()==true){
				//静态请求直接commit
			}else if(servletContainer.containsKey(webPath)){
				//127.0.0.1:8080/hello.s
				//判断虚拟路径中有没有该地址（Servlet容器中去找）
				//动态请求先由servlet处理
				HttpServlet servlet=servletContainer.get(webPath);
				servlet.service(request, response);
			}else{
				//404改写资源文件404.html
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
