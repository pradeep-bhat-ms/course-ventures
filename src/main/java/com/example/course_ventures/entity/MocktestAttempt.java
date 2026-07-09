package com.example.course_ventures.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class MocktestAttempt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Student student;  
	
	private MockTest mockTest;
	
	
	private int score;
	
	private String totalQuestions;
	
	private LocalDateTime attemptDate=LocalDateTime.now();

}
