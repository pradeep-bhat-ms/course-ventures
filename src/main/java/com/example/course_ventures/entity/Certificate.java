package com.example.course_ventures.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Certificate {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    
		@ManyToOne
	    private Student student;
		
		@ManyToOne
	    private Course course;
	    
		
	    @NotBlank(message = "Certificate number should not be empty")
	    private String certificateNumber;

	    private String certificateUrl;
	 
	    
	    private LocalDateTime issueDate=LocalDateTime.now();

	    
	
}
