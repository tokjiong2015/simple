package com.huiwen.zhongnn.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.Request;

/**
 * @author ZHONG
 * @time 9-12
 */
/**
 * 登录界面
 * 该界面会被反复使用,例如验证失败会返回该界面,重新执行
 * 或非法登录也会返回该界面,重新执行。
 */
public class Login extends HttpServlet
{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		//req :可以简单的理解为用于得到信息的对象,至于从哪里得到
		//就要看参数名称
		
		//res:可以简单的理解为用户发送信息的对象,要发送那肯定要有对象来设定信息
		//可以是session,cookie
		

		//解决中文乱码问题方式之一
		res.setContentType("text/html;charset=gbk");
		
		//从内存中写出信息,然用户看的到。
		PrintWriter out = res.getWriter();
		
		out.println("<html>");
		
		//设置背景颜色
		out.println("<body bgcolor=#CED3FF>");
		
		//设置Logo图片,存在问题
		//	out.println();
		
		//居中,并加了一条水平线
		out.println("<center><hr>");
		
		out.println("<h1>登录界面</h1><hr>");
		
		//得到error信息,问题1,服务器时怎么得到error信息的?
		//解决：
		//	登录验证失败: response.sendRedirect("login?info=nameError")
		//	在欢迎界面试图非法登录：response.sendRedirect("login?info=warError")
		/**
		 * info中存放了名为info的内容,同样一个名称可以有多个内容,这里
		 * info中存放了两种信息,但同一时刻它在接受一个信息,用if来区分
		 */
		String info = req.getParameter("info");
		
		if(info != null)//一开始登录info一定为空,当出现验证失败或非法登录不为空
		{
			if(info.equals("nameError"))
			{
				out.println("用户名或密码错误!");
			}
			if(info.equals("warError"))
			{
				out.println("非法登录！");
			}
			if(info.equals("begain"))
			{
				out.println("<font color=Gray><sub>请重新登录!</sub></font><br>");
			}
			if(info.equals("leave"))
			{
				out.println("<font color=Gray><sub>您已安全退出!</sub></font><br>");
			}
		}
		
		//用户登录表单 提交给logincl来处理
		out.println("<form action=logincl method=post>");
		
		//name=username
		out.println("用户名：<input type=text name=username><br><br>");
		
		//name=passwd
		out.println("密码：	&nbsp;&nbsp;<input type=password name=passwd ><br><br>");
		
		//复选框 两周了自动登录
		//name=keep
		out.println("<input type=checkbox name=keep valu=2>两周内不再重新登录<br><br>");
		
		//一点击发送,就会把通过由name标识的用户输入信息发送给logincl,并不需要手动的用res来设定这些信息
		out.println("<input type=submit value=发送>");
		
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("<input type=reset value=重设><br>");
		out.println("</form>");
		out.println("<hr></center></body>");
		out.println("</html>");
		//res.setContentType("text/plain");
		System.out.println(res.getLocale());
		res.addDateHeader("headerDu", 1234567890);
		
	    res.flushBuffer();
	    //Login Servlet
	    System.out.println("-----------Login Servlet--------------->");
	    System.out.println(res.getBufferSize());
	    System.out.println(res.isCommitted());
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		this.doGet(request, response);
	}
}
