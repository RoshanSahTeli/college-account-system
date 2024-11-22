package com.account.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.account.entity.Course;
import com.account.entity.User;
import com.account.service.adminService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class adminController {
	@Autowired
	private adminService aservice;

	@GetMapping("/home")
	public String home(Model model, HttpSession session, Principal principal) {
		User u = aservice.findByEmail(principal.getName());
		model.addAttribute("user", u);
		session.setAttribute("user", u);
		model.addAttribute("courses", aservice.findAllCourse());
		session.setAttribute("courses", aservice.findAllCourse());
		return "admin_home";
	}

	@GetMapping("/add_student_form")
	public String student(Model model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", session.getAttribute("courses"));
		return "add_student_form";
	}

	@PostMapping("/add_student")
	public void add_student(Model model, HttpSession session, HttpServletResponse response, @ModelAttribute User user,
			@RequestParam("img") MultipartFile file, @RequestParam("course") int cid) throws IOException {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", session.getAttribute("courses"));
		aservice.add_student(user, file, cid);
		response.sendRedirect("/admin/add_student_form");

	}

	@GetMapping("/course/{id}")
	public String courses(@PathVariable("id") int id, Model model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", aservice.findAllCourse());
		model.addAttribute("semesters", aservice.findSemesterByCourseId(id));
		model.addAttribute("course", aservice.findCourseById(id));
		return "courses";
	}

	@GetMapping("/add_course_form")
	public String add_course_form(Model model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", aservice.findAllCourse());
		return "add_course_form";
	}

	@PostMapping("/add_course")
	public void add_course(@ModelAttribute Course course, Model model, HttpSession session,
			HttpServletResponse response) throws IOException {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", aservice.findAllCourse());
		aservice.add_course(course);
		response.sendRedirect("/admin/add_course_form");
	}

	@GetMapping("/payment_form_sidebar")
	public String payment_form_sidebar(Model model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", aservice.findAllCourse());
		return "payment_form_sidebar";
	}

	@GetMapping("/payment_form/{id}")
	public String payment_form(@PathVariable("id") String uid, Model model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", aservice.findAllCourse());
		model.addAttribute("search", aservice.findUserById(uid));
		return "payment_form";
	}

	@PostMapping("/make_payment")
	public void make_payment(Model model, HttpSession session, HttpServletResponse response, Principal principal,
			@RequestParam("studentId") String id, @RequestParam("amount") double amount) throws IOException {
		aservice.make_payment(id, amount, principal.getName());
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", aservice.findAllCourse());
		response.sendRedirect("/admin/payment_form_sidebar");

	}

	@PostMapping("/search")
	public String search(Model model, HttpSession session, @RequestParam("search") String search) {
		model.addAttribute("search", aservice.findUserById(search));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", aservice.findAllCourse());
		session.setAttribute("search", aservice.findUserById(search));
		return "search";
	}

	@GetMapping("/semester/{id}")
	public String semester(Model model, HttpSession session, @PathVariable("id") String semId) {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", aservice.findAllCourse());
		model.addAttribute("users", aservice.findUserBySemesterId(semId));
		model.addAttribute("semId", semId);
		return "semester";
	}

	@GetMapping("/statement/{id}")
	public String statement(@PathVariable("id") String uid, Model model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", aservice.findAllCourse());
		model.addAttribute("statement", aservice.findPaymentByUid(uid));
		model.addAttribute("uid", uid);
		return "statement";
	}

	@GetMapping("/increment_semester")
	public void increment_semester(Model model, HttpSession session, HttpServletResponse response,
			@RequestParam("id") String semId) throws IOException {
		aservice.increment_semester(semId);
		response.sendRedirect("/admin/semester/" + semId);
	}

	@GetMapping("/download")
	public ResponseEntity<byte[]> downloadPdf(@RequestParam("id") String uid) {
		Map<String, Object> data = new HashMap<>();
		// Add any additional data you need to pass to the template
		// For example, you can include the statement data here
		data.put("statement", aservice.findPaymentByUid(uid)); // Assuming you have a service to get statement data

		byte[] pdfBytes = aservice.generatePdf("statement_template", data);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", "statement.pdf");

		return ResponseEntity.ok().headers(headers).body(pdfBytes);
	}

	@GetMapping("/update_form/{id}")
	public String update_form(Model model, HttpSession session, @PathVariable("id") String id) {
		model.addAttribute("update", aservice.findUserById(id));
		return "update_form";
	}

	@PostMapping("/update/{id}")
	public void update(@PathVariable("id") String uid, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("contact") String contact,
			@RequestParam("pendingFee") double pendingFee, @RequestParam("previousFee") double previousFee,
			@RequestParam("image") MultipartFile file, Model model, HttpSession session, HttpServletResponse response)
			throws IllegalStateException, IOException {
		System.out.println("Test");
		aservice.update(name, email, contact, pendingFee, previousFee, file, uid);
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", aservice.findAllCourse());
		response.sendRedirect("/admin/semester/" + aservice.findUserById(uid).getSemester().getSemesterId());
	}
	
	@GetMapping("/pendingFee")
	public String pendingFee(Model model,HttpSession session) {
		model.addAttribute("upending", aservice.pendingFee());
		return "pendingFee";
	}
	@GetMapping("/studentList")
	public String studentList(Model model,HttpSession session) {
		model.addAttribute("students", aservice.findStudents("USER"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", aservice.findAllCourse());
		return "studentList";
	}
	

	@GetMapping("/deleteUser/{id}")
	public void deleteByUid(Model model,HttpSession session,@PathVariable("id")String uid,HttpServletResponse response) throws IOException {
		aservice.deleteByUid(uid);
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("courses", aservice.findAllCourse());
		response.sendRedirect("/admin/studentList");
	}

	@GetMapping("/notify/{id}")
	public void notify(@PathVariable("id")String uid,Model model,HttpSession session,HttpServletResponse response) throws IOException {
		User u=aservice.findUserById(uid);
		double totalPending=u.getPendingFee()+u.getPreviousFee();
		String message="Dear "+u.getName()+", please note that today on "+ LocalDate.now()+", the deadline to pay your semester fee is reached." +
				" Kindly ensure payment of total"+totalPending+" is made before the due date to avoid any late fees or penalties. Thank you.";
		model.addAttribute("message",message);
		aservice.notify(uid,message);
		model.addAttribute("user",session.getAttribute("user"));
		model.addAttribute("courses",session.getAttribute("courses"));
		response.sendRedirect("/admin/pendingFee");
		System.out.println(uid);
	}
}
