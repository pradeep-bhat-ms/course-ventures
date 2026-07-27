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

import com.example.course_ventures.entity.MocktestAttempt;
import com.example.course_ventures.response.ResponseStructure;
import com.example.course_ventures.service.MockTestAttemptService;



@RestController
@RequestMapping("/mock-attempt")
public class MockTestAttemptController {

	@Autowired
	private MockTestAttemptService mockTestAttemptService;

	@PostMapping("/submit")
	public ResponseEntity<ResponseStructure<MocktestAttempt>> submitAttempt(
			@RequestParam int studentId,
			@RequestParam int mockTestId,
			@RequestBody Map<Integer, String> answers) {
		MocktestAttempt attempt = mockTestAttemptService.saveAttempt(studentId, mockTestId, answers);

		ResponseStructure<MocktestAttempt> rs = new ResponseStructure<>();
		rs.setStatus(HttpStatus.CREATED.value());
		rs.setMessage("Mock test submitted and graded successfully");
		rs.setData(attempt);

		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}

	@GetMapping("/student/{studentId}")
	public ResponseEntity<ResponseStructure<List<MocktestAttempt>>> getAttemptsByStudent(
	        @PathVariable int studentId) {

	    List<MocktestAttempt> attempts =
	            mockTestAttemptService.getAttemptsByStudentId(studentId);

	    ResponseStructure<List<MocktestAttempt>> rs =
	            new ResponseStructure<>();

	    rs.setStatus(HttpStatus.OK.value());
	    rs.setMessage("Mock test attempts retrieved successfully");
	    rs.setData(attempts);

	    return new ResponseEntity<>(rs, HttpStatus.OK);
	}
}

