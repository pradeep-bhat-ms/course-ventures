package com.example.course_ventures.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.CourseHub.entity.User;
import com.jsp.CourseHub.repository.UserRepository;
import com.jsp.CourseHub.response.ResponseStructure;
import com.jsp.CourseHub.service.EmailService;
import com.jsp.CourseHub.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emService;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<User>> saveUser(@Valid @RequestBody User user) {
		// Check if email already exists
		User existingUser = userRepository.findByEmail(user.getEmail() != null ? user.getEmail().trim().toLowerCase() : null);
		if (existingUser != null) {
			ResponseStructure<User> rs = new ResponseStructure<>();
			rs.setStatus(HttpStatus.CONFLICT.value());
			rs.setMessage("User with this email already exists");
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.CONFLICT);
		}
		
		User saveUser = userService.saveUser(user);
		 
		ResponseStructure<User> rs = new ResponseStructure<>();
		rs.setStatus(HttpStatus.CREATED.value());
		rs.setMessage("User saved Successfully");
		rs.setData(saveUser);
		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}
	
	@GetMapping("/fetch")
	public List<User> getAllUser(){
		return userService.getAllUsers();
	}
	@PostMapping("/verify")
	public String verifyOtp(
	        @RequestParam int userId,
	        @RequestParam String otp) {

	    return userService.verifyOtp(userId, otp);
	}

	@PostMapping("/resend-otp")
	public ResponseEntity<String> resendOtp(@RequestParam int userId) {
		try {
			String response = userService.resendOtp(userId);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/forgot-password/request")
	public ResponseEntity<ResponseStructure<String>> requestForgotPassword(@RequestParam String email) {
		ResponseStructure<String> rs = new ResponseStructure<>();
		try {
			String response = userService.requestForgotPassword(email);
			rs.setStatus(HttpStatus.OK.value());
			rs.setMessage(response);
			rs.setData(response);
			return new ResponseEntity<>(rs, HttpStatus.OK);
		} catch (Exception e) {
			rs.setStatus(HttpStatus.BAD_REQUEST.value());
			rs.setMessage(e.getMessage());
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/forgot-password/reset")
	public ResponseEntity<ResponseStructure<String>> resetPassword(
			@RequestParam String email,
			@RequestParam String otp,
			@RequestParam String newPassword) {
		ResponseStructure<String> rs = new ResponseStructure<>();
		try {
			String response = userService.resetPassword(email, otp, newPassword);
			rs.setStatus(HttpStatus.OK.value());
			rs.setMessage(response);
			rs.setData(response);
			return new ResponseEntity<>(rs, HttpStatus.OK);
		} catch (Exception e) {
			rs.setStatus(HttpStatus.BAD_REQUEST.value());
			rs.setMessage(e.getMessage());
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
		}
	}
}
