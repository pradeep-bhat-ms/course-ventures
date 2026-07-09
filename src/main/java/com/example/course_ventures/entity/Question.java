package com.example.course_ventures.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@NotBlank(message="question  should not be blank")
	private String question;
	
	@NotBlank(message="Option A Should not br empty")
	private String A;
	
	@NotBlank(message="Option B Should not br empty")
	private String B;
	
	@NotBlank(message="Option C Should not br empty")
	private String C;
	
	@NotBlank(message="Option D Should not br empty")
	private String D;

	@NotBlank(message="Answer should not be empty")
	@Pattern(regexp = "^[ABCD]$ ", message="Answer must be one of the options : A,B,C,D")
	private String answer;
}
