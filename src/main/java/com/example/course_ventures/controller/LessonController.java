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

import com.example.course_ventures.entity.Lessons;
import com.example.course_ventures.entity.User;
import com.example.course_ventures.enums.Role;
import com.example.course_ventures.repository.UserRepository;
import com.example.course_ventures.service.LessonService;

@RestController
@RequestMapping("/lesson")
public class LessonController {

	@Autowired
	LessonService lessonService;

	@Autowired
	UserRepository userRepository;

	@PostMapping("/save")
	public Lessons saveLesson(@RequestBody Lessons lesson, @RequestParam int moduleId, Authentication authentication) {
		User user = userRepository.findByemail(authentication.getName());
		if (user == null || (user.getRole() != Role.TRAINER && user.getRole() !=Role.ADMIN)) {
			throw new IllegalStateException("Only trainers can create lessons.");
		}
		return lessonService.saveLesson(lesson, moduleId);
	}

	@GetMapping("/module/{moduleId}")
	public List<Lessons> getLessonsByModuleId(@PathVariable int moduleId) {
		return lessonService.getLessonsByModuleId(moduleId);
	}

	@GetMapping("/{id}")
	public Lessons getLessonById(@PathVariable int id) {
		return lessonService.findLessonById(id);
	}

	@PutMapping("/update/{id}")
	public Lessons updateLesson(@PathVariable int id, @RequestBody Lessons lessonDetails, Authentication authentication) {
		User user = userRepository.findByemail(authentication.getName());
		if (user == null || (user.getRole() != Role.TRAINER && user.getRole() != Role.ADMIN)) {
			throw new IllegalStateException("Only trainers can update lessons.");
		}
		return lessonService.updateLesson(id, lessonDetails);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteLesson(@PathVariable int id, Authentication authentication) {
		User user = userRepository.findByemail(authentication.getName());
		if (user == null || (user.getRole() != Role.TRAINER && user.getRole() != Role.ADMIN)) {
			throw new IllegalStateException("Only trainers can delete lessons.");
		}
		lessonService.deleteLesson(id);
		return "Lesson Deleted Successfully";
	}

}
