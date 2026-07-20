package com.example.course_ventures.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class CourseProgress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Student student;
	
	@ManyToOne
	private Course course;
	
	@Min(value = 0, message = "Progress cannot be negative")
	@Max(value = 100, message = "Progress cannot exceed 100")
	private int progressPercentage;
	
	private LocalDateTime lastUpdated = LocalDateTime.now();
	
	private boolean completed = false;
	
	private LocalDateTime completionDate;
	

}
