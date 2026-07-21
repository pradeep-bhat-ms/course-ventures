package com.example.course_ventures.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class StudentRequestDto {

	@NotBlank(message="College name Should not be empty")
	private String collegename;
	
	@NotBlank(message="degree Should not be empty")
	private String degree;
	
	@NotBlank(message="stream Should not be empty")
	private String stream;
	
	
	@Min(value = 1900, message = "Year of passing should be after 1900.")
	@Max(value = 2100, message = "Year of passing should not exceed 2100.")
	private int yop;
	
	@DecimalMin(value = "0.0", message = "CGPA should not be less than 0.0")
	@DecimalMax(value = "10.0", message = "CGPA should not be greater than 10.0")
	private double cgpa;

}
