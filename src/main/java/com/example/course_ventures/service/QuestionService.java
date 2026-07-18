package com.example.course_ventures.service;

import org.springframework.stereotype.Service;

	import java.util.List;
	import org.springframework.beans.factory.annotation.Autowired;
	import com.example.course_ventures.entity.MockTest;
	import com.example.course_ventures.entity.Question;
	import com.example.course_ventures.repository.QuestionRepository;

	@Service
	public class QuestionService {

	    @Autowired
	    private QuestionRepository repo;

	    @Autowired
	    private MockTestService mockTestService;

	    // Save Question
	    public Question saveQuestion(Question question, int mockTestId) {
	        MockTest mockTest = mockTestService.findMockTestById(mockTestId);
	        question.setMockTest(mockTest);
	        return repo.save(question);
	    }

	    // Find Question By Id
	    public Question findQuestionById(int id) {
	        return repo.findById(id).orElseThrow(() -> new RuntimeException("Question Not Found"));
	    }

	    // Find All Questions
	    public List<Question> findAllQuestion() {
	        return repo.findAll();
	    }
	    
	    // update 
	    public Question update(Question question) {
	        return repo.save(question);
	    }

	    // Delete Question
	    public String deleteQuestion(int id) {
	        Question question = findQuestionById(id);
	        repo.delete(question);
	        return "Question Deleted Successfully";
	    }
	}


