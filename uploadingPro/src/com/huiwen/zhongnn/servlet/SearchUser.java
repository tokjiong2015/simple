package com.huiwen.zhongnn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.huiwen.zhongnn.bean.User;
import com.huiwen.zhongnn.dao.UserDAO;

public class SearchUser extends HttpServlet
{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		//req :获得客户端(浏览器)的信息
		
		//res：向客户端返回信息

		//中文乱码
		response.setContentType("text/html;charset=gbk");
		
		PrintWriter out = response.getWriter();
		
		//得到在服务器端的session内存中的信息(3个属性),该信息是
		//在登录成功时,设置的
		HttpSession hs = request.getSession(true);
		String u =(String) hs.getAttribute("uname");
	//	List myList = (List)hs.getAttribute("myList");
		
		out.println("<html>");
		out.println("<body bgcolor=#CED3FF><center>");
		
		out.println("欢迎您:"+u+" <hr>");
		//	out.println("<img src =images/3.jpg><br>");
		out.println("<h1>查询用户</h1>");
		
		out.println("<br><a href=main>返回主界面</a>");
		out.println("<a href=login?info=begain>返回重新登录</a>");
		out.println("<a href=leave>安全退出</a><hr>");
		
		String name = request.getParameter("username");
		System.out.println(name);
		
		String type = request.getParameter("radtype");
		System.out.println(type);//1表示模糊查找,2表示精确查找
		
	
		//==========================分页功能==========================
		int pageSize = 5;//一页显示几条记录 
		int pageNow = 1;//希望显示第几页
		
		UserDAO userdao  = new UserDAO();
		
		
		
		//动态的接收pageNow
		String sPageNow = request.getParameter("pageNow");
		
		if(sPageNow != null)
		{
			pageNow = Integer.parseInt(sPageNow);
		}

		out.println("<form action=searchuser>");
		out.println("请输入用户名：<input type=text name=username>");
		out.println("<input type=submit value=搜 ><br>");
		out.println("<input type=radio name=radtype value=1 >模糊查找");
		out.println("<input type=radio name=radtype value=2 checked>精确查找 ");
		out.println("</form>");
		/*
		List list = null;
		
		if(myList != null)
		{
			list =userdao.getResusltByPage(myList,pageNow,pageSize);
			
		}*/
		
		List all = userdao.getResultByPage(type,name,pageNow, pageSize);
		
		out.println("<table border =1>");
		
		out.println("<tr bgcolor=pink><th>用户ID</th><th>用户名称</th><th>用户密码</th><th>邮箱</th><th>级别</th></tr>");
		
		//定义一个颜色数组
		String[] myCol = {"silver","pink"};
		
		if(all != null)
		{
			for(int i = 0;i<all.size();i++)
			{
				User user =(User) all.get(i);
				out.println("<tr bgcolor="+myCol[i%2]+">");
				out.println("<td>"+user.getUserId()+"</td>");
				out.println("<td>"+user.getUsername()+"</td>");
				out.println("<td>"+user.getPasswd()+"</td>");
				out.println("<td>"+user.getEmail()+"</td>");
				out.println("<td>"+user.getGrade()+"</td>");
				out.println("</tr>");
				}
		}
		
		out.println("</table><br><br>");
			//上一页
			if(pageNow !=1)
			out.println("<a href=searchuser?pageNow="+(pageNow-1)+"&username="+name+"&radtype="+type+" >上一页</a>");
			
			//显示超链接
			for(int j = pageNow;j<=pageNow+pageSize-1; j ++)
			{
				out.println("<a href=searchuser?pageNow="+j+"&username="+name+"&radtype="+type+" > <"+j+"> </a>");
			}
			
			int pageCount = userdao.getPageCount();
			//下一页
			if(pageNow != pageCount)
			out.println("<a href=searchuser?pageNow="+(pageNow+1)+"&username="+name+"&radtype="+type+" >下一页</a><br><br>");

			out.println("</center></body>");
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
