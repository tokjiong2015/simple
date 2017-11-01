package com.huiwen.zhongnn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.huiwen.zhongnn.dao.UserDAO;

public class SearchUserCL extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();
		
	//	int radsearch_int= 0;
		
//		UserDAO udao = new UserDAO();
		
		String search = request.getParameter("search");
		
	//	String radsearch = request.getParameter("radsearch");

		System.out.println("要查询的用户名为：" + search);
		
//		radsearch_int = Integer.parseInt(radsearch);
		
//		System.out.println(radsearch_int);
		
	//	HttpSession hs = request.getSession(true);
		
	//	List listByName = udao.search(search,radsearch_int);
		
	//	hs.setAttribute("myList",listByName);
		
	//	if (listByName!=null)
	//	{
			// 修改成功
			response.sendRedirect("searchuser?name="+search);
//		} else
//		{
			// 修改失败
//			response.sendRedirect("error");
//		}


		out.flush();
		out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

	}

}
