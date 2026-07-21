package com.example.course_ventures.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
	import lombok.NoArgsConstructor;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class CourseResponseDto {
		 private int id;
		    private String title;
		    private String discription;
		    private double price;
		    private String duration;
		    private LocalDateTime createdDate;
		    private CourseResponseDto category;
		    private UserResponseDto trainer;
		
	}
