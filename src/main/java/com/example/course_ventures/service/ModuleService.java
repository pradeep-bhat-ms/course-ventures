package com.example.course_ventures.service;

import java.util.List;
import com.example.course_ventures.entity.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_ventures.entity.Course;
import com.example.course_ventures.repository.ModuleRepository;

@Service
public class ModuleService {
	
	
    @Autowired
    private ModuleRepository repo;

    @Autowired
    private CourseService courseService;

    
    // Add Module
    public Module saveModule(Module module, int courseId) {

        Course course = courseService.findCourseById(courseId);
        module.setCourse(course);
        return repo.save(module);
    }
    
    
    // Get Module By Id
    public Module findModuleById(int id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Module Not Found"));
    }
    
    // Get All Modules
    public List<Module> findAllModules() {
        return repo.findAll();
    }
    
    

}
