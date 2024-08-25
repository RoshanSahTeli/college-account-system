package com.account.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.account.entity.Course;

public interface courseRepository extends JpaRepository<Course, Integer> {
	
	
}
