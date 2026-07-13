package com.example.course_ventures.dto;

	import lombok.Data;
	import lombok.NoArgsConstructor;

	@Data
	@NoArgsConstructor
	public class TrainerResponse {

		private int id;
		private String name;
		private String mobile;
		private String email;
		private boolean verified;
		private String specialization;
		private int experience;

	}
