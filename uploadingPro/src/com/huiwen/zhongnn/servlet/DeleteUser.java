package com.huiwen.zhongnn.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huiwen.zhongnn.dao.UserDAO;

public class DeleteUser extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("userId");
		
		//调用UserDAO中的删除用户的方法
		if(new UserDAO().deleteUser(id))
		{
			//删除成功
			response.sendRedirect("ok");
		}
		else
		{
			//删除失败
			response.sendRedirect("error");
		}
			
		
		out.flush();
		out.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

	}

}
