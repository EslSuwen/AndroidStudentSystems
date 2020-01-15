package com.cqjtu.SQLHelper;

import com.cqjtu.entity.Admin;
import com.cqjtu.entity.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
	public  static SQLhelper sqlhelper=new SQLhelper(); 
	private static void CloseUtil(PreparedStatement ps,ResultSet rs,Connection con)
	{
		if(rs!=null)
		{
			try {
				rs.close();
				rs=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ps!=null)
		{
			try {
				ps.close();
				ps=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(con!=null)
		{
			try {
				con.close();
				con=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/***
	 *用户登录检验
	 */
	public	static boolean findAdmin(String uname,String pwd)
	{
		Connection con=sqlhelper.createConnection();
		PreparedStatement ps=null;
		ResultSet result=null;
		String sql="select "+Adminfield.uname+","+Adminfield.pwd+" from "+Adminfield.tablena+"  where "+Adminfield.uname+"=? and "+Adminfield.pwd+"=?";
		try {
			 ps=con.prepareStatement(sql);
			 ps.setString(1, uname);
			 ps.setString(2, pwd);
			 result=ps.executeQuery();
			 while(result.next())
			 {
				 return true;
			 }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally
		{
			CloseUtil(ps,result,con);
		}
		return false;
	}
	public	static boolean findStudent(String uname,String pwd)
	{
		Connection con=sqlhelper.createConnection();
		PreparedStatement ps=null;
		ResultSet result=null;
		String sql="select "+Studentfield.uname+","+Studentfield.pwd+" from "+Studentfield.tablena+"  where "+Studentfield.uname+"=? and "+Studentfield.pwd+"=?";
		try {
			 ps=con.prepareStatement(sql);
			 ps.setString(1, uname);
			 ps.setString(2, pwd);
			 result=ps.executeQuery();
			 while(result.next())
			 {
				 return true;
			 }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally
		{
			CloseUtil(ps,result,con);
		}
		return false;
	}

	/***
	 * 用户信息修改
	 */
	
	/***
	 * 插入信息
	 */
	public static  int insertAdmin(Admin root)
	{
		int temp=0;
		Connection con=sqlhelper.createConnection();
		PreparedStatement ps=null;
		String sql="insert into "+Adminfield.tablena+"("+Adminfield.uname+","+Adminfield.pwd+") values(?,?)";
		try {
			 ps=con.prepareStatement(sql);
			 ps.setString(1, root.getUname());
			 ps.setString(2, root.getPwd());
			temp=ps.executeUpdate();
			 return temp;
		} catch (SQLException e) {
			
			temp= 0;
			e.printStackTrace();
		}finally
		{
			CloseUtil(ps,null,con);
		}
		return temp;
	}
	public static  int insertStudent(Student stu)
	{
		Connection con=sqlhelper.createConnection();
		PreparedStatement ps=null;
		String sql="insert into "+Studentfield.tablena+"("+Studentfield.uname+","+Studentfield.pwd+","+Studentfield.name+","+
								Studentfield.gender+","+Studentfield.dep+","+Studentfield.institute+","+
								Studentfield.math+","+Studentfield.chinese+","+Studentfield.english+") values(?,?,?,?,?,?,?,?,?)";
		try {
			 ps=con.prepareStatement(sql);
			 ps.setString(1, stu.getUname());
			 ps.setString(2, stu.getPwd());
			 ps.setString(3,stu.getName());
			 ps.setString(4, stu.getGender());
			 ps.setString(5, stu.getDep());
			 ps.setString(6, stu.getInstitute());
			 ps.setDouble(7, stu.getMath());
			 ps.setDouble(8, stu.getChinese());
			 ps.setDouble(9, stu.getEnglish());
			 int i=ps.executeUpdate();
			 return i;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally
		{
			CloseUtil(ps,null,con);
		}
		return 0;
	}

	/***
	 * 删除信息
	 */
	public static int deleteStudent(String uname)
	{
		Connection con=sqlhelper.createConnection();
		PreparedStatement ps=null;
		String sql="delete from "+Studentfield.tablena+" where "+Studentfield.uname+"=?";
		try {
			 ps=con.prepareStatement(sql);
			 ps.setString(1, uname);
			 int i=ps.executeUpdate();
			 return i;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally
		{
			CloseUtil(ps,null,con);
		}
		return 0;
	}

	/***
	 * 编辑学生信息
	 */
	public static int updateStudent(Student s)
	{
		Connection con=sqlhelper.createConnection();
		PreparedStatement ps=null;
		String sql="update "+Studentfield.tablena+" set "+Studentfield.name+"=?,"+Studentfield.gender+"=?,"+
					Studentfield.dep+"=?,"+Studentfield.institute+"=?"+" where "+Studentfield.uname+"=?";
		try {
			 ps=con.prepareStatement(sql);
			 ps.setString(1, s.getName());
			 ps.setString(2, s.getGender());
			 ps.setString(3, s.getDep());
			 ps.setString(4, s.getInstitute());
			 ps.setString(5, s.getUname());
			 int i=ps.executeUpdate();
			 return i;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally
		{
			CloseUtil(ps,null,con);
		}
		return 0;
	}

	public static int updateStudent(String uname,Student s)
	{
		Connection con=sqlhelper.createConnection();
		PreparedStatement ps=null;
		String sql="update "+Studentfield.tablena+" set "+Studentfield.uname+"=?,"+Studentfield.pwd+"=?,"+Studentfield.name+"=?,"+Studentfield.gender+"=?,"+
					Studentfield.dep+"=?,"+Studentfield.institute+"=?,"+Studentfield.math+"=?,"
					+Studentfield.chinese+"=?,"+Studentfield.english+"=?"+" where "+Studentfield.uname+"=?";
		try {
			 ps=con.prepareStatement(sql);
			 ps.setString(1, s.getUname());
			 ps.setString(2, s.getPwd());
			 ps.setString(3, s.getName());
			 ps.setString(4, s.getGender());
			 ps.setString(5, s.getDep());
			 ps.setString(6, s.getInstitute());
			 ps.setDouble(7, s.getMath());
			 ps.setDouble(8, s.getChinese());
			 ps.setDouble(9, s.getEnglish());
			 ps.setString(10, uname);
			 int i=ps.executeUpdate();
			 return i;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally
		{
			CloseUtil(ps,null,con);
		}
		return 0;
	}
	/***
	 * 查询学生
	 */
	public static List<Student> findSudentinfo(String key)
	{
		Connection con=sqlhelper.createConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Student> students=new ArrayList<Student>();
		String sql;
		if(!"".equals(key)) {
		sql = "select * from "+Studentfield.tablena+" where "+Studentfield.uname+" like ?";
		}else {
		sql="select * from "+Studentfield.tablena;}
		try {
			ps=con.prepareStatement(sql);
			if(!"".equals(key))
				ps.setString(1, "%"+key+"%");
			rs=ps.executeQuery();
			while(rs.next()){
				String uname=rs.getString(rs.findColumn(Studentfield.uname));
				String pwd=rs.getString(rs.findColumn(Studentfield.pwd));
				String gender=rs.getString(rs.findColumn(Studentfield.gender));
				String dep=rs.getString(rs.findColumn(Studentfield.dep));
				String institute=rs.getString(rs.findColumn(Studentfield.institute));
				String name=rs.getString(rs.findColumn(Studentfield.name));
				double math=rs.getDouble(rs.findColumn(Studentfield.math));
				double chinese=rs.getDouble(rs.findColumn(Studentfield.chinese));
				double english=rs.getDouble(rs.findColumn(Studentfield.english));
				Student s=new Student(uname,pwd,name,gender,dep,institute,math,chinese,english);
				students.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally {
			CloseUtil(ps,rs,con);
		}
		return students;	
	}
	
	public static Student GetSudentinfo(String id)
	{
		Connection con=sqlhelper.createConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		Student s=null;
		String sql = "select * from "+Studentfield.tablena+" where "+Studentfield.uname+"=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1,id);
			rs=ps.executeQuery();
			while(rs.next()){
				String uname=rs.getString(rs.findColumn(Studentfield.uname));
				String pwd=rs.getString(rs.findColumn(Studentfield.pwd));
				String gender=rs.getString(rs.findColumn(Studentfield.gender));
				String dep=rs.getString(rs.findColumn(Studentfield.dep));
				String institute=rs.getString(rs.findColumn(Studentfield.institute));
				String name=rs.getString(rs.findColumn(Studentfield.name));
				double math=rs.getDouble(rs.findColumn(Studentfield.math));
				double chinese=rs.getDouble(rs.findColumn(Studentfield.chinese));
				double english=rs.getDouble(rs.findColumn(Studentfield.english));
				s=new Student(uname,pwd,name,gender,dep,institute,math,chinese,english);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally
		{
			CloseUtil(ps,rs,con);
		}
		return s;	
	}
}
