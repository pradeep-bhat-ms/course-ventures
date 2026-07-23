package com.example.course_ventures.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsp.CourseHub.entity.Course;
import com.jsp.CourseHub.entity.Enrollement;
import com.jsp.CourseHub.entity.Student;
import com.jsp.CourseHub.entity.Trainer;
import com.jsp.CourseHub.service.AdminDashboardService;


@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

	@Autowired
	AdminDashboardService adminDashboardService;

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		Map<String, Object> statistics = adminDashboardService.getDashboardStatistics();
		model.addAttribute("statistics", statistics);
		return "admin/dashboard";
	}

	@GetMapping("/students")
	public String students(Model model) {
		List<Student> students = adminDashboardService.getAllStudents();
		model.addAttribute("students", students);
		return "admin/students";
	}

	@GetMapping("/trainers")
	public String trainers(Model model) {
		List<Trainer> trainers = adminDashboardService.getAllTrainers();
		model.addAttribute("trainers", trainers);
		return "admin/trainers";
	}

	@GetMapping("/courses")
	public String courses(Model model) {
		List<Course> courses = adminDashboardService.getAllCourses();
		model.addAttribute("courses", courses);
		return "admin/courses";
	}

	@GetMapping("/enrollments")
	public String enrollments(Model model) {
		List<Enrollement> enrollments = adminDashboardService.getAllEnrollments();
		model.addAttribute("enrollments", enrollments);
		return "admin/enrollments";
	}

	@PostMapping("/approve/{id}")
	@ResponseBody
	public ResponseEntity<String> approveUser(@PathVariable int id) {
		adminDashboardService.approveUser(id);
		return ResponseEntity.ok("User approved successfully");
	}
}
