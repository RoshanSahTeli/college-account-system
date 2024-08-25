package com.account.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class semester {

	@Id
	private String semesterId;

	private double semesterFee;

	private String semesterName;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
	private Course course;

	@OneToMany(mappedBy = "semester")
	private List<User> user;

	

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public double getSemesterFee() {
		return semesterFee;
	}

	public void setSemesterFee(double semesterFee) {
		this.semesterFee = semesterFee;
	}

	public String getSemesterName() {
		return semesterName;
	}

	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public semester(String semesterId, double semesterFee, String semesterName, 
			Course course, List<User> user) {
		super();
		this.semesterId = semesterId;
		this.semesterFee = semesterFee;
		this.semesterName = semesterName;
		
		this.course = course;
		this.user = user;
	}

	public semester() {
		super();
		// TODO Auto-generated constructor stub
	}

}
