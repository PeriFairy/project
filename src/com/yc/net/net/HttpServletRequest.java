package com.yc.net.net;

import java.util.HashMap;

public class HttpServletRequest {
	
	private String method;
	private String requestURL;
	private String protocol;
	private HashMap<String,String> headerMap=new HashMap<>();
	
	public HttpServletRequest(String content){
		//解析请求报文
		String []lines=content.split("\r\n");
		for(int i=0;i<lines.length;i++){
			if(i==0){
				//解析头行 以空格为间隔符
				String [] topLines=lines[i].split("\\s");
				method=topLines[0];
				requestURL=topLines[1];
				protocol=topLines[2];
			}else{
				String [] headerLines=lines[i].split(":\\s");
				headerMap.put(headerLines[0], headerLines[1]);
			}
		}
	}
	
	
	public String getRequestURL(){
		return requestURL;
	}
	
	public String getMethod(){
		return method;
	}
	
	public String getProtocol(){
		return protocol;
	}
	
	public String getHeader(String header){
		return headerMap.get(header);
	}
}
