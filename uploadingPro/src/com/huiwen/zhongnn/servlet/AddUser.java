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
		// req :��ÿͻ���(�����)����Ϣ

		// res����ͻ��˷�����Ϣ

		// ��������
		res.setContentType("text/html;charset=gbk");

		PrintWriter out = res.getWriter();

		HttpSession hs = req.getSession(true);
		String u =(String) hs.getAttribute("uname");
		
		out.println("<html>");
		out.println("<body bgcolor=#CED3FF><center>");
		
		out.println("��ӭ��:"+u+" <hr>");
		//	out.println("<img src =images/3.jpg><br>");
		out.println("<h1>����û�</h1>");
		
		out.println("<br><a href=main>����������</a>");
		out.println("<a href=login?info=begain>�������µ�¼</a>");
		out.println("<a href=leave>��ȫ�˳�</a><hr>");
	

		out.println("<form action=addusercl method=get>");

		out.println("<table border=1>");

		out.println(
				"<tr>" +
				"<td>�û�����</td>" +
				"<td><input type=text name=username ></td>" +
				"</tr>"
					);

		out.println(
				"<tr>" + 
				"<td>�û�����</td>"+
				"<td><input type=text name=passwd></td>" + 
				"</tr>"
					);

		out.println(
				"<tr>" + 
				"<td>����</td>"+
				"<td><input type=text name=email></td>" + 
				"</tr>"
					);
		
		out.println(
				"<tr>" + 
				"<td>����</td>"+
				"<td><input type=text name=grade></td>" + 
				"</tr>"
					);
		
		out.println("<tr><td colspan=2><input type=submit value=����></td><td><input type=reset value=����></td></tr>");
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
