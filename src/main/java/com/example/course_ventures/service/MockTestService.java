package com.example.course_ventures.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.course_ventures.entity.Course;
import com.example.course_ventures.entity.MockTest;
import com.example.course_ventures.entity.Question;
import com.example.course_ventures.exception.MockTestNotFound;
import com.example.course_ventures.repository.MockTestRepository;
import com.example.course_ventures.repository.QuestionRepository;

@Service
public class MockTestService {
	
	    @Autowired
	    private MockTestRepository repo;

	    @Autowired
	    private CourseService courseService;
	    
	    @Autowired
	    private QuestionRepository questionRepository;

	    @Autowired
	    private StudentService studentService;

	    @Autowired
	    private com.example.course_ventures.repository.MockTestAttemptRepository mockTestAttemptRepository;

	    public MockTest saveMockTest(MockTest test, int courseId) {

	        Course course = courseService.findCourseById(courseId);
	        test.setCourse(course);

	        if (test.getQuestions() != null) {
	            for (Question q : test.getQuestions()) {
	                q.setMockTest(test);
	            }
	        }

	        return repo.save(test);
	    }
	    
	    
	    // find by id
	    public MockTest findMockTestById(int id)
	    {
	    	return repo.findById(id)
	    	        .orElseThrow(() -> new MockTestNotFound());	    }

	    // find all
	    public List<MockTest> getMockTestsByCourseId(int courseId) {

	        courseService.findCourseById(courseId);

	        return repo.findByCourseId(courseId);
	    }
	    
	    public MockTest updateMockTest(int id, MockTest testDetails) {

	        MockTest test = findMockTestById(id);

	        test.setTestname(testDetails.getTestname());
	        test.setTestDuration(testDetails.getTestDuration());
	        test.setResult(testDetails.getResult());

	        return repo.save(test);
	    }
	    
	    public Question addQuestionToMockTest(int mockTestId, Question question) {

	        MockTest mockTest = findMockTestById(mockTestId);

	        question.setMockTest(mockTest);

	        return questionRepository.save(question);
	    }
	    
	    public void deleteMockTest(int id) {

	        MockTest mockTest = findMockTestById(id);

	        repo.delete(mockTest);
	    }

	    public com.example.course_ventures.entity.MocktestAttempt submitAttempt(
	            int studentId, int mockTestId, java.util.Map<String, String> answers) {

	        MockTest mockTest = findMockTestById(mockTestId);
	        com.example.course_ventures.entity.Student student = studentService.findStudentById(studentId);

	        List<Question> questions = mockTest.getQuestions();
	        int score = 0;
	        for (Question q : questions) {
	            String submitted = answers.get(String.valueOf(q.getId()));
	            if (submitted != null && submitted.equalsIgnoreCase(q.getCorrectOption())) {
	                score++;
	            }
	        }

	        com.example.course_ventures.entity.MocktestAttempt attempt =
	                new com.example.course_ventures.entity.MocktestAttempt();
	        attempt.setStudent(student);
	        attempt.setMockTest(mockTest);
	        attempt.setScore(score);
	        attempt.setTotalQuestions(questions.size());

	        return mockTestAttemptRepository.save(attempt);
	    }
}