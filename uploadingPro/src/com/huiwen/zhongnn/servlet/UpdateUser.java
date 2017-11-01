package com.huiwen.zhongnn.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.huiwen.zhongnn.bean.User;

/**
 * @author ZHONGNN
 * @time 9-12 修改界面
 */
public class UpdateUser extends HttpServlet
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
		out.println("<h1>修改用户</h1>");
		
		out.println("<br><a href=main>返回主界面</a>");
		out.println("<a href=login?info=begain>返回重新登录</a>");
		out.println("<a href=leave>安全退出</a><hr>");
	

		out.println("<form action=updatecl method=get>");
		
		out.println("<table border=1>");
		
		out.println("<tr>" +
				"<td>id</td>" +
				"<td><input readonly type=text name=userId value="+req.getParameter("userId")+"></td>" +
				"</tr>");
		
		out.println("<tr>" +
				"<td>name</td>" +
				"<td><input type=text name=username value="+req.getParameter("userNmae")+"></td>" +
				"</tr>");


		out.println("<tr>" +
				"<td>passwd</td>" +
				"<td><input type=text name=passwd value="+req.getParameter("pwd")+"></td>" +
				"</tr>");

		
		out.println("<tr>" +
				"<td>email</td>" +
				"<td><input type=text name=email value="+req.getParameter("email")+"></td>" +
				"</tr>");
		
		out.println("<tr>" +
				"<td>grade</td>" +
				"<td><input type=text name=grade value="+req.getParameter("grade")+"></td>" +
				"</tr>");
		
		out.println("<tr><td colspan=2><input type=submit value=修改用户></td></tr>");
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
