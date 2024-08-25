package com.account.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.account.DTO.semesterStudentCount;
import com.account.Repository.courseRepository;
import com.account.Repository.paymentRepository;
import com.account.Repository.semesterRepository;
import com.account.Repository.userRepository;
import com.account.entity.Course;
import com.account.entity.User;
import com.account.entity.payment;
import com.account.entity.semester;

import jakarta.transaction.Transactional;

@Component
public class adminService {

	@Autowired
	private userRepository urepo;

	@Autowired
	private courseRepository crepo;

	@Autowired
	private semesterRepository srepo;

	@Autowired
	private paymentRepository prepo;

	public User findByEmail(String email) {
		return urepo.findByEmail(email);
	}

	public void add_student(User user, MultipartFile file, int cid) throws IllegalStateException, IOException {
		User u = new User();
		if (file.isEmpty()) {
			u.setImage("profile.jpg");
		} else {
			String path = "C:\\Users\\rosha\\OneDrive\\Desktop\\New folder\\account\\src\\main\\resources\\static\\images\\";
			String npath = path + file.getOriginalFilename();
			String search = "images\\";
			int i = npath.indexOf(search);
			u.setImage(npath.substring(i + search.length()));
			file.transferTo(new File(npath));

		}
		
		u.setName(user.getName());
		u.setAddress(user.getAddress());
		u.setBatch(user.getBatch());
		u.setContact(user.getContact());
		u.setEmail(user.getEmail());
		u.setGender(user.getGender());
		u.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		u.setRole("USER");
		u.setAddedDate(LocalDateTime.now());
		u.setCourse(crepo.findById(cid).get());
		u.setSemester(srepo.findById(crepo.findById(cid).get().getCourseName() + "-1").get());
		u.setPendingFee(srepo.findById(crepo.findById(cid).get().getCourseName() + "-1").get().getSemesterFee());
		if (urepo.findByBatch(user.getBatch()).isEmpty()) {
			u.setUid(user.getBatch() + "-1");
		} else {
			User lastAdded = urepo.findLastAddedByBatch(user.getBatch());
			String[] parts = lastAdded.getUid().split("-");
			int incrementedID = Integer.parseInt(parts[1]) + 1;
			u.setUid(parts[0] + "-" + incrementedID);
		}
		urepo.save(u);

	}

	public List<Course> findAllCourse() {
		return crepo.findAll();
	}

	public void add_course(Course course) {
		crepo.save(course);
	}

	public List<semesterStudentCount> findSemesterByCourseId(int cid) {
		return srepo.findSemesterByCourseId(cid);
	}

	public Course findCourseById(int cid) {
		return crepo.findById(cid).get();
	}
	
	@Transactional
	public void make_payment(String uid, double amount, String receiver) {
		User student = urepo.findById(uid).get();
		User receivedBy = urepo.findByEmail(receiver);
		double previousAmount=student.getPendingFee();
		double newAmount=previousAmount-amount;

		payment p = new payment();
		p.setUser(student);
		p.setReceivedBy(receivedBy);
		p.setAmount(amount);
		p.setPaymentDate(LocalDateTime.now());
		p.setPaymentId(UUID.randomUUID().toString());
		urepo.UpdatePendingFee(newAmount, uid);
		prepo.save(p);

	}
	public User findUserById(String id) {
		return urepo.findById(id).get();
	}
	
	public List<User> findUserBySemesterId(String semesterId){
		return urepo.findUserBySemesterId(semesterId);
	}
	public List<payment> findPaymentByUid(String uid){
		return prepo.findPaymentByUid(uid);
	}
	
	@Transactional
	public void increment_semester(String presentId) {
		
		String[] parts=presentId.split("-");
		int sem=Integer.parseInt(parts[1])+1;
		String newSem= parts[0]+"-"+String.valueOf(sem);
		urepo.incrementSemester(newSem, presentId,srepo.findById(presentId).get().getSemesterFee());
		
		
	}
}
