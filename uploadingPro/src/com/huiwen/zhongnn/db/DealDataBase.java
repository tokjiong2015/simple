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
	PreparedStatement psta = null;//����PreparedStatement�������������ݿⷢ��SQL��䡣
	
	ResultSet rs = null;//�������������
	
	Connection con = null;

	/**
	 * �����ݿ�����
	 * @return Connection
	 */
	public Connection getConn()
	{	
		//��һ�����ݿ�����,ʹ��JDBC���ӷ�ʽ
		try
		{
			//��������
			Class.forName(DBConfig.DRIVER);
			
			//�������ݿ�����,������
		    return con = DriverManager.getConnection(DBConfig.URL,DBConfig.UName,DBConfig.PWD);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//�ڶ������ݿ�����,ʹ�����ݳ����ӷ�ʽ
	/*	try
		{
			//����һ�������Ļ���
			Context context = new javax.naming.InitialContext();
			
			//ͨ��con�õ�����Դ
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
	 * �ر�����,�ͷ���Դ 
	 */
	public void close()
	{
		try
		{
			//��һ���ر�����,���ͷ���Դ
			if(con != null) {con.close();con =null;}
			
			//�ڶ����ر� �����ݿⷢ��SQL�������,���ͷ���Դ
			if(psta != null){psta.close();psta=null;}
			
			//�������رս����,���ͷ���Դ
			if(rs != null){rs.close();rs=null;}
		} 
		catch (Exception e2)
		{
			e2.printStackTrace();
		}
	}
	
	/**
	 * ������ռλ����ĳ���ݱ��еļ�¼����
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
				
				//ΪPreparedStatement�����͵�sql����е�ռλ����ֵ
				psta.setObject(i+1, values[i]);
				
			}
			
			rs = psta.executeQuery();

			if(rs.next())//��ȷ����
			{
				rowCount = rs.getInt(1);//���صڼ���
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
	 * ����ĳ���ݱ��еļ�¼����
	 */
	public int getRowCount(String sql)
	{
		int rowCount = 0;
		
		try
		{
			con = this.getConn();
			
			psta = con.prepareStatement(sql);
			
			rs = psta.executeQuery();

			if(rs.next())//��ȷ����
			{
				rowCount = rs.getInt(1);//���صڼ���
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
	 * ����һ��ResultSet�û���֤�û��Ϸ���
	 */
	public ResultSet getRS(String sql,Object... values)
	{
		try
		{
			con = this.getConn();
			
			psta = con.prepareStatement(sql);
			
			for(int i = 0;i<values.length;i++)
			{
				
				//ΪPreparedStatement�����͵�sql����е�ռλ����ֵ
				psta.setObject(i+1, values[i]);
				
			}
			
			//ִ�в�ѯ��䷵�ؽ��������
			return rs = psta.executeQuery();
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	//��ѯ��û��ռλ������������
	/**
	 * ���� sql,mapping����List<BookType>�������
	 * sql���ݿ�ִ�����
	 * mapping ��rs��ÿһ�е�����װ��BookType��
	 */
	public List query(String sql,EntityMapping mapping)
	{
		//����List<Object>����������ڽ��ս������
		List<Object> list = new Vector<Object>();
		
		try 
		{
			con = this.getConn();//��������
			
			psta = con.prepareStatement(sql);//����SQL���
			
			rs = psta.executeQuery();//ִ�в�ѯ��䷵�ؽ��������
			
			while(rs.next())//���������
			{	
				//��������е�ÿһ�����ݷ�װ��ĳ��������
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
		
		return list;//��������
	}

	/**
	 * ���ݵĲ�ѯ������ID��ѯ
	 * ���� sql,mapping,values ����List<User>�������
	 * sql���ݿ�ִ�����
	 * mapping ��rs��ÿһ�е�����װ��BookType��
	 * values Ϊռλ����ֵ.
	 */
	public List query(String sql,EntityMapping mapping,Object... values)
	{
		//����List<Object>����������ڽ��ս������
		List<Object> list = new Vector<Object>();
			
			
		try 
		{
			con = this.getConn();//��������
			
			psta = con.prepareStatement(sql);//����SQL���
			
			for(int i = 0;i<values.length;i++)
			{
				
				 //ΪPreparedStatement�����͵�sql����е�ռλ����ֵ
				psta.setObject(i+1, values[i]);
				
			}
			
			rs = psta.executeQuery();//ִ�в�ѯ��䷵�ؽ��������
			
			while(rs.next())//���������
			{
				
				//��������е�ÿһ�����ݷ�װ��ĳ��������
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
		
		return list;//��������
	}
	
	/**
	 * ���ݵ����ӡ�����ɾ�����߼�ɾ�����޸ġ�
	 * ���� SQL ��  values һ����sql��䣬һ����Object����.
	 */
	public int upDate(String sql,Object... values)
	{
		
		int row = 0;
		
		try
		{
			con = this.getConn();
			
			//���������ݿⷢ��Ҫִ�е�sql������
			psta = con.prepareStatement(sql);
			
			for(int i = 0;i<values.length;i++)
			{
				
				//Ϊ�ö����ÿһ��ռλ���� ����values��ֵ��
				psta.setObject(i+1, values[i]);
				
			}
			
			//ִ�����ݿ���ӡ�ɾ�����޸ĵ���䣬����int
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
		
		return row;//����ִ�н��
	}
}
