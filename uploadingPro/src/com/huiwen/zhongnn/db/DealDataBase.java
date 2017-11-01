package com.huiwen.zhongnn.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

//import javax.naming.Context;
//import javax.sql.*;

import com.huiwen.zhongnn.mapping.EntityMapping;

public class DealDataBase
{
	PreparedStatement psta = null;//创建PreparedStatement对象用于向数据库发送SQL语句。
	
	ResultSet rs = null;//创建结果集对象
	
	Connection con = null;

	/**
	 * 与数据库连接
	 * @return Connection
	 */
	public Connection getConn()
	{	
		//第一种数据库连接,使用JDBC连接方式
		try
		{
			//加载驱动
			Class.forName(DBConfig.DRIVER);
			
			//创建数据库连接,并返回
		    return con = DriverManager.getConnection(DBConfig.URL,DBConfig.UName,DBConfig.PWD);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//第二种数据库连接,使用数据池连接方式
	/*	try
		{
			//创建一个上下文环境
			Context context = new javax.naming.InitialContext();
			
			//通过con得到数据源
			DataSource ds = (DataSource) context.lookup("java:comp/env/muserSource");
			
			return	con = ds.getConnection();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	*/	
		return null;
	}
	
	/**
	 * 关闭连接,释放资源 
	 */
	public void close()
	{
		try
		{
			//第一步关闭连接,并释放资源
			if(con != null) {con.close();con =null;}
			
			//第二步关闭 向数据库发送SQL请求对象,并释放资源
			if(psta != null){psta.close();psta=null;}
			
			//第三步关闭结果集,并释放资源
			if(rs != null){rs.close();rs=null;}
		} 
		catch (Exception e2)
		{
			e2.printStackTrace();
		}
	}
	
	/**
	 * 返回有占位符的某数据表中的记录条数
	 */
	public int getRowCount(String sql,Object... values)
	{
		int rowCount = 0;
		
		try
		{
			con = this.getConn();
			
			psta = con.prepareStatement(sql);
			
			for(int i = 0;i<values.length;i++)
			{
				
				//为PreparedStatement对象发送的sql语句中的占位符赋值
				psta.setObject(i+1, values[i]);
				
			}
			
			rs = psta.executeQuery();

			if(rs.next())//精确到行
			{
				rowCount = rs.getInt(1);//返回第几列
			}
				
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.close();
		}
		return rowCount;
	}
	
	/**
	 * 返回某数据表中的记录条数
	 */
	public int getRowCount(String sql)
	{
		int rowCount = 0;
		
		try
		{
			con = this.getConn();
			
			psta = con.prepareStatement(sql);
			
			rs = psta.executeQuery();

			if(rs.next())//精确到行
			{
				rowCount = rs.getInt(1);//返回第几列
			}
				
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.close();
		}
		return rowCount;
	}
	
	/**
	 * 返回一个ResultSet用户验证用户合法性
	 */
	public ResultSet getRS(String sql,Object... values)
	{
		try
		{
			con = this.getConn();
			
			psta = con.prepareStatement(sql);
			
			for(int i = 0;i<values.length;i++)
			{
				
				//为PreparedStatement对象发送的sql语句中的占位符赋值
				psta.setObject(i+1, values[i]);
				
			}
			
			//执行查询语句返回结果集对象
			return rs = psta.executeQuery();
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	//查询出没有占位符的所有数据
	/**
	 * 参数 sql,mapping返回List<BookType>数组对象
	 * sql数据库执行语句
	 * mapping 将rs中每一行的语句封装到BookType中
	 */
	public List query(String sql,EntityMapping mapping)
	{
		//创建List<Object>数组对象，用于接收结果集。
		List<Object> list = new Vector<Object>();
		
		try 
		{
			con = this.getConn();//创建连接
			
			psta = con.prepareStatement(sql);//发送SQL语句
			
			rs = psta.executeQuery();//执行查询语句返回结果集对象
			
			while(rs.next())//遍历结果集
			{	
				//将结果集中的每一行数据封装到某个对象中
				list.add(mapping.mapping(rs));
				
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally
		{
			this.close();
		}
		
		return list;//返回数组
	}

	/**
	 * 数据的查询、根据ID查询
	 * 参数 sql,mapping,values 返回List<User>数组对象
	 * sql数据库执行语句
	 * mapping 将rs中每一行的语句封装到BookType中
	 * values 为占位符赋值.
	 */
	public List query(String sql,EntityMapping mapping,Object... values)
	{
		//创建List<Object>数组对象，用于接收结果集。
		List<Object> list = new Vector<Object>();
			
			
		try 
		{
			con = this.getConn();//创建连接
			
			psta = con.prepareStatement(sql);//发送SQL语句
			
			for(int i = 0;i<values.length;i++)
			{
				
				 //为PreparedStatement对象发送的sql语句中的占位符赋值
				psta.setObject(i+1, values[i]);
				
			}
			
			rs = psta.executeQuery();//执行查询语句返回结果集对象
			
			while(rs.next())//遍历结果集
			{
				
				//将结果集中的每一行数据封装到某个对象中
				list.add(mapping.mapping(rs));
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally
		{
			this.close();
		}
		
		return list;//返回数组
	}
	
	/**
	 * 数据的增加、物理删除、逻辑删除、修改、
	 * 参数 SQL 和  values 一个是sql语句，一个是Object数组.
	 */
	public int upDate(String sql,Object... values)
	{
		
		int row = 0;
		
		try
		{
			con = this.getConn();
			
			//创建向数据库发送要执行的sql语句对象
			psta = con.prepareStatement(sql);
			
			for(int i = 0;i<values.length;i++)
			{
				
				//为该对象的每一个占位符赋 数组values的值。
				psta.setObject(i+1, values[i]);
				
			}
			
			//执行数据库添加、删除、修改等语句，返回int
			row = psta.executeUpdate();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.close();
		}
		
		return row;//返回执行结果
	}
}
