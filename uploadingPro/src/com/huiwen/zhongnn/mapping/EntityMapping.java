package com.huiwen.zhongnn.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapping
{
	/**
	 *映射，返回一个对象
	 *该方法通过初入的结果集对象,让数据库中的每一行
	 *都映射为一个对象,通过构造方法,构建对象 
	 */
	public Object mapping(ResultSet rs);
	
}
