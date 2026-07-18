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
    
    // update
    public Lessons update(Lessons l1)
    {
    	return repo.save(l1);
    }

    // Delete Lesson
    public String deleteLesson(int id) {
        Lessons lesson = findLessonById(id);
        repo.delete(lesson);
        return "Lesson Deleted Successfully";
    }
}