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
	CourseRepository courseRepo;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	TrainerService  trainerService;
	
	public Course save(Course c,int trainerid,int categoryId) {
		com.example.course_ventures.entity.Category category=categoryService.findCategoryById(categoryId);
		Trainer trainer=trainerService.findTrainerById(trainerid);
		
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

	public Map<String, Object> getAllCourses() {
		// TODO Auto-generated method stub
		return null;
	}
}
