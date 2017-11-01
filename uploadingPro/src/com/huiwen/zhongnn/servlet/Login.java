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
 * ��¼����
 * �ý���ᱻ����ʹ��,������֤ʧ�ܻ᷵�ظý���,����ִ��
 * ��Ƿ���¼Ҳ�᷵�ظý���,����ִ�С�
 */
public class Login extends HttpServlet
{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		//req :���Լ򵥵����Ϊ���ڵõ���Ϣ�Ķ���,���ڴ�����õ�
		//��Ҫ����������
		
		//res:���Լ򵥵����Ϊ�û�������Ϣ�Ķ���,Ҫ�����ǿ϶�Ҫ�ж������趨��Ϣ
		//������session,cookie
		

		//��������������ⷽʽ֮һ
		res.setContentType("text/html;charset=gbk");
		
		//���ڴ���д����Ϣ,Ȼ�û����ĵ���
		PrintWriter out = res.getWriter();
		
		out.println("<html>");
		
		//���ñ�����ɫ
		out.println("<body bgcolor=#CED3FF>");
		
		//����LogoͼƬ,��������
		//	out.println();
		
		//����,������һ��ˮƽ��
		out.println("<center><hr>");
		
		out.println("<h1>��¼����</h1><hr>");
		
		//�õ�error��Ϣ,����1,������ʱ��ô�õ�error��Ϣ��?
		//�����
		//	��¼��֤ʧ��: response.sendRedirect("login?info=nameError")
		//	�ڻ�ӭ������ͼ�Ƿ���¼��response.sendRedirect("login?info=warError")
		/**
		 * info�д������Ϊinfo������,ͬ��һ�����ƿ����ж������,����
		 * info�д����������Ϣ,��ͬһʱ�����ڽ���һ����Ϣ,��if������
		 */
		String info = req.getParameter("info");
		
		if(info != null)//һ��ʼ��¼infoһ��Ϊ��,��������֤ʧ�ܻ�Ƿ���¼��Ϊ��
		{
			if(info.equals("nameError"))
			{
				out.println("�û������������!");
			}
			if(info.equals("warError"))
			{
				out.println("�Ƿ���¼��");
			}
			if(info.equals("begain"))
			{
				out.println("<font color=Gray><sub>�����µ�¼!</sub></font><br>");
			}
			if(info.equals("leave"))
			{
				out.println("<font color=Gray><sub>���Ѱ�ȫ�˳�!</sub></font><br>");
			}
		}
		
		//�û���¼�� �ύ��logincl������
		out.println("<form action=logincl method=post>");
		
		//name=username
		out.println("�û�����<input type=text name=username><br><br>");
		
		//name=passwd
		out.println("���룺	&nbsp;&nbsp;<input type=password name=passwd ><br><br>");
		
		//��ѡ�� �������Զ���¼
		//name=keep
		out.println("<input type=checkbox name=keep valu=2>�����ڲ������µ�¼<br><br>");
		
		//һ�������,�ͻ��ͨ����name��ʶ���û�������Ϣ���͸�logincl,������Ҫ�ֶ�����res���趨��Щ��Ϣ
		out.println("<input type=submit value=����>");
		
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("<input type=reset value=����><br>");
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
