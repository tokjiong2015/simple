package com.huiwen.zhongnn.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.huiwen.zhongnn.bean.User;
import com.huiwen.zhongnn.dao.UserDAO;

public class Welcom extends HttpServlet
{
	/*
	 * 如果Session(20秒)时间到了会怎么样?
	 * 1.验证成功,到了主界面,没有进入Welcom界面,这是Session时间到了
	 * 这是如果Cookie中没有保存用户名和密码,这回显示非法登录
	 * 如果Cookie保存了,这时会点击进入Welcom界面是自动去验证Cookie中的信息,即要双击两次
	 * 才能进入welcom界面
	 *
	 * 
	 */
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
		String val = (String)hs.getAttribute("pass");
		String u =(String) hs.getAttribute("uname");
		String pwd = (String)hs.getAttribute("pwd");
		
		System.out.println(val);
		
		String myname = "";
		String mypwd = "";
		
		//没有经过验证,服务器端Session中也就没有信息
		if(val == null)
		{
			//非法登录
			
			//如果session没有用户信息即非法登录,在看看有没有cookie信息,如果有,者2周是不用登录的
			Cookie[] allCookies = request.getCookies();
			
			int i = 0;
			
			//如果allCookies不为空
			if(allCookies!=null)
			{
				//从中取出cookie
				for(i = 0;i<allCookies.length;i++)
				{
					//依次取出
					Cookie temp = allCookies[i];
					
					System.out.println("=========");
					
					if(temp.getName().equals("myname"))
					{
						//得到cookie的值
						myname = temp.getValue();
						
						System.out.println(myname+"keep 不为 null");
					}
					else if(temp.getName().equals("mypwd"))
					{
						//得到cookie的值
						mypwd = temp.getValue();
						
						System.out.println(mypwd);
					}
					
					System.out.println("============");
				}
				//Cookie中myname和mypwd值中不为空,还不行,还要去自动去验证登录，登录成功返回主界面
				
				System.out.println(!myname.equals("")&&!mypwd.equals(""));
				
				if(!myname.equals("")&&!mypwd.equals(""))
				{
					System.out.println("到loguicl去验证");
					//到loguicl去验证
					response.sendRedirect("logincl?username="+myname+"&passwd="+mypwd);
					return;//立即返回,不往下走
				}
			}
			
			response.sendRedirect("login?info=warError");
			return;
		}
		
		out.println("<html>");
		out.println("<body bgcolor=#CED3FF><center>");
		
		out.println("欢迎您:"+u+" <hr>");
		//	out.println("<img src =images/3.jpg><br>");
		out.println("<h1>管理用户</h1>");
		
		out.println("<br><a href=main>返回主界面</a>");
		out.println("<a href=login?info=begain>返回重新登录</a>");
		out.println("<a href=leave>安全退出</a><hr>");
		
		//添加网页访问次数的功能
		
	
		//==========================分页功能==========================
		int pageSize = 5;//一页显示几条记录 
		int pageNow = 1;//希望显示第几页
		
		//动态的接收pageNow
		String sPageNow = request.getParameter("pageNow");
		
		if(sPageNow != null)
		{
			pageNow = Integer.parseInt(sPageNow);
		}
		
		//调用UserDAO
		UserDAO udao = new UserDAO();
		
		List all = udao.getResultByPage(pageNow, pageSize);
		
		out.println("<table border =1>");
		out.println("<tr bgcolor=pink><th>用户ID</th><th>用户名称</th><th>用户密码</th><th>邮箱</th><th>级别</th><th>修改用户</th><th>删除用户</th></tr>");
		
		//定义一个颜色数组
		String[] myCol = {"silver","pink"};
		
		for(int i = 0;i<all.size();i++)
		{
			User user =(User) all.get(i);
			out.println("<tr bgcolor="+myCol[i%2]+">");
			out.println("<td>"+user.getUserId()+"</td>");
			out.println("<td>"+user.getUsername()+"</td>");
			out.println("<td>"+user.getPasswd()+"</td>");
			out.println("<td>"+user.getEmail()+"</td>");
			out.println("<td>"+user.getGrade()+"</td>");
			out.println("<td><a href=updateuser?userId="+user.getUserId()+"&userNmae="+user.getUsername()+"&pwd="+user.getPasswd()+"&email="+user.getEmail()+"&grade="+user.getGrade()+">修改用户</a></td>");
			out.println("<td><a href=deleteuser?userId="+user.getUserId()+" onclick=\"return window.confirm('您确认要删除该用户吗?')\">删除用户</a></td>");
			out.println("</tr>");
			}
			
			out.println("</table><br><br>");
			
			//上一页
			if(pageNow !=1)
			out.println("<a href=welcom?pageNow="+(pageNow-1)+" >上一页</a>");
			
			//显示超链接
			for(int j = pageNow;j<=pageNow+pageSize-1; j ++)
			{
				out.println("<a href=welcom?pageNow="+j+" > <"+j+"> </a>");
			}
			
			int pageCount = udao.getPageCount();
			//下一页
			if(pageNow != pageCount)
			out.println("<a href=welcom?pageNow="+(pageNow+1)+" >下一页</a><br><br>");
			
			//指定跳转到某页,实际上是一个表单
			out.println("<form action=welcom>");
			out.println("<input type=text name=pageNow size=10>");
			out.println("<input type=submit value=go>");
			out.println("</form><hr>");
			
			out.println("<br>该网页被访问了"+this.getServletContext().getAttribute("visitTimes").toString()+"次<br>");
	//		out.println("您的IP="+request.getRemoteAddr()+"<br>");
	//		out.println("您的机器名="+request.getRemoteHost()+"<br>");
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
