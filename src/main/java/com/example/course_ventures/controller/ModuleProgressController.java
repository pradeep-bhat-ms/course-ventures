package com.example.course_ventures.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_ventures.entity.ModuleProgress;
import com.example.course_ventures.service.ModuleProgressService;




@RestController
@RequestMapping("/module-progress")
public class ModuleProgressController {


	@Autowired
	ModuleProgressService moduleProgressService;

	@PostMapping("/update")
	public ModuleProgress updateProgress(@RequestParam int studentId, @RequestParam int moduleId, @RequestParam int completedLessons) {
		return moduleProgressService.updateProgress(studentId, moduleId, completedLessons);
	}

	@GetMapping("/student/{studentId}")
	public java.util.List<ModuleProgress> getStudentProgress(@PathVariable int studentId) {
		return moduleProgressService.getStudentProgress(studentId);
	}

	@GetMapping("/module/{moduleId}")
	public java.util.List<ModuleProgress> getModuleProgress(@PathVariable int moduleId) {
		return moduleProgressService.getModuleProgress(moduleId);
	}

	@GetMapping("/course-progress")
	public int getCourseProgress(@RequestParam int studentId, @RequestParam int courseId) {
		return moduleProgressService.calculateCourseProgress(studentId, courseId);
	}
}