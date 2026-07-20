package com.example.course_ventures.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_ventures.entity.Enrollement;

public interface EnrollementRepository  extends JpaRepository<Enrollement, Integer>{
	Optional<Enrollement> findByStudentIdAndCourseId(int studentId, int courseId);

	java.util.List<Enrollement> findByStudentId(int studentId);

}
