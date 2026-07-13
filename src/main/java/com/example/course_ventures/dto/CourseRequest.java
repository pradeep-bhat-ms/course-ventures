package com.example.course_ventures.dto;

	import java.time.LocalDateTime;
	import jakarta.validation.constraints.Min;
	import jakarta.validation.constraints.NotBlank;
	import lombok.Data;
	import lombok.NoArgsConstructor;

	@Data
	@NoArgsConstructor
	public class CourseRequest {
				
		@NotBlank(message="Title should not be empty")
		private String title;
		
		@NotBlank(message="Description should not be empty")
		private String description;
		
		@NotBlank(message="Duration should not be empty")
		private String duration;
		
		@Min(value = 499, message="price should be a positive number")
		private double price;
		
		private LocalDateTime createdAt=LocalDateTime.now();
	}
