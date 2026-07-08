package com.example.course_ventures.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Trainer extends User {

	@NotBlank(message="Specilization should not be empty")
	private String specilization;
	
	@Min(value=0, message="Experience should be a positive number")
	private int experience;
	
}
