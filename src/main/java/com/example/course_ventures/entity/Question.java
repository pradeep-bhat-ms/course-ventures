package com.example.course_ventures.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@NotBlank(message = "Question text should not be empty")
	private String questionText;

	
	@NotBlank(message="Option A Should not br empty")
	private String optionA;
	
	@NotBlank(message="Option B Should not br empty")
	private String optionB;
	
	@NotBlank(message="Option C Should not br empty")
	private String optionC;
	
	@NotBlank(message="Option D Should not br empty")
	private String optionD;

	@NotBlank(message = "Correct option should not be empty")
	@Pattern(regexp = "^[A-D]$", message = "Correct option must be A, B, C, or D")
	private String correctOption;


	@ManyToOne
	@JsonBackReference
   private MockTest mockTest;


   public String getCorrectOption() {
	// TODO Auto-generated method stub
	return null;
   }
}
