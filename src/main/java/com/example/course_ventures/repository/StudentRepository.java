package com.example.course_ventures.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_ventures.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

}
