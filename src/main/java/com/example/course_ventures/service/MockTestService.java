package com.example.course_ventures.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.course_ventures.entity.Course;
import com.example.course_ventures.entity.MockTest;
import com.example.course_ventures.repository.MockTestRepository;

@Service
public class MockTestService {
	
	    @Autowired
	    private MockTestRepository repo;

	    @Autowired
	    private CourseService courseService;

	    // save
	    public MockTest saveMockTest(MockTest test, int courseId) {
	        Course course = courseService.findCourseById(courseId);
	        test.setCourseName(course);
	        return repo.save(test);
	    }
	    // find by id
	    public MockTest findMockTestById(int id)
	    {
	        return repo.findById(id).orElseThrow(() -> new RuntimeException("Mock Test Not Found"));
	    }

	    // find all
	    public List<MockTest> findAllMockTest() {
	        return repo.findAll();
	    }
	    
	    // update
	    public MockTest update(MockTest M1)
	    {
	    	return repo.save(M1);
	    }
	    
	    // delete
	    public String deleteMockTest(int id)
	    {
	        repo.delete(findMockTestById(id));
	        return "Mock Test Deleted Successfully";
	    }
}
