package com.cqjtu.Servlet;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.entity.Student;
import com.cqjtu.operator.AdminOperation;
import com.cqjtu.operator.AdminOperationImp;
import com.cqjtu.operator.StudentOperation;
import com.cqjtu.operator.StudentOperationImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private AdminOperation ado=new AdminOperationImp();
    private StudentOperation sdo=new StudentOperationImp();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("这是一个get方法");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String key=req.getParameter("switches");
		int value=0;
		if("admin".equals(key))
		{
			String olduname=req.getParameter("olduname");
			String uname=req.getParameter("uname");
			String pwd=req.getParameter("pwd");
			String name=req.getParameter("name");
			String gender=req.getParameter("gender");
			String dep=req.getParameter("dep");
			String institute=req.getParameter("institute");
			double math=Double.parseDouble(req.getParameter("math"));
			double english=Double.parseDouble(req.getParameter("english"));
			double chinese=Double.parseDouble(req.getParameter("chinese"));
			Student s=new Student(uname,pwd,name,gender,dep,institute,math,chinese,english);
			value=ado.editStudentinfo(olduname, s);
		}else if("student".equals(key))
		{
			String uname=req.getParameter("uname");
			String name=req.getParameter("name");
			String gender=req.getParameter("gender");
			String dep=req.getParameter("dep");
			String institute=req.getParameter("institute");
			Student s=new Student(uname,null,name,gender,dep,institute,0,0,0);
			value=sdo.EditStudent(s);
		}
		JSONObject json=new JSONObject();
		json.put("code", value);
		PrintWriter pw=resp.getWriter();
		pw.print(json);
		pw.close();
	}

}
