package com.example.course_ventures.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity

public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@NotBlank(message="category Name should not be empty")
	private String categoryName;
	
	@NotBlank(message="category Description should not be empty")
	private String categoryDescription;

}


// review mock test and certificate 