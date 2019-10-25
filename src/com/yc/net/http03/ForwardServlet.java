package com.yc.net.http03;

public class ForwardServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		//请求转发  和http协议无关
		
		//request.setRequestURL("/index.html");
		
		RequestDispatcher rd=request.getRequestDispatcher("/index.html");
		rd.forward(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request,response);
	}
	
	
	
}
