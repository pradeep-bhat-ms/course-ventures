package com.example.course_ventures.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Course  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
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
