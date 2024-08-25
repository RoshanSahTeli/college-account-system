package com.account.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Course {
	
	@Id
	private int courseId;
	
	private String courseName;
	
	private double admissionFee;
	
	
	private double totalFee;
	
	@OneToMany(mappedBy = "course")
	private List<User> students;
	
	@OneToMany(mappedBy = "course" )
	private List<semester> semester;
	
	

	public double getAdmissionFee() {
		return admissionFee;
	}

	public void setAdmissionFee(double admissionFee) {
		this.admissionFee = admissionFee;
	}

	public List<semester> getSemester() {
		return semester;
	}

	public void setSemester(List<semester> semester) {
		this.semester = semester;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public List<User> getStudents() {
		return students;
	}

	public void setStudents(List<User> students) {
		this.students = students;
	}

	

	public Course(int courseId, String courseName, double admissionFee, double totalFee, List<User> students,
			List<com.account.entity.semester> semester) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.admissionFee = admissionFee;
		this.totalFee = totalFee;
		this.students = students;
		this.semester = semester;
	}

	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
