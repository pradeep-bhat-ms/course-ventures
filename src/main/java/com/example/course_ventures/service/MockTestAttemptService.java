package com.example.course_ventures.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_ventures.entity.Enrollement;
import com.example.course_ventures.entity.MockTest;
import com.example.course_ventures.entity.MocktestAttempt;
import com.example.course_ventures.entity.Question;
import com.example.course_ventures.entity.Student;
import com.example.course_ventures.exception.MockTestAttemptNotFound;
import com.example.course_ventures.exception.UnauthorizedException;
import com.example.course_ventures.repository.EnrollementRepository;
import com.example.course_ventures.repository.MockTestAttemptRepository;

@Service
public class MockTestAttemptService {

    @Autowired
    private MockTestAttemptRepository attemptRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private MockTestService mockTestService;

    @Autowired
    private EnrollementRepository enrollementRepository;

    // Submit Mock Test
    public MocktestAttempt saveAttempt(int studentId, int mockTestId, Map<Integer, String> answers) {

        Student student = studentService.findStudentById(studentId);
        MockTest mockTest = mockTestService.findMockTestById(mockTestId);

        // Verify Enrollment
        Optional<Enrollement> enrollement =
                enrollementRepository.findByStudentIdAndCourseId(
                        studentId,
                        mockTest.getCourse().getId());

        if (enrollement.isEmpty()
                || !"ENROLLED".equalsIgnoreCase(enrollement.get().getStatus())) {

            throw new UnauthorizedException(
                    "You must purchase the course '"
                            + mockTest.getCourse().getTitle()
                            + "' before attempting this mock test.");
        }

        // Calculate Score
        int score = 0;

        List<Question> questions = mockTest.getQuestions();

        for (Question question : questions) {

            String studentAnswer = answers.get(question.getId());

            if (studentAnswer != null
                    && studentAnswer.trim()
                            .equalsIgnoreCase(question.getCorrectOption().trim())) {

                score++;
            }
        }

        MocktestAttempt attempt = new MocktestAttempt();

        attempt.setStudent(student);
        attempt.setMockTest(mockTest);
        attempt.setScore(score);
        attempt.setTotalQuestions(questions.size());
        attempt.setAttemptTime(LocalDateTime.now());

        return attemptRepository.save(attempt);
    }

    // Find Attempt By Id
    public MocktestAttempt findAttemptById(int id) {

        return attemptRepository.findById(id)
                .orElseThrow(() -> new MockTestAttemptNotFound("Attempt Not Found"));
    }

    // Find All Attempts
    public List<MocktestAttempt> findAllAttempts() {

        return attemptRepository.findAll();
    }

    // Get Attempts By Student
    public List<MocktestAttempt> getAttemptsByStudentId(int studentId) {

        studentService.findStudentById(studentId);

        return attemptRepository.findByStudentId(studentId);
    }

    // Update Attempt
    public MocktestAttempt updateAttempt(int id, MocktestAttempt attemptDetails) {

        MocktestAttempt attempt = findAttemptById(id);

        attempt.setScore(attemptDetails.getScore());
        attempt.setTotalQuestions(attemptDetails.getTotalQuestions());
        attempt.setAttemptTime(attemptDetails.getAttemptTime());

        return attemptRepository.save(attempt);
    }

    // Delete Attempt
    public String deleteAttempt(int id) {

        MocktestAttempt attempt = findAttemptById(id);

        attemptRepository.delete(attempt);

        return "Attempt Deleted Successfully";
    }
}