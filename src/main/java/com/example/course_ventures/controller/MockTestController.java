package com.example.course_ventures.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_ventures.entity.MockTest;
import com.example.course_ventures.entity.Question;
import com.example.course_ventures.entity.User;
import com.example.course_ventures.enums.Role;
import com.example.course_ventures.repository.UserRepository;
import com.example.course_ventures.response.ResponseStructure;
import com.example.course_ventures.service.MockTestService;




@RestController
@RequestMapping("/mock-test")
public class MockTestController {


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MockTestService mockTestService;

	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<MockTest>> saveMockTest(
			@RequestBody MockTest mockTest,
			@RequestParam int courseId) {
		MockTest savedMock = mockTestService.saveMockTest(mockTest, courseId);

		ResponseStructure<MockTest> rs = new ResponseStructure<>();
		rs.setStatus(HttpStatus.CREATED.value());
		rs.setMessage("Mock test created successfully");
		rs.setData(savedMock);

		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}

	@GetMapping("/course/{courseId}")
	public ResponseEntity<ResponseStructure<List<MockTest>>> getMockTestsByCourse(
			@PathVariable int courseId) {
		List<MockTest> mocks = mockTestService.getMockTestsByCourseId(courseId);

		ResponseStructure<List<MockTest>> rs = new ResponseStructure<>();
		rs.setStatus(HttpStatus.OK.value());
		rs.setMessage("Mock tests retrieved successfully");
		rs.setData(mocks);

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<MockTest>> getMockTestById(
			@PathVariable int id) {
		MockTest mockTest = mockTestService.findMockTestById(id);

		ResponseStructure<MockTest> rs = new ResponseStructure<>();
		rs.setStatus(HttpStatus.OK.value());
		rs.setMessage("Mock test retrieved successfully");
		rs.setData(mockTest);

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	@PostMapping("/{mockTestId}/add-question")
	public ResponseEntity<ResponseStructure<Question>> addQuestion(
			@PathVariable int mockTestId,
			@RequestBody Question question) {
		Question savedQuestion = mockTestService.addQuestionToMockTest(mockTestId, question);

		ResponseStructure<Question> rs = new ResponseStructure<>();
		rs.setStatus(HttpStatus.CREATED.value());
		rs.setMessage("Question added successfully to the mock test");
		rs.setData(savedQuestion);

		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}

	@PostMapping("/submit")
	public ResponseEntity<java.util.Map<String, Object>> submitExam(
			@RequestParam int studentId,
			@RequestParam int mockTestId,
			@RequestBody java.util.Map<String, String> answers) {

		com.example.course_ventures.entity.MocktestAttempt attempt =
				mockTestService.submitAttempt(studentId, mockTestId, answers);

		java.util.Map<String, Object> data = new java.util.HashMap<>();
		data.put("score", attempt.getScore());
		data.put("totalQuestions", attempt.getTotalQuestions());

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteMockTest(
			@PathVariable int id,
			Authentication authentication) {

		User user = userRepository.findByemail(authentication.getName());
		if (user == null || user.getRole() != Role.TRAINER) {
			throw new IllegalStateException("Only trainers can delete mock tests.");
		}

		MockTest mockTest = mockTestService.findMockTestById(id);
		if (mockTest.getCourse() == null || mockTest.getCourse().getTrainer() == null
				|| mockTest.getCourse().getTrainer().getId() != user.getId()) {
			throw new IllegalStateException("You are not authorized to delete this mock test.");
		}

		mockTestService.deleteMockTest(id);

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatus(HttpStatus.OK.value());
		rs.setMessage("Mock test deleted successfully");
		rs.setData("Mock Test Deleted Successfully");

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
}