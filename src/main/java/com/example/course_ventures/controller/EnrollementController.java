package com.example.course_ventures.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.CourseHub.entity.Enrollement;
import com.jsp.CourseHub.repository.EnrollementRepository;
import com.jsp.CourseHub.service.EnrollementService;


@RestController
@RequestMapping("/enrollement")
public class EnrollementController {

	@Autowired
	EnrollementService enrollmentService;
	
	@Autowired
	EnrollementRepository enrollmentRepository;

	@PostMapping("/save")
	public Enrollement enrollCourse(
			@RequestParam int studentId,
			@RequestParam int courseId) {

		return enrollmentService.enrollCourse(studentId, courseId);
	}

	@GetMapping("/fetch")
	public List<Enrollement> getAllEnrollments() {

		return enrollmentService.getAllEnrollments();
	}
	
	@GetMapping("/student")
	public List<Enrollement> getStudentEnrollments(@RequestParam int studentId) {
		return enrollmentRepository.findByStudentId(studentId);
	}
}

