package com.example.course_ventures.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_ventures.entity.Lessons;

public interface LessonsRepository extends JpaRepository<Lessons, Integer>{

List<Lessons> findByModuleId(int moduleId);
	
	List<Lessons> findByModuleIdOrderByOrderIndexAsc(int moduleId);

}
