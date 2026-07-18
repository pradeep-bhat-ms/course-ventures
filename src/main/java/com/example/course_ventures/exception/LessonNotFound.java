package com.example.course_ventures.exception;

public class LessonNotFound extends RuntimeException {
	
	@Override
	public String getMessage() {
		return "Lesson Not Found";
	}

}
