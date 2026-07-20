package com.example.course_ventures.entity;

import java.util.List;

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
public class MockTest {

	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @NotBlank(message = "Testname should not be empty")
	    private String testname;

	    
	    @NotBlank(message=" test duration should not be empty")
	    @Min(value = 1, message = "TestDuration should be at least 1 minute")
	    private String Testduration;
	 
	    private int result;

	   
	    
	    private Course Course;
	    
	    private List<Question> questions;
}
