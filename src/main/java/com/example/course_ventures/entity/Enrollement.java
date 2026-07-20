package com.example.course_ventures.entity;

	import java.time.LocalDate;

	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
	import lombok.Setter;

	@Data
	@Entity
	public class Enrollement {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;

		private LocalDate enrollmentDate = LocalDate.now();

		private String status;

		@ManyToOne
		private Student student;

		@ManyToOne
		private Course course;

	

}
