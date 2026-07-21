package com.example.course_ventures.entity;

import com.example.course_ventures.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="users")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) //  this class is super class  and inherited 
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // joining two table like user and trainer and student // multiple table executes simultanesouly
	private int id;
	
	@NotBlank(message="name should not be empty")
	@Size(min=3, max=20 , message="name should  be beteween 3 to 20")
	private String name;
	
	@Email(message="email should be in valid format")
	private String email;
	
	@NotBlank(message = "Password is required")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$",message = "Password must be 8-20 characters long and include uppercase, lowercase, digit, and special character.")
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	private String otp;
	private boolean verified=false;
	
	private boolean approved=false;
	public void setEmail(String email) {
		this.email = email != null ? email.trim().toLowerCase() : null;
	}

}
