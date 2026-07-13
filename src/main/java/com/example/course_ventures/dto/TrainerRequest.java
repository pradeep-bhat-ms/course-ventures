package com.example.course_ventures.dto;

import com.example.course_ventures.enums.Role;
import jakarta.persistence.EnumType;
	import jakarta.persistence.Enumerated;
	import jakarta.validation.constraints.Email;
	import jakarta.validation.constraints.Min;
	import jakarta.validation.constraints.NotBlank;
	import jakarta.validation.constraints.Pattern;
	import jakarta.validation.constraints.Size;
	import lombok.Data;
	import lombok.NoArgsConstructor;

	@Data
	@NoArgsConstructor
	public class TrainerRequest {
		
		@NotBlank
		@Size
		private String name;
		
		@Pattern(regexp = "^[0-9]{10}$",message = "Mobile number should be 10")
		private String mobile;
		
		@Email
		private String email;
		
		@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
			    message = "Password must contain at least 8 characters")
		private String password;
		
		@Enumerated(EnumType.STRING) 
		private Role role;
		private String opt;
		private boolean verified=false;
		@NotBlank
		private String specialization;
		
		@Min(value = 0,message = "Experience should be a positive number")
		private int experience;
	
}
