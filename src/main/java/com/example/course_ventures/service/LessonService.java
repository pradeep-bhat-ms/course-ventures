package com.example.course_ventures.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_ventures.entity.Lessons;
import com.example.course_ventures.entity.Module;
import com.example.course_ventures.exception.LessonNotFound;
import com.example.course_ventures.repository.LessonsRepository;

@Service
public class LessonService {

    @Autowired
    private LessonsRepository repo;

    @Autowired
    private ModuleService moduleService;

    // Save Lesson
    public Lessons saveLesson(Lessons lesson, int moduleId) {
        Module module = moduleService.findModuleById(moduleId);
        lesson.setModule(module);
        return repo.save(lesson);
    }

    // Find Lesson By Id
    public Lessons findLessonById(int id) {
        return repo.findById(id).orElseThrow(() -> new LessonNotFound());
    }

    // Find All Lessons
    public List<Lessons> findAllLesson() {
        return repo.findAll();
    }
    
	public Lessons updateLesson(int id, Lessons lessonDetails) {
		Lessons lesson = findLessonById(id);
		lesson.setTitle(lessonDetails.getTitle());
		lesson.setContentType(lessonDetails.getContentType());
		lesson.setContentUrl(lessonDetails.getContentUrl());
		lesson.setTextContent(lessonDetails.getTextContent());
		lesson.setOrderIndex(lessonDetails.getOrderIndex());
		lesson.setDurationMinutes(lessonDetails.getDurationMinutes());
		return repo.save(lesson);
	}

    // Delete Lesson
    public String deleteLesson(int id) {
        Lessons lesson = findLessonById(id);
        repo.delete(lesson);
        return "Lesson Deleted Successfully";
    }
}