package com.example.course_ventures.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_ventures.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	List<Review> findByCourseId(int courseId);
	List<Review> findByStudentId(int studentId);

}
