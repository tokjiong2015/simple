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

		//req :��ÿͻ���(�����)����Ϣ
		
		//res����ͻ��˷�����Ϣ

		//��������
		response.setContentType("text/html;charset=gbk");
		
		PrintWriter out = response.getWriter();
		
		//�õ��ڷ������˵�session�ڴ��е���Ϣ(3������),����Ϣ��
		//�ڵ�¼�ɹ�ʱ,���õ�
		HttpSession hs = request.getSession(true);
		String u =(String) hs.getAttribute("uname");
	//	List myList = (List)hs.getAttribute("myList");
		
		out.println("<html>");
		out.println("<body bgcolor=#CED3FF><center>");
		
		out.println("��ӭ��:"+u+" <hr>");
		//	out.println("<img src =images/3.jpg><br>");
		out.println("<h1>��ѯ�û�</h1>");
		
		out.println("<br><a href=main>����������</a>");
		out.println("<a href=login?info=begain>�������µ�¼</a>");
		out.println("<a href=leave>��ȫ�˳�</a><hr>");
		
		String name = request.getParameter("username");
		System.out.println(name);
		
		String type = request.getParameter("radtype");
		System.out.println(type);//1��ʾģ������,2��ʾ��ȷ����
		
	
		//==========================��ҳ����==========================
		int pageSize = 5;//һҳ��ʾ������¼ 
		int pageNow = 1;//ϣ����ʾ�ڼ�ҳ
		
		UserDAO userdao  = new UserDAO();
		
		
		
		//��̬�Ľ���pageNow
		String sPageNow = request.getParameter("pageNow");
		
		if(sPageNow != null)
		{
			pageNow = Integer.parseInt(sPageNow);
		}

		out.println("<form action=searchuser>");
		out.println("�������û�����<input type=text name=username>");
		out.println("<input type=submit value=�� ><br>");
		out.println("<input type=radio name=radtype value=1 >ģ������");
		out.println("<input type=radio name=radtype value=2 checked>��ȷ���� ");
		out.println("</form>");
		/*
		List list = null;
		
		if(myList != null)
		{
			list =userdao.getResusltByPage(myList,pageNow,pageSize);
			
		}*/
		
		List all = userdao.getResultByPage(type,name,pageNow, pageSize);
		
		out.println("<table border =1>");
		
		out.println("<tr bgcolor=pink><th>�û�ID</th><th>�û�����</th><th>�û�����</th><th>����</th><th>����</th></tr>");
		
		//����һ����ɫ����
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
			//��һҳ
			if(pageNow !=1)
			out.println("<a href=searchuser?pageNow="+(pageNow-1)+"&username="+name+"&radtype="+type+" >��һҳ</a>");
			
			//��ʾ������
			for(int j = pageNow;j<=pageNow+pageSize-1; j ++)
			{
				out.println("<a href=searchuser?pageNow="+j+"&username="+name+"&radtype="+type+" > <"+j+"> </a>");
			}
			
			int pageCount = userdao.getPageCount();
			//��һҳ
			if(pageNow != pageCount)
			out.println("<a href=searchuser?pageNow="+(pageNow+1)+"&username="+name+"&radtype="+type+" >��һҳ</a><br><br>");

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
