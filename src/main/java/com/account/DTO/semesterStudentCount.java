package com.account.DTO;

public interface semesterStudentCount {
	String getCname();
	int getTotalStudent();
	public abstract Double getPendingFee();
	public abstract Double getPaidFee();
	String getSemId();
}
