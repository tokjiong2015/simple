package com.huiwen.zhongnn.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddUser extends HttpServlet
{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		// req :获得客户端(浏览器)的信息

		// res：向客户端返回信息

		// 中文乱码
		res.setContentType("text/html;charset=gbk");

		PrintWriter out = res.getWriter();

		HttpSession hs = req.getSession(true);
		String u =(String) hs.getAttribute("uname");
		
		out.println("<html>");
		out.println("<body bgcolor=#CED3FF><center>");
		
		out.println("欢迎您:"+u+" <hr>");
		//	out.println("<img src =images/3.jpg><br>");
		out.println("<h1>添加用户</h1>");
		
		out.println("<br><a href=main>返回主界面</a>");
		out.println("<a href=login?info=begain>返回重新登录</a>");
		out.println("<a href=leave>安全退出</a><hr>");
	

		out.println("<form action=addusercl method=get>");

		out.println("<table border=1>");

		out.println(
				"<tr>" +
				"<td>用户名称</td>" +
				"<td><input type=text name=username ></td>" +
				"</tr>"
					);

		out.println(
				"<tr>" + 
				"<td>用户密码</td>"+
				"<td><input type=text name=passwd></td>" + 
				"</tr>"
					);

		out.println(
				"<tr>" + 
				"<td>邮箱</td>"+
				"<td><input type=text name=email></td>" + 
				"</tr>"
					);
		
		out.println(
				"<tr>" + 
				"<td>级别</td>"+
				"<td><input type=text name=grade></td>" + 
				"</tr>"
					);
		
		out.println("<tr><td colspan=2><input type=submit value=发送></td><td><input type=reset value=重设></td></tr>");
		out.println("</table>");

		out.println("</form>");
		out.println("<hr></center></body>");
		out.println("</html>");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

	}

}
