package com.example.course_ventures.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleExceptionHandler{
	
	@ExceptionHandler(CourseNotFound.class)
	public String CourseNF(CourseNotFound c)
	{
		return c.getMessage();
	}
	
	@ExceptionHandler(ModuleNotFound.class)
	public String ModuleNF(ModuleNotFound m)
	{
		return m.getMessage();
	}
	
	@ExceptionHandler(LessonNotFound.class)
	public String LessonNF(LessonNotFound l)
	{
		return l.getMessage();
	}
	
	@ExceptionHandler(MockTestNotFound.class)
	public String MockTestNF(MockTestNotFound mk1)
	{
	    return mk1.getMessage();
	}
	
	@ExceptionHandler(QuestionNotFound.class)
	public String QuestionNF(QuestionNotFound q1)
	{
		return q1.getMessage();
	}
	
	}

