package com.account.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class payment {
	
	@Id
	private String paymentId;
	
	private LocalDateTime paymentDate;
	
	private double amount;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private User user;
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name = "receiverId")
	private User receivedBy;
	
	

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(User receivedBy) {
		this.receivedBy = receivedBy;
	}

	

	public payment(String paymentId, LocalDateTime paymentDate, double amount, User user, User receivedBy) {
		super();
		this.paymentId = paymentId;
		this.paymentDate = paymentDate;
		this.amount = amount;
		this.user = user;
		this.receivedBy = receivedBy;
	}

	public payment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
