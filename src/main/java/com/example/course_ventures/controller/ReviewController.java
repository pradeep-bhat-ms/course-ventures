package com.example.course_ventures.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.CourseHub.dto.ReviewRequestDto;
import com.jsp.CourseHub.dto.ReviewResponseDto;
import com.jsp.CourseHub.service.ReviewService;


@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	ReviewService reviewService;

	@PostMapping("/add")
	public ReviewResponseDto addReview(@RequestBody ReviewRequestDto requestDto) {
		return reviewService.addReview(requestDto);
	}

	@GetMapping("/course")
	public List<ReviewResponseDto> getCourseReviews(@RequestParam int courseId) {
		return reviewService.getCourseReviews(courseId);
	}

	@GetMapping("/student")
	public List<ReviewResponseDto> getStudentReviews(@RequestParam int studentId) {
		return reviewService.getStudentReviews(studentId);
	}

	@GetMapping("/average")
	public double getAverageRating(@RequestParam int courseId) {
		return reviewService.getAverageRating(courseId);
	}
}
