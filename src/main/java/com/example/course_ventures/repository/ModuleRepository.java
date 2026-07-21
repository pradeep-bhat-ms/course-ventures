package com.example.course_ventures.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.course_ventures.entity.Module;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
	List<Module> findByCourseIdOrderByOrderIndexAsc(int courseId);
	List<Module> findByCourseId(int courseId);
	@Query("SELECT COUNT(m) FROM Module m WHERE m.course.id = :courseId")
	int countByCourseId(int courseId);
}