package com.example.course_ventures.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_ventures.entity.Student;
import com.example.course_ventures.entity.User;
import com.example.course_ventures.repository.UserRepository;
import com.example.course_ventures.response.ResponseStructure;
import com.example.course_ventures.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	StudentService studentService;
	
	@Autowired
	UserRepository userRepository;

	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<Student>> saveStudent(@RequestBody Student student) {
		// Check if email already exists
		User existingUser = userRepository.findByemail(student.getEmail() != null ? student.getEmail().trim().toLowerCase() : null);
		if (existingUser != null) {
			ResponseStructure<Student> rs = new ResponseStructure<>();
			rs.setStatus(HttpStatus.CONFLICT.value());
			rs.setMessage("User with this email already exists");
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.CONFLICT);
		}
		
		Student savedStudent = studentService.saveStudent(student);
		
		ResponseStructure<Student> rs = new ResponseStructure<>();
		rs.setStatus(HttpStatus.CREATED.value());
		rs.setMessage("Student saved successfully");
		rs.setData(savedStudent);
		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}

	@GetMapping("/fetch")
	public List<Student> getAllStudents() {
	    return studentService.findAllStudent();
	}
}
