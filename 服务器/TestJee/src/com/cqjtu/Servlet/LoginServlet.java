package com.cqjtu.Servlet;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.operator.LoginOperator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		req.setCharacterEncoding("UTF-8");
		String uname=req.getParameter("account");
		String pwd=req.getParameter("pwd");
		String switches=req.getParameter("switches");
		JSONObject json = new JSONObject();
		if("admin".equals(switches))
		{
			
			if(LoginOperator.Admin_logincheck(uname, pwd))
			{
				json.put("code", 1);
			}else 
			{
				json.put("code", 0);
			}
		}else if("student".equals(switches))
		{
			if(LoginOperator.Studnet_logincheck(uname, pwd))
			{
				json.put("code", 1);
			}else
			{
				json.put("code", 0);
			}
		}
		PrintWriter pw=resp.getWriter();
		pw.print(json);
		pw.close();
	}

}
