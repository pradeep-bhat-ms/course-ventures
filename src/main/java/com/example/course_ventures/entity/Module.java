package com.example.course_ventures.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Module {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private Course course; // relation ship
	    
	    @NotBlank(message = "Module name should not be empty")
	    private String moduleTitle;
	    
	    @NotBlank(message = "Module description should not be empty")
	    private String moduleDescription;
	    
	    
	    private String moduleDuration;
	    
	    private  int numberOfLessons;
	    
	    private int orderIndex;
	    
	    private List<Lessons> lessons = new ArrayList();
}
