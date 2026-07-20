package com.example.course_ventures.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ModuleProgress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Student student;

	@ManyToOne
	private Module module;

	private int completedLessons;
	private int totalLessons;
	private boolean completed;
	private LocalDateTime lastAccessed = LocalDateTime.now();
	private LocalDateTime completedAt;

}
