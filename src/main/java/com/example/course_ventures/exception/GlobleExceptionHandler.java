package com.example.course_ventures.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleExceptionHandler {

	@ExceptionHandler(CourseNotFound.class)
	public String CourseNF(CourseNotFound c)
	{
		return c.getMessage();
	}
}
