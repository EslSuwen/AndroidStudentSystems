package com.cqjtu.operator;

import com.cqjtu.entity.Admin;
import com.cqjtu.entity.Student;

public interface AdminOperation {
	public int insertAdmin(Admin root);
	public int editStudentinfo(String uname, Student s);
}
