package com.cqjtu.operator;

import com.cqjtu.SQLHelper.DBUtil;
import com.cqjtu.entity.Admin;
import com.cqjtu.entity.Student;

public class AdminOperationImp implements AdminOperation{

	@Override
	public int insertAdmin(Admin root) {
		
		return DBUtil.insertAdmin(root);
	}
	@Override
	public int editStudentinfo(String uname, Student s) {
		// TODO Auto-generated method stub
		return DBUtil.updateStudent(uname, s);
	}
}
