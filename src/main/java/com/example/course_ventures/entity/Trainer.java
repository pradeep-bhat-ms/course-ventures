package com.example.course_ventures.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Trainer extends User {

	@NotBlank(message="Specilization should not be empty")
	private String specilization;
	
	@Min(value=0, message="Experience should be a positive number")
	private int experience;
	
	@OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Course> courses;
	
}

