package com.yc.net.http02;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;

public class HttpServletResponse {
	
	//web.xml解析器
	private static WebXmlParser webXmlParser=new WebXmlParser("web.xml");
	
	private HttpServletRequest request;
	private OutputStream out;
	
	//状态码字段
	private int status=200;
	
	//状态码的描述信息
	private String message="OK";
	
	//头域集合
	private HashMap<String,String> headerMap=new HashMap<>();
	
	public HttpServletResponse(HttpServletRequest request,OutputStream out){
		super();
		this.request=request;
		this.out=out;
	}
	
	/**
	 * 
	 * response.setContentType("????")//设置响应类型
	 * response.setStatus(404,"");//设置结果码
	 * response.setHeader("键","值")
	 */
	//提交方法
	public void commit() throws IOException{
		String suffix=request.getRequestURL().substring(
				request.getRequestURL().lastIndexOf(".")+1);
		
		//从web.xml文件中读取contentType,替换之前的编码判断
//		String contentType= webXmlParser.getContentType(suffix);
		
		//判断是否设置Content-Type
		if(headerMap.containsKey("Content-Type")==false){
			//设置响应类型
			String contentType= webXmlParser.getContentType(suffix);
			setContentType(contentType);
		}

		String responseStr="HTTP/1.1 "+status+" "+message+"\r\n";
		//responseStr+="Content-Type: "+contentType+"\r\n";
		
		//写头域信息
		for(Entry<String,String> entry:headerMap.entrySet()){
			responseStr+=entry.getKey()+":"+entry.getValue()+"\r\n";
		}
		responseStr+="\r\n";//CRLF 空行
//		responseStr+="<h1>hello world</h1>";
		out.write(responseStr.getBytes());
		
		//响应重定向不需要写body
		if(status<300 || status>399){
			if(caw.toString().isEmpty()){
				String rootPath=("D:/photo");
				String filePath=request.getRequestURL();
				//判断文件是否存在
				String disPath=rootPath+filePath;
				if(new File(disPath).exists()==false){
					disPath=rootPath+"/404.html";
				}
				FileInputStream fis=new FileInputStream(disPath);
				
				byte[] buffer = new byte[1024];
				int count;
				// 向浏览器发送报文
				//向浏览器发送报文
				while((count=fis.read(buffer))>0){
					out.write(buffer,0,count);
				}
				fis.close();
			}else{
				out.write(caw.toString().getBytes());
			}
			
		}
		
	}
	
	public  void setStatus(int status,String message){
		this.status=status;
		this.message=message;
	}
	
	/**
	 * 响应重定向
	 * @param webPath
	 */
	public void sendRedirect(String webPath){
		
		/**
		 * 响应结果码：
		 * 	1xx 接受请求,继续处理
		 * 	2xx 正常响应
		 * 	3xx 响应重定向301 302
		 * 	4xx 浏览器端发生错误404 405
		 * 	5xx 服务器端发生错误
		 */
		
		this.setStatus(301, "Redirect");
		this.addHeader("Location",webPath);
	}

	public void addHeader(String key, String value) {
		this.headerMap.put(key, value);
	}
	
	public void setContentType(String contentType){
		this.headerMap.put("Content-Type", contentType);
	}
	
	CharArrayWriter caw=new CharArrayWriter();
	PrintWriter pw=new PrintWriter(caw);
	
	public PrintWriter getWriter() {
		return pw;
	}
}
