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

import com.example.course_ventures.entity.Trainer;
import com.example.course_ventures.entity.User;
import com.example.course_ventures.repository.UserRepository;
import com.example.course_ventures.response.ResponseStructure;
import com.example.course_ventures.service.TrainerService;




@RestController
@RequestMapping("/trainer")
 public class TrainerController { 

	@Autowired
	TrainerService trainerService;
	
	@Autowired
	UserRepository userRepository;

	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<Trainer>> saveTrainer(@RequestBody Trainer trainer) {
		// Check if email already exists
		User existingUser = userRepository.findByemail(trainer.getEmail() != null ? trainer.getEmail().trim().toLowerCase() : null);
		if (existingUser != null) {
			ResponseStructure<Trainer> rs = new ResponseStructure<>();
			rs.setStatus(HttpStatus.CONFLICT.value());
			rs.setMessage("User with this email already exists");
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.CONFLICT);
		}
		
		Trainer savedTrainer = trainerService.saveTrainerInfo(trainer);
		
		ResponseStructure<Trainer> rs = new ResponseStructure<>();
		rs.setStatus(HttpStatus.CREATED.value());
		rs.setMessage("Trainer saved successfully");
		rs.setData(savedTrainer);
		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}

	@GetMapping("/fetch")
	public List<Trainer> getAllTrainers() {

		return trainerService.getAllTrainers();
	}
}
