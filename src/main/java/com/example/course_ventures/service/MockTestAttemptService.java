package com.example.course_ventures.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.course_ventures.entity.MockTest;
import com.example.course_ventures.entity.MocktestAttempt;
import com.example.course_ventures.entity.Student;
import com.example.course_ventures.repository.MockTestAttemptRepository;

@Service
public class MockTestAttemptService {

    @Autowired
    private MockTestAttemptRepository repo;

    @Autowired
    private StudentService studentService;

    @Autowired
    private MockTestService mockTestService;

    // save
    public MocktestAttempt saveAttempt(MocktestAttempt attempt,int studentId,int mockTestId) {
        Student student = studentService.findStudentById(studentId);
        MockTest mockTest = mockTestService.findMockTestById(mockTestId);
        attempt.setStudent(student);
        attempt.setMockTest(mockTest);
        return repo.save(attempt);
    }

    // find by id
    public MocktestAttempt findAttemptById(int id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Attempt Not Found"));
    }
    
    // find all
    public List<MocktestAttempt> findAllAttempts() {
        return repo.findAll();
    }
    
    // update
    public MocktestAttempt update(MocktestAttempt attempt) {
        return repo.save(attempt);
    }

    // delete
    public String deleteAttempt(int id) {
        repo.delete(findAttemptById(id));
        return "Attempt Deleted Successfully";
    }
}