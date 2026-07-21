package com.example.course_ventures.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.entity.Lesson;
import com.jsp.CourseHub.entity.Module;
import com.jsp.CourseHub.entity.ModuleProgress;
import com.jsp.CourseHub.entity.Student;
import com.jsp.CourseHub.repository.LessonRepository;
import com.jsp.CourseHub.repository.ModuleProgressRepository;
import com.jsp.CourseHub.repository.ModuleRepository;
import com.jsp.CourseHub.service.StudentService;

@Service
public class ModuleProgressService {

	@Autowired
	ModuleProgressRepository moduleProgressRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	LessonRepository lessonRepository;

	@Autowired
	StudentService studentService;

	public ModuleProgress getOrCreateProgress(int studentId, int moduleId) {
		Optional<ModuleProgress> existing = moduleProgressRepository.findByStudentIdAndModuleId(studentId, moduleId);
		if (existing.isPresent())	{
			return existing.get();
		}

		Student student = studentService.getStudentById(studentId);
		Module module = moduleRepository.findById(moduleId)
				.orElseThrow(() -> new RuntimeException("Module Not Found"));
		
		List<Lesson> lessons = lessonRepository.findByModuleId(moduleId);
		
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

	public List<ModuleProgress> getStudentProgress(int studentId) {
		return moduleProgressRepository.findByStudentId(studentId);
	}

	public List<ModuleProgress> getModuleProgress(int moduleId) {
		return moduleProgressRepository.findByModuleId(moduleId);
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

