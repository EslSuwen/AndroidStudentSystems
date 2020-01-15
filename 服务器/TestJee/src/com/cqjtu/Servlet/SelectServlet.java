package com.cqjtu.Servlet;

import com.alibaba.fastjson.JSONArray;
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
import java.util.List;


public class SelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  private StudentOperation sdo=new StudentOperationImp();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("只是一个get方法");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String key=req.getParameter("key");
		JSONObject json = new JSONObject();
		JSONArray array =null;
		List<Student> students=sdo.searchstudent(key);
		if(students!=null||students.size()!=0) {
        array = new JSONArray();
        for (Student ent : students) {
            JSONObject object = new JSONObject();
	            object.put("uname", ent.getUname());
	            object.put("pwd", ent.getPwd());
	            object.put("name", ent.getName());
	            object.put("gender", ent.getGender());
	            object.put("dep", ent.getDep());
	            object.put("institute", ent.getInstitute());
	            object.put("math",ent.getMath());
	            object.put("chinese", ent.getChinese());
	            object.put("english", ent.getEnglish());
	            array.add(object);
	        }
		}
		json.put("students", array);
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8"); //防止页面出现中文乱码，要放在PrintWriter前面
		PrintWriter pw = resp.getWriter();
		pw.print(json);
		pw.close();

	}

}
