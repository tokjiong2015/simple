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
 * 对User对象的操作,包括增加、删除、修改、查找、分页查找
 */
public class UserDAO
{	
	//在分页显示界面要用到
	private int pageCount = 0;//共有几页
	
	//处理数据库对象
	DealDataBase dealDateBase = new DealDataBase();
	
	/**
	 * 修改用户信息
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
			//修改成功
			b = true;
		}
		
		return b;
	}
	
	/**
	 * 删除用户
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
			//删除成功
			b = true;
		}
		return b;
	}
	
	/**
	 * 增加用户信息
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
			//修改成功
			b = true;
		}
		
		return b;
	}
	
	
	//返回pageCount 在分页界面要用到该PageCount
	public int getPageCount()
	{
		return this.pageCount;
	}
	
	/**
	 * 分页显示
	 * @param pageNow
	 * @param pageSize
	 * @return List
	 */
	public List getResultByPage(int pageNow,int pageSize)
	{
		List userListByPage = null;
		
		int rowCount = 0;//共有几条记录
		
		String sql = "select count(*) from users";
		
		rowCount = dealDateBase.getRowCount(sql);
			
		//计算pageCount
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
	 * 验证用户
	 * 需要用到结果集对象
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
	 * 精确查找和模糊查找
	 * @param type 查找类型 1表示模糊查找,2表示精确查找
	 * @param name 查找对象的名称
	 * @param pageNow 当前第几页
	 * @param pageSize	每页显示页数
	 * @return
	 */
	public List getResultByPage(String type,String name,int pageNow,int pageSize)
	{
		List userListByPage = null;
		
		int type_int = 0;//初始化type_int=0
		
		if(type != null)
		{
			//如果type不为空,即用户点击了精确或模糊查找,则让该String类型转换为int类型
			//如果type为空则让 type_int 为0,下面代码去处理(返回一个空数组)
			type_int = Integer.parseInt(type);//1代表模糊查找,2代表精确查找
		}
		
		int rowCount = 0;//共有几条记录
					
		String sql = "select count(*) from users where username=?";
		
		//根据username,返回总的记录数
		rowCount = dealDateBase.getRowCount(sql,name);
			
		//计算pageCount
		if(rowCount%pageSize == 0)
		{
			pageCount = rowCount/pageSize;
		}
		else
		{
			pageCount = rowCount/pageSize + 1;	
		}
		
		String sql2 ="";
		
		//2表示是精确查找,返回精确查找后的数组
		if(type_int == 2)
		{
			sql2 = "select top "+pageSize+"  * from users where userId not in " +
			"(select top "+ pageSize*(pageNow-1)+" userId from users where username=?) and username=?";
			
			return userListByPage = dealDateBase.query(sql2,new UserMapping(),name,name);
		}
		//1表示是模糊查找,返回模糊查找后的数组
		if(type_int == 1)
		{
			sql2 = "select top "+pageSize+"  * from users where userId not in " +
			"(select top "+ pageSize*(pageNow-1)+" userId from users where username like '%"+name+"%') and username like '%"+name+"%'";
			
			System.out.println(sql2);
			
			return userListByPage = dealDateBase.query(sql2,new UserMapping());
		}
		//0表示 没有出入type类型,即用户第一次登陆该查询界面,返回一个空数组
		if(type_int == 0)
		{
			return null;
		}
		return null;
	}
	
	/**
	 * 查找
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
