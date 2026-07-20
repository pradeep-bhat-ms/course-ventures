package com.example.course_ventures.entity;

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
public class Lessons {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	   
	    @NotBlank(message = "Lesson title should not be empty")
	    private String lessonName;
	   
	    @NotBlank(message = "Lesson description should not be empty")
	    private String lessonDescription;
	   
	    private String contentType;
	    private String contentUrl;
	    private String lessonDuration;
	   
	    private Module module;
}
