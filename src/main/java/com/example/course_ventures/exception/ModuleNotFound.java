package com.example.course_ventures.exception;

public class ModuleNotFound extends RuntimeException{
	
	@Override
	public String getMessage() {
		return "Module Not Found";
	}

}
