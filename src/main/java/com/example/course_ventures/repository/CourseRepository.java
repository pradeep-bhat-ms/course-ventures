package com.example.course_ventures.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_ventures.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer>{

}
