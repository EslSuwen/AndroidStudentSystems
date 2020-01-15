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


public class StuSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private StudentOperation sdo=new StudentOperationImp();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StuSelectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.print("这是一个get");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String uname=req.getParameter("uname");
		 JSONObject object = new JSONObject();
		 Student s=sdo.GetStudent(uname);
		if(s!=null) {
		 object.put("code", 1);
         object.put("uname", s.getUname());
         object.put("pwd", s.getPwd());
         object.put("name", s.getName());
         object.put("gender", s.getGender());
         object.put("dep", s.getDep());
         object.put("institute", s.getInstitute());
         object.put("math",s.getMath());
         object.put("chinese", s.getChinese());
         object.put("english", s.getEnglish());
       }else
       {
    	   object.put("code", 0);
       }
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8"); //防止页面出现中文乱码，要放在PrintWriter前面
		PrintWriter pw=resp.getWriter();
		pw.print(object);
		pw.close();
	}

}
