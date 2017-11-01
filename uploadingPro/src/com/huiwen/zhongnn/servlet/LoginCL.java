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
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.huiwen.zhongnn.bean.User;
import com.huiwen.zhongnn.dao.UserDAO;

/**
 * @author ZHONGNN
 * @time 9-9
 * ����get����,�û���¼������
 */
public class LoginCL extends HttpServlet
{
	//��дinit����
	/**
	 * ��ȡ���Ѵ����ļ��е���Ϣ��ȡ���ڴ���
	 * д�������ڴ��е���Ϣд���������ļ���
	 */
	public void init()
	{
		try
		{
			FileReader fr = new FileReader("D:\\save.txt");
			
			//����һ�����ڴ����ļ��ж�ȡ��Ϣ�Ķ���
			BufferedReader br = new BufferedReader(fr);
			
			//�Ѵ����ļ��е���Ϣ��ȡ���ڴ���
			String numVal = br.readLine();
			System.out.println("Here number value is"+numVal);
			br.close();

			//times �����������û����ʵ�
			//��timesֵ�ŵ�servletcontent
			//servletcontent:�ڷ�������,���е�¼�����û�������ڴ�ռ�
			//��init()����,��tomcat�������������,ֻ������һ��
			//�Ѵ��ļ��ж�ȡ����ֵ,��visitTimes��ʶ,�ŵ�serveltContex�ڴ���,
			//�ڳ��ε���LoginCLʱִ��һ��,�ڶ��ε���LoinCLʱ��������
			this.getServletContext().setAttribute("visitTimes", numVal);
			System.out.println(this.getInitParameter("hiname"));
			
			System.out.println("init ������");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//��дdestroy����,�ر�tomcat�Ǳ�ִ��һ��
	public void destroy()
	{
		try
		{
			//���ڴ��е���Ϣд�ص��ļ���
			FileWriter fw = new FileWriter("D:\\save.txt");
			
			//����һ���ܰ��ڴ��е���Ϣд�ص��ļ��еĶ���
			BufferedWriter bw = new BufferedWriter(fw);
			
			//���ļ���д��һ������,��д�����ʲô��Ϣ��
			//д�����servletContext�ڴ��е���visitTiems��ʶ����Ϣ
			bw.write(this.getServletContext().getAttribute("visitTimes").toString());
			
			bw.close();
			
			System.out.println("destroy ������");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * ��¼�ɹ����ļ�������
	 * 1.���û���,������,ͨ��Cookie�������浽�ͻ���
	 * 2.�ڷ�����,����һ���ڴ�ռ�Session,������3������(pass,name,pwd)
	 * 3.ȡ����һ�μ�����serveltContext�е�ֵ,�Ӽ�,�ڷŻص����ڴ�ռ���
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		//req :�����Ϣ
		
		//res��������Ϣ
		
		//���������������
		response.setContentType("text/html;charset=gbk");

		PrintWriter out = response.getWriter();

		//����ͨ����¼��������Ͱ�ť�Զ����͹������û�����������Ϣ 
		String u = request.getParameter("username");//��仰ָ���ǵõ�������ֵ,����ͬ���͵�u������
		String p = request.getParameter("passwd");
		User user = new User();
		user.setUsername(u);
		user.setPasswd(p);
		
		UserDAO udao = new UserDAO();
				
		//����UserDAO�е���֤����
		if(udao.checkUser(user))
		{
			//�����ʾ�û��Ϸ�
			
			//���ж��û��Ƿ�ѡ����keep��ʶ�ĸ�ѡ��,ѡ����֤�ɹ�,���ͨ�����Զ����͸���Ϣ
			String keep = request.getParameter("keep");//���� keep = 2
			
			System.out.println("keep ��ֵΪ:" + keep);
			
			if(keep != null)
			{
				//ʲô��cookie
				/**
				 * �������ڿͻ��˱�����û���Ϣ,�����¼��
				 * �����....����cookie
				 * ��������,�û������¼,�ѵ�¼��������,��ѡ����Ϣ
				 * ���뵽������,��֤�ɹ�,����û��������뱣�浽��
				 * �ͻ���,�ڷ���������Ҫ��ʱ����Դӿͻ��˶�ȡ
				 * 
				 * ��;�� A.�����û���������,��һ��ʱ���ڲ������µ�¼
				 * 		 B.��¼�û�������վ��ϲ��(���ޱ�������)
				 *       C.��վ�ĸ��Ի�,���綨����վ�ķ���,���� 
				 * 
				 * ���裺
				 * 1.�ڷ���������
				 * 		Cookie c = new Coolie(String name,String val);
				 * 
				 * 2.����Cookie��ӵ��ͻ���
				 * 		response.addCookie(c);
				 * 
				 * 3.�ӿͻ��˶�ȡ��Cookie����������
				 * 		request.getCookies();
				 * 
				 * ע�⣺Cookie����:��Cookie������ʼ����ʱ�����,���ܸ�Cookie�Ƿ񱻷���
				 * 
				 * 		Cookie�Ǳ����ڿͻ��˵�,����A��cookie������A�ͻ���,B��cookie������B�ͻ���,�˴˲�Ӱ��
				 */
				
				//���Ϸ��û��������뱣���ڿͻ���(cookie����)
				//1.����
				Cookie name = new Cookie("myname",u);
				Cookie pwd = new Cookie("mypwd",p);
				
				//2.����ʱ�� 2��
				name.setMaxAge(14*24*3600);
				pwd.setMaxAge(14*24*3600);
				
				//3.��д���ͻ���
				response.addCookie(name);
				response.addCookie(pwd);
			}
			
			/**
			 * ʲô��session?
			 * 
			 * 	       ���û��������,����ĳ����վʱ,�������ͻ��ڷ������ڴ�
			 * Ϊ�����������һ���ռ�,�ÿռ䱻����������ռ,���ڲ�ͬҳ�湲������
			 * 
			 * 		��;��
			 * 			A.���ﳵ (����ͬһ��������),��������Ա���,�Ա����ķ������ᴴ��һ��session�ڴ�ռ�,��������������һ��Session
			 * 			B.�����¼�û�����Ϣ����ĳЩ���ݷ��뵽session�й�ͬһ�û��ĸ���ҳ��ʹ��
			 * 			C. ��ֹ�û��Ƿ���¼��ĳ��ҳ��
			 * 
			 * 		���ʹ��session
			 * 		1.�õ�session
			 * 		HttpSession hs = request.getSession(true);
			 *      2.��session�������
			 *      hs.setAttribute(String name,Object val);
			 *      3.��session�еõ�ĳ������
			 *      String name = hs.getAttribute(String name);
			 *      4.��session��ɾ��ĳ������
			 *      hs.removeAttribute(String name);
			 * 
			 * ע�⣺
			 * 		session��Ϣ�Ǳ����ڷ������е�,����ͻ���A��session�����ڷ������еķ����һ��A�ڴ�ռ�,
			 * 		�ͻ���B��session�����ڸ÷������еķ����һ��B�ڴ�ռ�,���Ƕ��ڷ������ڴ�ռ�,�����Ǳ˴��Ƕ�����
			 * 		�໥��û�й����ġ�
			 * 	
			 * 		session�Ĵ���ʱ�䣺session�ķ���ʱ��,�������ۼ�ʱ��
			 * 
			 * 		ͬһ���ͻ���,˫����һ����վ,��˫����ͬһ����վ,��������Ϊ���ǿ���������ͬ��
			 * 		session�ڴ�ռ�
			 * 		
			 * 		session�ĸ�������Ҫռ�÷������ڴ��,����򲻵��ѲŻ�ʹ��
			 */
			
			//����֤�ɹ�����Ϣ,д��session
			//1.�õ�session
			HttpSession hs = request.getSession(true);
			//�޸�session�Ĵ���ʱ��
			hs.setMaxInactiveInterval(60*60);//��
			
			//session�б�����3������ pass/uname/pwd
			hs.setAttribute("pass", "ok");
			hs.setAttribute("uname", u);
			hs.setAttribute("pwd", p);
			
			//��¼��֤�ɹ���servletContext�ڴ��е�visiTime����Ӧ��ֵȡ��
			String times = this.getServletContext().getAttribute("visitTimes").toString();
			
			//��times��ֵ++�����·Żص�seveltContext�ڴ���
			this.getServletContext().setAttribute("visitTimes", (Integer.parseInt(times)+1)+"");
			
			//trial area for servlet context
			System.out.println("Servlet Context Start----------------------------------------------------->");
			System.out.println("servlet context name"+this.getServletContext().getServletContextName());
			System.out.println("servlet get real path"+this.getServletContext().getRealPath("LoginCl"));
			System.out.println("servlet get real path"+this.getServletContext().getRealPath("index.jsp"));
			System.out.println("servlet get resource paths"+this.getServletContext().getResourcePaths("/"));
			System.out.println("servlet get resource"+this.getServletContext().getResourceAsStream("/"));	
			System.out.println("MIME Type"+this.getServletContext().getMimeType("/index.jsp"));	
			RequestDispatcher rd = this.getServletContext().getNamedDispatcher("LoginCL");
			System.out.println(rd);
			System.out.println("Servlet Context End------------------------------------------------------->");
			
			System.out.println("Servlet Config Start----------------------------------------------------->");
			//trial area for servlet config
			System.out.println(this.getServletConfig().getServletName());
			System.out.println(this.getServletConfig().getClass());
			System.out.println("Servlet Config End------------------------------------------------------->");
			
			System.out.println("Request Scope Attributes----------------------------------------------------->");
			request.setAttribute("try", "tryvalue");
			System.out.println(request.getAttribute("try"));
			System.out.println("char encoding"+request.getCharacterEncoding());
			System.out.println("content length"+request.getContentLength());
			System.out.println(request.getContentType());
			System.out.println(request.getLocale());
			
			System.out.println(request.getProtocol());
			System.out.println(request.getScheme());
			System.out.println(request.isSecure());
			
			String [] queryValue = request.getParameterValues("username");
			for(String q:queryValue)
			{
				System.out.println(q);
			}
			
			Map<String,String[]> queryResult = request.getParameterMap();
			for(Map.Entry<String, String[]> a:queryResult.entrySet())
			{
				System.out.println(a.getKey() + a.getValue().length);
			}
			
			
			System.out.println(request.getServerName());
			
			
			System.out.println("HttpServletRequest------------------------------------------------------------->");
			System.out.println(request.getHeader("Content-Type"));
			
			
			System.out.println("Security Related--------------------------------------------------------------->");
			System.out.println(request.getAuthType());
			System.out.println(request.getRemoteUser());
			System.out.println(request.getUserPrincipal());
			
			System.out.println("Cookies--------------------------------------------------------------->");
			Cookie [] c = request.getCookies();
			System.out.println(c);
			
			System.out.println("Session Management--------------------------------------------------------------->");
			System.out.println(request.getRequestedSessionId());
			System.out.println(request.getSession());
			System.out.println(request.isRequestedSessionIdFromCookie());
			System.out.println(request.isRequestedSessionIdFromURL());
			
			System.out.println("Response Interface--------------------------------------------------------------->");
			System.out.println(response.containsHeader("Content-Type"));
			
			
			MyWrapper requestWrapper = new MyWrapper(request);
			
			//��ת
			//response.sendRedirect("main");//д��Ҫ����servlet���Ǹ�url
			
			RequestDispatcher rdd =getServletContext().getNamedDispatcher("Main");
			rdd.forward(requestWrapper, response);
			
			}
			else
			{
				//˵���û���������
				//���Ϸ�
				//��ת
				response.sendRedirect("login?info=nameError");//д��Ҫ����servlet���Ǹ�url
			}			
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		System.out.println("I am in doPOST method");
		System.out.println(request.getContentLength());
		this.doGet(request, response);
	}

}
