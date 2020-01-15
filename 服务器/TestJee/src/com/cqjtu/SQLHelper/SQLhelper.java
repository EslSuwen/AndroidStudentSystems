package com.cqjtu.SQLHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLhelper {
	private static String driverstr="com.mysql.cj.jdbc.Driver";
	private String url="jdbc:mysql://localhost:3306/students?useSSL=true&characterEncoding=utf-8&serverTimezone=GMT";
	private String sqluser="root";
	private String sqlpwd="Liyu8824";
	static {
		try {
			Class.forName(driverstr);//加载驱动
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Connection createConnection()
	{	
		Connection con=null;
			try {
				con=DriverManager.getConnection(url,sqluser,sqlpwd);//建立连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(con==null)
			System.out.print("connection is null");
		return con;
	}
}
