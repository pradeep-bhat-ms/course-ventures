package com.example.course_ventures.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.CourseHub.entity.MockTestAttempt;
import com.jsp.CourseHub.response.ResponseStructure;
import com.jsp.CourseHub.service.MockTestAttemptService;


@RestController
@RequestMapping("/mock-attempt")
public class MockTestAttemptController {

	@Autowired
	private MockTestAttemptService mockTestAttemptService;

	@PostMapping("/submit")
	public ResponseEntity<ResponseStructure<MockTestAttempt>> submitAttempt(
			@RequestParam int studentId,
			@RequestParam int mockTestId,
			@RequestBody Map<Integer, String> answers) {
		MockTestAttempt attempt = mockTestAttemptService.submitAttempt(studentId, mockTestId, answers);

		ResponseStructure<MockTestAttempt> rs = new ResponseStructure<>();
		rs.setStatus(HttpStatus.CREATED.value());
		rs.setMessage("Mock test submitted and graded successfully");
		rs.setData(attempt);

		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}

	@GetMapping("/student/{studentId}")
	public ResponseEntity<ResponseStructure<List<MockTestAttempt>>> getAttemptsByStudent(
			@PathVariable int studentId) {
		List<MockTestAttempt> attempts = mockTestAttemptService.getAttemptsByStudentId(studentId);

		ResponseStructure<List<MockTestAttempt>> rs = new ResponseStructure<>();
		rs.setStatus(HttpStatus.OK.value());
		rs.setMessage("Mock test attempts retrieved successfully");
		rs.setData(attempts);

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
}

