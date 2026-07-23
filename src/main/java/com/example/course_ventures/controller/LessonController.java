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

import com.jsp.CourseHub.entity.Lesson;
import com.jsp.CourseHub.entity.User;
import com.jsp.CourseHub.repository.UserRepository;
import com.jsp.CourseHub.service.LessonService;


@RestController
@RequestMapping("/lesson")
public class LessonController {

	@Autowired
	LessonService lessonService;

	@Autowired
	UserRepository userRepository;

	@PostMapping("/save")
	public Lesson saveLesson(@RequestBody Lesson lesson, @RequestParam int moduleId, Authentication authentication) {
		User user = userRepository.findByEmail(authentication.getName());
		if (user == null || (user.getRole() != com.jsp.CourseHub.enums.Role.TRAINER && user.getRole() != com.jsp.CourseHub.enums.Role.ADMIN)) {
			throw new IllegalStateException("Only trainers can create lessons.");
		}
		return lessonService.saveLesson(lesson, moduleId);
	}

	@GetMapping("/module/{moduleId}")
	public List<Lesson> getLessonsByModuleId(@PathVariable int moduleId) {
		return lessonService.getLessonsByModuleId(moduleId);
	}

	@GetMapping("/{id}")
	public Lesson getLessonById(@PathVariable int id) {
		return lessonService.getLessonById(id);
	}

	@PutMapping("/update/{id}")
	public Lesson updateLesson(@PathVariable int id, @RequestBody Lesson lessonDetails, Authentication authentication) {
		User user = userRepository.findByEmail(authentication.getName());
		if (user == null || (user.getRole() != com.jsp.CourseHub.enums.Role.TRAINER && user.getRole() != com.jsp.CourseHub.enums.Role.ADMIN)) {
			throw new IllegalStateException("Only trainers can update lessons.");
		}
		return lessonService.updateLesson(id, lessonDetails);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteLesson(@PathVariable int id, Authentication authentication) {
		User user = userRepository.findByEmail(authentication.getName());
		if (user == null || (user.getRole() != com.jsp.CourseHub.enums.Role.TRAINER && user.getRole() != com.jsp.CourseHub.enums.Role.ADMIN)) {
			throw new IllegalStateException("Only trainers can delete lessons.");
		}
		lessonService.deleteLesson(id);
		return "Lesson Deleted Successfully";
	}
}


}
