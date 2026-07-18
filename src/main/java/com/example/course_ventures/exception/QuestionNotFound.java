package com.example.course_ventures.exception;


public class QuestionNotFound extends RuntimeException{

	@Override
	public String getMessage() {
		return "Question Not Found";
	}
}
