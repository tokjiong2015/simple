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
 * @time 9-12 �޸Ľ���
 */
public class UpdateUser extends HttpServlet
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
		out.println("<h1>�޸��û�</h1>");
		
		out.println("<br><a href=main>����������</a>");
		out.println("<a href=login?info=begain>�������µ�¼</a>");
		out.println("<a href=leave>��ȫ�˳�</a><hr>");
	

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
		
		out.println("<tr><td colspan=2><input type=submit value=�޸��û�></td></tr>");
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
