package com.cqjtu.Servlet;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.entity.Student;
import com.cqjtu.operator.StudentOperation;
import com.cqjtu.operator.StudentOperationImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class InsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private StudentOperation sdo=new StudentOperationImp();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertServlet() {
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
	protected void doPost(HttpServletRequest rep, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		rep.setCharacterEncoding("utf-8");
		Student s=new Student();
		s.setUname(rep.getParameter("uname"));
		s.setPwd(rep.getParameter("pwd"));
		s.setName(rep.getParameter("name"));
		s.setGender(rep.getParameter("sex"));
		s.setInstitute(rep.getParameter("institute"));
		s.setDep(rep.getParameter("dep"));
		s.setMath(Double.parseDouble(rep.getParameter("math")));
		s.setChinese(Double.parseDouble(rep.getParameter("chinese")));
		s.setEnglish(Double.parseDouble(rep.getParameter("english")));
		JSONObject json = new JSONObject();
		int value=sdo.insertStudent(s);
		json.put("code", value);
		PrintWriter pw=resp.getWriter();
		pw.print(json);
		pw.close();
	}

}
