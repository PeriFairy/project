package com.yc.net.http04;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;

public class HttpServletResponse {
	
	private static WebXmlParser webXmlParser=new WebXmlParser("web.xml");
	
	private HttpServletRequest request;
	
	private OutputStream out;
	
	private int status=200;
	
	private String message="OK";
	
	private HashMap<String,String> headerMap=new HashMap<>();
	
	public HttpServletResponse(HttpServletRequest request,OutputStream out){
		super();
		this.request=request;
		this.out=out;
	}
	
	public void commit() throws IOException{
		String suffix=request.getRequestURL().substring(
				request.getRequestURL().lastIndexOf(".")+1);
		String contentType=webXmlParser.getContentType(suffix);
		if(headerMap.containsKey("content-Type")==false){
			String contentTypep=webXmlParser.getContentType(suffix);
			setContentType(contentType);
		}
		String resp="HTTP/1.1 "+status+" "+message+"\r\n";
		for(Entry<String,String> entry:headerMap.entrySet()){
			resp+=entry.getKey()+": "+entry.getValue()+"\r\n";
		}
		
		resp+="\r\n";
		out.write(resp.getBytes());
		if(status<300 ||status>399){
			if(caw.toString().isEmpty()){
				String rootPath=("D:/photo");
				String filePath=request.getRequestURL();
				String disPath=rootPath+filePath;
				if(new File(disPath).exists()==false){
					disPath=rootPath+"/404.html";
				}
				FileInputStream fis=new FileInputStream(disPath);
				byte [] buffer=new byte[1024];
				int count;
				while((count=fis.read(buffer))>0){
					out.write(buffer,0,count);
				}
				fis.close();
			}else{
				out.write(caw.toString().getBytes());
			}
		}
	}
	
	public void setStatus(int status,String message){
		this.status=status;
		this.message=message;
	}
	public void sendRedirect(String webPath){
		this.setStatus(301, "Redirect");
		this.addHeader("Location",webPath);
	}
	public void addHeader(String key, String value) {
		this.headerMap.put(key, value);
	}

	private void setContentType(String contentType) {
		this.headerMap.put("Content-Type", contentType);
	}
	
	CharArrayWriter caw=new CharArrayWriter();
	PrintWriter pw=new PrintWriter(caw);
	public PrintWriter getWriter(){
		return pw;
	}
	
}
