package com.cqjtu.Servlet;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.entity.Admin;
import com.cqjtu.operator.AdminOperation;
import com.cqjtu.operator.AdminOperationImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private AdminOperation ado=new AdminOperationImp();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		req.setCharacterEncoding("UTF-8");
		String uname=req.getParameter("uname");
		String pwd=req.getParameter("pwd");
		Admin admin=new Admin(uname,pwd);
		JSONObject json = new JSONObject();
		int value=ado.insertAdmin(admin);
		json.put("code", value);
		PrintWriter pw=resp.getWriter();
		pw.print(json);
		pw.close();
	}

}
