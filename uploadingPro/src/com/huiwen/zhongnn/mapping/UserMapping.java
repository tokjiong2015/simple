package com.huiwen.zhongnn.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.huiwen.zhongnn.bean.User;

public class UserMapping implements EntityMapping
{
	/**
	 * ͨ������Ľ��������,i++,���������е����ж���,������
	 * �÷���,ִ��һ�η��ؽ�����е�һ������,��rsһ���Ƿ���һ��ѭ���е�
	 * ��һ���������������ж���
	 */
	public User mapping(ResultSet rs)
	{
		int i = 1;
		
		User user = null;
		
		try
		{
			user = new User
					(
					rs.getInt(i++), rs.getString(i++), 
					rs.getString(i++), rs.getString(i++),
					rs.getInt(i++)
					
					);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return user;
	}

}
