package com.example.course_ventures.dto;

	
	import jakarta.validation.constraints.Min;
	import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
	import lombok.NoArgsConstructor;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class CourseRequestDto {
				
	    @NotBlank(message = "Course title should not be empty")
		private String title;
		
		private String description;
		
	    @Min(value = 1, message = "Duration must be greater than zero")
		private String duration;
		
	    @Min(value = 1, message = "Price must be greater than zero")
		private double price;
		
	    private int categoryId;
	    
	    private int trainerId;
	}
