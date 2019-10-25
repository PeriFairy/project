package com.yc.net.http03;

public class RedirectServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 1.页面跳转（请求转发、响应重定向）
		 * 
		 * 2.直接输出html内容 或者json数据
		 * 
		 * 
		 */
		response.sendRedirect("/index.html");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request,response);
	}
	
	

}
