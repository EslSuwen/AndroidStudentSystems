package com.cqjtu.operator;

import com.cqjtu.SQLHelper.DBUtil;

public class LoginOperator {
	public static boolean Admin_logincheck(String uname,String pwd)
	{
		return DBUtil.findAdmin(uname, pwd);
	}
	public static boolean Studnet_logincheck(String uname,String pwd)
	{
		return DBUtil.findStudent(uname, pwd);
	}
}
