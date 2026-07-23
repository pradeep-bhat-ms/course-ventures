package com.example.course_ventures.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.CourseHub.entity.Category;
import com.jsp.CourseHub.entity.Course;
import com.jsp.CourseHub.entity.User;
import com.jsp.CourseHub.repository.UserRepository;
import com.jsp.CourseHub.service.CategoryService;
import com.jsp.CourseHub.service.CourseService;


@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	CourseService courseService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	UserRepository userRepository;

	@PostMapping("/save")
	public Course saveCourse(
			@RequestBody Course course,
			@RequestParam int categoryId,
			Authentication authentication) {

		User user = userRepository.findByEmail(authentication.getName());
		if (user == null || user.getRole() != com.jsp.CourseHub.enums.Role.TRAINER) {
			throw new IllegalStateException("Only trainers can create courses.");
		}

		return courseService.saveCourse(course, categoryId, user.getId());
	}

	@GetMapping("/fetch")
	public List<Course> getAllCourses() {

		return courseService.getAllCourses();
	}

	@GetMapping("/fetch/{id}")
	public Course getCourseById(@PathVariable int id) {

		return courseService.getCourseById(id);
	}

	@PostMapping("/update/{id}")
	public Course updateCourse(
			@PathVariable int id,
			@RequestBody Course courseDetails,
			@RequestParam int categoryId,
			Authentication authentication) {

		User user = userRepository.findByEmail(authentication.getName());
		if (user == null || user.getRole() != com.jsp.CourseHub.enums.Role.TRAINER) {
			throw new IllegalStateException("Only trainers can update courses.");
		}

		Course course = courseService.getCourseById(id);
		if (course.getTrainer() == null || course.getTrainer().getId() != user.getId()) {
			throw new IllegalStateException("You are not authorized to update this course.");
		}

		course.setTitle(courseDetails.getTitle());
		course.setDiscription(courseDetails.getDiscription());
		course.setPrice(courseDetails.getPrice());
		course.setDuration(courseDetails.getDuration());

		// Save/Update Category
		Category category = categoryService.getCategoryById(categoryId);
		course.setCategory(category);

		return courseService.saveCourse(course, categoryId, user.getId());
	}

	@DeleteMapping("/delete/{id}")
	public String deleteCourse(
			@PathVariable int id,
			Authentication authentication) {

		User user = userRepository.findByEmail(authentication.getName());
		if (user == null || user.getRole() != com.jsp.CourseHub.enums.Role.TRAINER) {
			throw new IllegalStateException("Only trainers can delete courses.");
		}

		Course course = courseService.getCourseById(id);
		if (course.getTrainer() == null || course.getTrainer().getId() != user.getId()) {
			throw new IllegalStateException("You are not authorized to delete this course.");
		}

		courseService.deleteCourse(id);
		return "Course Deleted Successfully";
	}
}

