package com.cqjtu.Servlet;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.operator.StudentOperation;
import com.cqjtu.operator.StudentOperationImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private StudentOperation sdo=new StudentOperationImp();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.print("这是一个get方法");
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String uname=req.getParameter("uname");
		int value=sdo.deleteStudent(uname);
		JSONObject json=new JSONObject();
		json.put("code", value);
		PrintWriter pw=resp.getWriter();
		pw.print(json);
		pw.close();
	}

}
