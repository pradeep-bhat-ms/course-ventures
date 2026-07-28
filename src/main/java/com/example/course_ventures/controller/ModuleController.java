package com.example.course_ventures.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_ventures.entity.Module;
import com.example.course_ventures.entity.User;
import com.example.course_ventures.enums.Role;
import com.example.course_ventures.repository.UserRepository;
import com.example.course_ventures.service.ModuleService;



@RestController
@RequestMapping("/module")
public class ModuleController {


	@Autowired
	ModuleService moduleService;

	@Autowired
	UserRepository userRepository;

	@PostMapping("/save")
	public Module saveModule(@RequestBody Module module, @RequestParam int courseId, Authentication authentication) {
		User user = userRepository.findByemail(authentication.getName());
		if (user == null || (user.getRole() != Role.TRAINER && user.getRole() != Role.ADMIN)) {
			throw new IllegalStateException("Only trainers can create modules.");
		}
		return moduleService.saveModule(module, courseId);
	}

	@GetMapping("/course/{courseId}")
	public List<com.example.course_ventures.entity.Module> getModulesByCourseId(@PathVariable int courseId) {
		return moduleService.getModulesByCourseId(courseId);
	}

	@GetMapping("/{id}")
	public com.example.course_ventures.entity.Module getModuleById(@PathVariable int id) {
		return moduleService.findModuleById(id);
	}

	@PutMapping("/update/{id}")
	public Module updateModule(@PathVariable int id, @RequestBody Module moduleDetails, Authentication authentication) {
		User user = userRepository.findByemail(authentication.getName());
		if (user == null || (user.getRole() !=Role.TRAINER && user.getRole() !=Role.ADMIN)) {
			throw new IllegalStateException("Only trainers can update modules.");
		}
		return moduleService.updateModule(id, moduleDetails);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteModule(@PathVariable int id, Authentication authentication) {
		User user = userRepository.findByemail(authentication.getName());
		if (user == null || (user.getRole() != Role.TRAINER && user.getRole() != Role.ADMIN)) {
			throw new IllegalStateException("Only trainers can delete modules.");
		}
		moduleService.deleteModule(id);
		return "Module Deleted Successfully";
	}
}
