package com.huiwen.zhongnn.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapping
{
	/**
	 *ӳ�䣬����һ������
	 *�÷���ͨ������Ľ��������,�����ݿ��е�ÿһ��
	 *��ӳ��Ϊһ������,ͨ�����췽��,�������� 
	 */
	public Object mapping(ResultSet rs);
	
}
