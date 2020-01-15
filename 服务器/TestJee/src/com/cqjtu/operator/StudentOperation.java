package com.cqjtu.operator;

import com.cqjtu.entity.Student;

import java.util.List;

public interface StudentOperation {
	public List<Student> searchstudent(String key);
	public int insertStudent(Student s);
	public int EditStudent(Student s);
	public int deleteStudent(String uname);
	public Student GetStudent(String uname);
}
