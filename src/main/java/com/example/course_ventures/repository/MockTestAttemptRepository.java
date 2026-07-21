package com.example.course_ventures.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.course_ventures.entity.MockTest;
import com.example.course_ventures.entity.MocktestAttempt;

public interface MockTestAttemptRepository extends JpaRepository<MocktestAttempt, Integer> {

    List<MocktestAttempt> findByStudentId(int studentId);

    @Query("SELECT DISTINCT mta.mockTest FROM MockTestAttempt mta WHERE mta.student.id = :studentId AND mta.mockTest.course.id = :courseId")
    List<MockTest> findAttemptedMockTestsByStudentAndCourse(int studentId, int courseId);
}