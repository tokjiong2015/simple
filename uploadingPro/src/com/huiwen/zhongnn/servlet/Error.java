package com.huiwen.zhongnn.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Error extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>��������</TITLE></HEAD>");
		out.println("  <BODY bgcolor=#CED3FF><center>");
		out.println("<h1>���ź����������ɹ�</h1>");
		out.println("<a href=main>����������</a>&nbsp;&nbsp;<a href=welcom>���ع������</a>");
		out.println("<hr></center></BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		
	}

}
