package com.example.course_ventures.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.course_ventures.entity.ModuleProgress;


public interface ModuleProgressRepository extends JpaRepository<ModuleProgress, Integer> {
	
	Optional<ModuleProgress> findByStudentIdAndModuleId(int studentId, int moduleId);
	
	List<ModuleProgress> findByStudentId(int studentId);
	
	List<ModuleProgress> findByModuleId(int moduleId);
	
	@Query("SELECT COUNT(mp) FROM ModuleProgress mp WHERE mp.student.id = :studentId AND mp.module.course.id = :courseId AND mp.completed = true")
	int countCompletedModulesByStudentAndCourse(int studentId, int courseId);
}
