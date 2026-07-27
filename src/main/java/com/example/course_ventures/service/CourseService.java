package com.example.course_ventures.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_ventures.entity.Course;
import com.example.course_ventures.entity.Trainer;
import com.example.course_ventures.exception.CourseNotFound;
import com.example.course_ventures.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	private TrainerService trainerService;;
	
	public Course saveCourse(Course c, int trainerId, int categoryId) {
		com.example.course_ventures.entity.Category category=categoryService.findCategoryById(categoryId);
		Trainer trainer=trainerService.findTrainerById(trainerId);
		
		c.setCategory(category);
		c.setTrainer(trainer);
     	return	courseRepo.save(c);
		
	}
	
	// find by id
	public Course findCourseById(int id) {
	    return courseRepo.findById(id).orElseThrow(() -> new CourseNotFound());
	}
	
	// find all
	public List<Course> findAllCourse() 
	{
	    return courseRepo.findAll();
	}
	
	// delete by id 
	public String deleteCourse(int id) 
	{
	    Course course = findCourseById(id);
	    courseRepo.delete(course);
	    return "Course Deleted Successfully";
	}
	
	// Update Course
	public Course updateCourse(int id, Course courseDetails, int categoryId, int trainerId) {

	    Course course = findCourseById(id);

	    if (categoryId > 0) {
	        course.setCategory(categoryService.findCategoryById(categoryId));
	    }

	    if (trainerId > 0) {
	        course.setTrainer(trainerService.findTrainerById(trainerId));
	    }

	    course.setTitle(courseDetails.getTitle());
	    course.setDescription(courseDetails.getDescription());
	    course.setPrice(courseDetails.getPrice());
	    course.setDuration(courseDetails.getDuration());

	    return courseRepo.save(course);
	}

	public List<Course> getAllCourses() {
	    return courseRepo.findAll();
	
	}
}
