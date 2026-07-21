package com.example.course_ventures.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_ventures.entity.CourseProgress;

public interface CourseProgressRepository extends JpaRepository<CourseProgress,Integer> {
	 Optional<CourseProgress> findByStudentIdAndCourseId(int studentId, int courseId);
	    
	    List<CourseProgress> findByStudentId(int studentId);
	    
	    List<CourseProgress> findByCourseId(int courseId);
	    
	    List<CourseProgress> findByCompletedTrue();
}
