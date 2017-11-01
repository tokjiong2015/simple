package com.huiwen.zhongnn.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyWrapper extends HttpServletRequestWrapper{

	public MyWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
	public String getHeader(String header)
	{
		return "hardcode";
		
	}

}
