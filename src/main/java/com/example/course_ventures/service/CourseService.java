package com.example.course_ventures.service;

import java.util.List;

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
	TrainerService service;
	
	public Course save(Course c,int trainerid,int categoryId) {
		com.example.course_ventures.entity.Category category=categoryService.findCategoryById(categoryId);
		Trainer trainer=service.findTrainerById(trainerid);
		
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
	
	// partial update
	
	public Course update(Course c, int id,String name, double price, String duration)
	{
		Course course=findCourseById(id);
		course.setTitle(name);
		course.setPrice(price);
		course.setDuration(duration);
		return courseRepo.save(course);
	}
}
