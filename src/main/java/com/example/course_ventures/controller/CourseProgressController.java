package com.example.course_ventures.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_ventures.dto.CourseProgressRequestDto;
import com.example.course_ventures.dto.CourseProgressResponseDto;
import com.example.course_ventures.service.CourseProgressService;



@RestController
@RequestMapping("/course-progress")
public class CourseProgressController {

	@Autowired
	CourseProgressService courseProgressService;

	@PostMapping("/update")
	public CourseProgressResponseDto updateProgress(@RequestBody CourseProgressRequestDto requestDto) {
		return courseProgressService.updateProgress(requestDto);
	}

	@GetMapping("/get")
	public CourseProgressResponseDto getProgress(@RequestParam int studentId, @RequestParam int courseId) {
		return courseProgressService.getProgress(studentId, courseId);
	}

	@GetMapping("/student")
	public List<CourseProgressResponseDto> getStudentProgress(@RequestParam int studentId) {
		return courseProgressService.getStudentProgress(studentId);
	}

	@GetMapping("/course")
	public List<CourseProgressResponseDto> getCourseProgress(@RequestParam int courseId) {
		return courseProgressService.getCourseProgress(courseId);
	}

	@GetMapping("/completed")
	public List<CourseProgressResponseDto> getAllCompletedProgress() {
		return courseProgressService.getAllCompletedProgress();
	}

}
