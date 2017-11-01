package com.huiwen.zhongnn.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Main extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		//req :获得信息
		
		//res：发送信息

		//解决中文乱码问题
		res.setContentType("text/html;charset=gbk");
		
		PrintWriter out = res.getWriter();
		
		out.println("<html>");
		out.println("<body bgcolor=#CED3FF>");
		out.println("<center><hr>");
		out.println("<h1>主界面</h1>");
		
		out.println("<a href=welcom>管理用户</a><br><br>");
		out.println("<a href=adduser>添加用户</a><br><br>");
		out.println("<a href=searchuser>查找用户</a><br><br>");
		out.println("<a href=leave>安全退出</a><br><br>");
		
		out.println("<hr></center></body>");
		out.println("</html>");
		out.flush();
		out.close();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		this.doGet(request, response);
	}

}
