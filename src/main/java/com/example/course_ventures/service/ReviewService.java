package com.example.course_ventures.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_ventures.entity.Course;
import com.example.course_ventures.entity.Review;
import com.example.course_ventures.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository repo;
	
	@Autowired
	CourseService service;
	
	public Review save(Review review, int id)
	{
		Course course=service.findCourseById(id);
		review.setCourse(course);
		return repo.save(review);
	}
	
	public List<Review> findAll()
	{
		return repo.findAll();	
	}

}
