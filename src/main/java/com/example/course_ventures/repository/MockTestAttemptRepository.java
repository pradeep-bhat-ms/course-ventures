package com.example.course_ventures.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_ventures.entity.MocktestAttempt;

public interface MockTestAttemptRepository extends JpaRepository<MocktestAttempt, Integer> {

    List<MocktestAttempt> findByStudentId(int studentId);

}