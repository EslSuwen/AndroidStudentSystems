package com.cqjtu.entity;

import java.io.Serializable;

public class Student implements Serializable{
	private String uname;
	private String pwd;
	private String name;
	private String gender;
	private String dep;
	private String institute;
	private double math;
	private double chinese;
	private double english;
	public Student()
	{}
	public Student(String uname, String pwd, String name, String gender, String dep, String institute, double math,
			double chinese, double english) {
		super();
		this.uname = uname;
		this.pwd = pwd;
		this.name = name;
		this.gender = gender;
		this.dep = dep;
		this.institute = institute;
		this.math = math;
		this.chinese = chinese;
		this.english = english;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public double getMath() {
		return math;
	}
	public void setMath(double math) {
		this.math = math;
	}
	public double getChinese() {
		return chinese;
	}
	public void setChinese(double chinese) {
		this.chinese = chinese;
	}
	public double getEnglish() {
		return english;
	}
	public void setEnglish(double english) {
		this.english = english;
	}
	
}