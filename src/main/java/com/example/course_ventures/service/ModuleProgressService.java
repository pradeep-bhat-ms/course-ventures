package com.example.course_ventures.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_ventures.entity.Lessons;
import com.example.course_ventures.entity.ModuleProgress;
import com.example.course_ventures.entity.Student;
import com.example.course_ventures.repository.LessonsRepository;
import com.example.course_ventures.repository.ModuleProgressRepository;
import com.example.course_ventures.repository.ModuleRepository;



@Service
public class ModuleProgressService {

	@Autowired
	ModuleProgressRepository moduleProgressRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	LessonsRepository lessonsRepository;

	@Autowired
	StudentService studentService;

	public ModuleProgress getOrCreateProgress(int studentId, int moduleId) {
		Optional<ModuleProgress> existing = moduleProgressRepository.findByStudentIdAndModuleId(studentId, moduleId);
		if (existing.isPresent())	{
			return existing.get();
		}

		Student student = studentService.findStudentById(studentId);
		com.example.course_ventures.entity.Module module = moduleRepository.findById(moduleId)
				.orElseThrow(() -> new RuntimeException("Module Not Found"));
		
		List<Lessons> lessons = lessonsRepository.findByModuleId(moduleId);		
		ModuleProgress progress = new ModuleProgress();
		progress.setStudent(student);
		progress.setModule(module);
		progress.setCompletedLessons(0);
		progress.setTotalLessons(lessons.size());
		progress.setCompleted(false);
		
		return moduleProgressRepository.save(progress);
	}

	public ModuleProgress updateProgress(int studentId, int moduleId, int completedLessons) {
		ModuleProgress progress = getOrCreateProgress(studentId, moduleId);
		progress.setCompletedLessons(completedLessons);
		
		if (progress.getTotalLessons() > 0 && completedLessons >= progress.getTotalLessons()) {
			progress.setCompleted(true);
		}
		
		return moduleProgressRepository.save(progress);
	}

	public Optional<ModuleProgress> getStudentProgress(int studentId) {
	    return moduleProgressRepository.findById(studentId);
	}

	public Optional<ModuleProgress> getModuleProgress(int moduleId) {
	    return moduleProgressRepository.findById(moduleId);
	}

	public int calculateCourseProgress(int studentId, int courseId) {
		int totalModules = moduleRepository.countByCourseId(courseId);
		if (totalModules == 0) {
			return 0;
		}
		
		int completedModules = moduleProgressRepository.countCompletedModulesByStudentAndCourse(studentId, courseId);
		return (completedModules * 100) / totalModules;
	}
}

