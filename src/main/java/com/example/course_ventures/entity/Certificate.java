package com.example.course_ventures.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	    
	    private Student student;
	    
	    private Course course;
	    
	    @NotBlank(message = "certificateDescription name should not be empty")
	    private String certificateDescription;

	    @NotBlank(message = "Certificate number should not be empty")
	    private String certificateNumber;

	    @NotBlank(message="CertificateNmae should not be empty")
	    private String certificateName;
	 
	    
	    private LocalDateTime issueDate=LocalDateTime.now();

	    
	
}
