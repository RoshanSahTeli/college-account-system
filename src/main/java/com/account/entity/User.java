package com.account.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class User {

	@Id
	private String uid;

	private String name;

	private String email;

	private String role;

	private String password;

	private String contact;

	private String address;

	private String image;

	private String batch;

	private String gender;

	private double pendingFee;

	private double previousFee;

	@Column(nullable = true)
	private String message;

	private LocalDateTime addedDate;

	@OneToMany(mappedBy = "user")
	private List<payment> payments;

	@OneToMany(mappedBy = "receivedBy")
	private List<payment> paymentReceived;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
	private Course course;

	@ManyToOne
	@JoinColumn(name = "semesterId", referencedColumnName = "semesterId")
	private semester semester;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPendingFee() {
		return pendingFee;
	}

	public void setPendingFee(double pendingFee) {
		this.pendingFee = pendingFee;
	}

	public double getPreviousFee() {
		return previousFee;
	}

	public void setPreviousFee(double previousFee) {
		this.previousFee = previousFee;
	}

	public List<payment> getPaymentReceived() {
		return paymentReceived;
	}

	public void setPaymentReceived(List<payment> paymentReceived) {
		this.paymentReceived = paymentReceived;
	}

	public List<payment> getPayments() {
		return payments;
	}

	public void setPayments(List<payment> payments) {
		this.payments = payments;
	}

	public semester getSemester() {
		return semester;
	}

	public void setSemester(semester semester) {
		this.semester = semester;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public LocalDateTime getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDateTime addedDate) {
		this.addedDate = addedDate;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



	public User(String uid, String name, String email, String role, String password, String contact, String address,
			String image, String batch, String gender, double pendingFee, double previousFee,String message, LocalDateTime addedDate,
			List<payment> payments, List<payment> paymentReceived, Course course,
			com.account.entity.semester semester) {
		super();
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.role = role;
		this.password = password;
		this.contact = contact;
		this.address = address;
		this.image = image;
		this.batch = batch;
		this.gender = gender;
		this.pendingFee = pendingFee;
		this.message=message;
		this.previousFee = previousFee;
		this.addedDate = addedDate;
		this.payments = payments;
		this.paymentReceived = paymentReceived;
		this.course = course;
		this.semester = semester;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

}
