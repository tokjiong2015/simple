package com.huiwen.zhongnn.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.huiwen.zhongnn.bean.User;

public class UserMapping implements EntityMapping
{
	/**
	 * 通过传入的结果集对象,i++,构造结果集中的所有对象,并返回
	 * 该方法,执行一次返回结果集中的一个对象,而rs一定是放在一个循环中的
	 * 用一个数组来接收所有对象
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
