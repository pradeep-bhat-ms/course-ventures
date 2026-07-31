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

import com.example.course_ventures.entity.Category;
import com.example.course_ventures.entity.Course;
import com.example.course_ventures.entity.User;
import com.example.course_ventures.enums.Role;
import com.example.course_ventures.repository.UserRepository;
import com.example.course_ventures.service.CategoryService;
import com.example.course_ventures.service.CourseService;


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
	        Authentication authentication) {
		System.out.println("Category from request = " + course.getCategory());
		System.out.println("Category ID = " + course.getCategory().getId());

	    User user = userRepository.findByemail(authentication.getName());

	    if (user == null || user.getRole() != Role.TRAINER) {
	        throw new IllegalStateException("Only trainers can create courses.");
	    }

	    return courseService.saveCourse(
	            course,
	            course.getCategory().getId(),
	            user.getId()
	    );
	}
	
	@GetMapping("/{id}")
	public Course getCourse(@PathVariable int id) {
	    return courseService.findCourseById(id);
	}

	@GetMapping("/fetch")
	public List<Course> getAllCourses() {
	    return courseService.getAllCourses();
	}

	@GetMapping("/fetch/{id}")
	public Course getCourseById(@PathVariable int id) {

		return courseService.findCourseById(id);
	}

	@PostMapping("/update/{id}")
	public Course updateCourse(
			@PathVariable int id,
			@RequestBody Course courseDetails,
			@RequestParam int categoryId,
			Authentication authentication) {

		User user = userRepository.findByemail(authentication.getName());
		if (user == null || user.getRole() != Role.TRAINER) {
			throw new IllegalStateException("Only trainers can update courses.");
		}

		Course course = courseService.findCourseById(id);
		if (course.getTrainer() == null || course.getTrainer().getId() != user.getId()) {
			throw new IllegalStateException("You are not authorized to update this course.");
		}

		course.setTitle(courseDetails.getTitle());
		course.setDescription(courseDetails.getDescription());
		course.setPrice(courseDetails.getPrice());
		course.setDuration(courseDetails.getDuration());

		// Save/Update Category
		Category category = categoryService.findCategoryById(categoryId);
		course.setCategory(category);

		return courseService.saveCourse(course, categoryId, user.getId());
	}

	@DeleteMapping("/delete/{id}")
	public String deleteCourse(
	        @PathVariable int id,
	        Authentication authentication) {

	    User user = userRepository.findByemail(authentication.getName());

	    if (user == null) {
	        throw new IllegalStateException("User not found.");
	    }

	    Course course = courseService.findCourseById(id);

	    // Admin can delete any course
	    if (user.getRole() == Role.ADMIN) {
	        courseService.deleteCourse(id);
	        return "Course Deleted Successfully";
	    }

	    // Trainer can delete only their own course
	    if (user.getRole() == Role.TRAINER) {

	        if (course.getTrainer() == null ||
	                course.getTrainer().getId() != user.getId()) {

	            throw new IllegalStateException(
	                    "You are not authorized to delete this course.");
	        }

	        courseService.deleteCourse(id);
	        return "Course Deleted Successfully";
	    }

	    throw new IllegalStateException(
	            "Only Admin or Trainer can delete courses.");
	}
}

