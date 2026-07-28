package com.example.course_ventures.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_ventures.dto.ReviewRequestDto;
import com.example.course_ventures.dto.ReviewResponseDto;
import com.example.course_ventures.entity.Course;
import com.example.course_ventures.entity.Review;
import com.example.course_ventures.entity.Student;
import com.example.course_ventures.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository repo;
	
	@Autowired
	private StudentService studentService;
	
	
	@Autowired
	CourseService service;
	
	public Review saveReview(Review review, int studentId, int courseId)
	{
	    Student student = studentService.findStudentById(studentId);
	    Course course = service.findCourseById(courseId);

	    review.setStudent(student);
	    review.setCourse(course);

	    return repo.save(review);
	}
	
	public List<Review> getCourseReviews(int courseId)
	{
	    service.findCourseById(courseId);

	    return repo.findByCourseId(courseId);
	}
	
	public List<Review> getStudentReviews(int studentId)
	{
	    studentService.findStudentById(studentId);

	    return repo.findByStudentId(studentId);
	}
	
	public double getAverageRating(int courseId)
	{
	    List<Review> reviews = repo.findByCourseId(courseId);

	    if (reviews.isEmpty())
	    {
	        return 0.0;
	    }

	    return reviews.stream()
	            .mapToInt(Review::getRating)
	            .average()
	            .orElse(0.0);
	}

	public ReviewResponseDto addReview(ReviewRequestDto requestDto) {

	    Student student = studentService.findStudentById(requestDto.getStudentId());

	    Course course = service.findCourseById(requestDto.getCourseId());

	    Review review = new Review();
	    review.setRating(requestDto.getRating());
	    review.setComment(requestDto.getComment());
	    review.setStudent(student);
	    review.setCourse(course);

	    Review saved = repo.save(review);

	    ReviewResponseDto dto = new ReviewResponseDto();
	    dto.setId(saved.getId());
	    dto.setRating(saved.getRating());
	    dto.setComment(saved.getComment());

	    return dto;
	}

}
