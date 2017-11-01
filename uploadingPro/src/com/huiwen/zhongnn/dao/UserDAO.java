package com.huiwen.zhongnn.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.huiwen.zhongnn.bean.User;
import com.huiwen.zhongnn.db.DealDataBase;
import com.huiwen.zhongnn.mapping.UserMapping;

/**
 * @author ZHONGNN
 * @time 9-12
 */

/**
 * ��User����Ĳ���,�������ӡ�ɾ�����޸ġ����ҡ���ҳ����
 */
public class UserDAO
{	
	//�ڷ�ҳ��ʾ����Ҫ�õ�
	private int pageCount = 0;//���м�ҳ
	
	//�������ݿ����
	DealDataBase dealDateBase = new DealDataBase();
	
	/**
	 * �޸��û���Ϣ
	 * @param id
	 * @param name
	 * @param pwd
	 * @param email
	 * @param grade
	 * @return boolean
	 */
	public boolean updateUser(String id,String name,String pwd,String email,String grade)
	{
		boolean b = false;
		
		String sql = "update users set username =?, passwd =?,email=?, grade =? where userId= ?";
		
		int num = dealDateBase.upDate(sql,name,pwd,email,grade,id);
			
		if(num == 1)
		{
			//�޸ĳɹ�
			b = true;
		}
		
		return b;
	}
	
	/**
	 * ɾ���û�
	 * @param id
	 * @return
	 */
	public boolean deleteUser(String id)
	{
		boolean b = false;
		
		String sql = "delete from users where userId= ?";
			
		int num = dealDateBase.upDate(sql, id);
			
		if(num == 1)
		{
			//ɾ���ɹ�
			b = true;
		}
		return b;
	}
	
	/**
	 * �����û���Ϣ
	 * @param name
	 * @param pwd
	 * @param email
	 * @param grade
	 * @return boolean
	 */
	public boolean addUser(String name,String pwd,String email,String grade)
	{
		boolean b = false;
		
		int currMax =5;
		
		//String selectSql ="select max(userId) from users";
		
		//List test =dealDateBase.query(selectSql,new UserMapping());
		
		String sql = "INSERT INTO `users` (`userId`, `username`, `passwd`, `email`, `grade`) VALUES ('"+currMax+"', ?, ?, ?, ?);";
		
		System.out.println("insert query"+sql);
		
		int num = dealDateBase.upDate(sql,name,pwd,email,grade);
			
		if(num == 1)
		{
			//�޸ĳɹ�
			b = true;
		}
		
		return b;
	}
	
	
	//����pageCount �ڷ�ҳ����Ҫ�õ���PageCount
	public int getPageCount()
	{
		return this.pageCount;
	}
	
	/**
	 * ��ҳ��ʾ
	 * @param pageNow
	 * @param pageSize
	 * @return List
	 */
	public List getResultByPage(int pageNow,int pageSize)
	{
		List userListByPage = null;
		
		int rowCount = 0;//���м�����¼
		
		String sql = "select count(*) from users";
		
		rowCount = dealDateBase.getRowCount(sql);
			
		//����pageCount
		if(rowCount%pageSize == 0)
		{
			pageCount = rowCount/pageSize;
		}
		else
		{
			pageCount = rowCount/pageSize + 1;	
		}
		
		String sql2 = "select top "+pageSize+"  * from users where userId not in " +
				"(select top "+ pageSize*(pageNow-1)+" userId from users)";
		
		return userListByPage = dealDateBase.query(sql2,new UserMapping());
	}
	
	/**
	 * ��֤�û�
	 * ��Ҫ�õ����������
	 * @param user
	 * @return boolean
	 */
	public boolean checkUser(User user)
	{
		ResultSet rs = null;
		
		boolean b = false;
		
		try
		{
			String sql = "select passwd from users where username= ?";
			
			rs = dealDateBase.getRS(sql, user.getUsername());
			
			if(rs.next())
			{
				String dbPasswd = rs.getString(1);
				
				if(dbPasswd.equals(user.getPasswd()))
				{
					b = true;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null){rs.close();rs=null;}
				
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
		return b;
	}
	
	
	/**
	 * ��ȷ���Һ�ģ������
	 * @param type �������� 1��ʾģ������,2��ʾ��ȷ����
	 * @param name ���Ҷ��������
	 * @param pageNow ��ǰ�ڼ�ҳ
	 * @param pageSize	ÿҳ��ʾҳ��
	 * @return
	 */
	public List getResultByPage(String type,String name,int pageNow,int pageSize)
	{
		List userListByPage = null;
		
		int type_int = 0;//��ʼ��type_int=0
		
		if(type != null)
		{
			//���type��Ϊ��,���û�����˾�ȷ��ģ������,���ø�String����ת��Ϊint����
			//���typeΪ������ type_int Ϊ0,�������ȥ����(����һ��������)
			type_int = Integer.parseInt(type);//1����ģ������,2����ȷ����
		}
		
		int rowCount = 0;//���м�����¼
					
		String sql = "select count(*) from users where username=?";
		
		//����username,�����ܵļ�¼��
		rowCount = dealDateBase.getRowCount(sql,name);
			
		//����pageCount
		if(rowCount%pageSize == 0)
		{
			pageCount = rowCount/pageSize;
		}
		else
		{
			pageCount = rowCount/pageSize + 1;	
		}
		
		String sql2 ="";
		
		//2��ʾ�Ǿ�ȷ����,���ؾ�ȷ���Һ������
		if(type_int == 2)
		{
			sql2 = "select top "+pageSize+"  * from users where userId not in " +
			"(select top "+ pageSize*(pageNow-1)+" userId from users where username=?) and username=?";
			
			return userListByPage = dealDateBase.query(sql2,new UserMapping(),name,name);
		}
		//1��ʾ��ģ������,����ģ�����Һ������
		if(type_int == 1)
		{
			sql2 = "select top "+pageSize+"  * from users where userId not in " +
			"(select top "+ pageSize*(pageNow-1)+" userId from users where username like '%"+name+"%') and username like '%"+name+"%'";
			
			System.out.println(sql2);
			
			return userListByPage = dealDateBase.query(sql2,new UserMapping());
		}
		//0��ʾ û�г���type����,���û���һ�ε�½�ò�ѯ����,����һ��������
		if(type_int == 0)
		{
			return null;
		}
		return null;
	}
	
	/**
	 * ����
	 * @param pageNow
	 * @param pageSize
	 * @return List
	 */
	public List search(String username,int radSearch)
	{
		List userListByName = null;
				
		String sql1 ="";
		
		if(radSearch == 2)
		{
			sql1 = "select  * from users where username =? ";
		}
		if(radSearch == 1)
		{
			sql1 = "select * from users where username like %?%";
		}
				
		return userListByName = dealDateBase.query(sql1,new UserMapping(),username);
	}
}
