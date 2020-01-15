package com.cqjtu.operator;

import com.cqjtu.SQLHelper.DBUtil;
import com.cqjtu.entity.Student;

import java.util.List;

public class StudentOperationImp implements StudentOperation {

	@Override
	public List<Student> searchstudent(String key) {
		return DBUtil.findSudentinfo(key);
	}

	@Override
	public int insertStudent(Student s) {
		return DBUtil.insertStudent(s);
	}

	@Override
	public int EditStudent(Student s) {
		// TODO Auto-generated method stub
		return DBUtil.updateStudent(s);
	}

	@Override
	public int deleteStudent(String uname) {
		// TODO Auto-generated method stub
		return DBUtil.deleteStudent(uname);
	}

	@Override
	public Student GetStudent(String uname) {
		// TODO Auto-generated method stub
		return DBUtil.GetSudentinfo(uname);
	}
}
