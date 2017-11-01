package com.huiwen.zhongnn.bean;

public class User
{
	private int userId;
	private String username;
	private String passwd;
	private String email;
	private int grade;
	
	public User()
	{
		
	}
	public User(int userId, String username, String passwd, String email,
			int grade)
	{
		super();
		this.userId = userId;
		this.username = username;
		this.passwd = passwd;
		this.email = email;
		this.grade = grade;
	}
	
	public int getUserId()
	{
		return userId;
	}
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPasswd()
	{
		return passwd;
	}
	public void setPasswd(String passwd)
	{
		this.passwd = passwd;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public int getGrade()
	{
		return grade;
	}
	public void setGrade(int grade)
	{
		this.grade = grade;
	}
}
