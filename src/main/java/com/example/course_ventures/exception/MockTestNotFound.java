package com.example.course_ventures.exception;

public class MockTestNotFound extends RuntimeException {
	@Override
	public String getMessage() {
		return "Module Not Found";
	}

}
