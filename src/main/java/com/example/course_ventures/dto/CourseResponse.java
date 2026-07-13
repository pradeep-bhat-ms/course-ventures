package com.example.course_ventures.dto;

import java.time.LocalDateTime;

	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.validation.constraints.Min;
	import jakarta.validation.constraints.NotBlank;
	import lombok.Data;
	import lombok.NoArgsConstructor;

	@Data
	@NoArgsConstructor
	public class CourseResponse {
		private String title;
		private String description;
		private String duration;
		private double price;
		private LocalDateTime createdAt=LocalDateTime.now();
		
	}
