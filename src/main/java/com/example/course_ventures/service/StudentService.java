package com.example.course_ventures.service;

	import java.util.List;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.security.core.userdetails.UsernameNotFoundException;
	import org.springframework.stereotype.Service;

	import com.example.course_ventures.entity.Student;
	import com.example.course_ventures.enums.Role;
import com.example.course_ventures.exception.StudentNotFound;
import com.example.course_ventures.repository.StudentRepository;

	@Service
	public class StudentService {

	    @Autowired
	    private StudentRepository repo;

	    @Autowired
	    private UserService userService;

	    // Register Student
	    public Student saveStudent(Student student) 
	    {
	        student.setRole(Role.STUDENT);
	        return (Student) userService.saveUser(student);
	    }

	    // Get Student By Id
	  
	    	public Student findStudentById(int id)
	    	{
	    	    return repo.findById(id)
	    	            .orElseThrow(() -> new StudentNotFound());
	    	}	    

	    // Get All Students
	    public List<Student> findAllStudent() {
	        return repo.findAll();
	    }

	    // Update Student
	    public Student updateStudent(int id, Student student) {

	        Student dbStudent = findStudentById(id);

	        dbStudent.setName(student.getName());
	        dbStudent.setMobile(student.getMobile());
	        dbStudent.setEmail(student.getEmail());
	        dbStudent.setCollegename(student.getCollegename());
	        dbStudent.setDegree(student.getDegree());
	        dbStudent.setStream(student.getStream());
	        dbStudent.setYop(student.getYop());
	        dbStudent.setCgpa(student.getCgpa());

	        return repo.save(dbStudent);
	    }
	    // Delete Student
	    public String deleteStudent(int id) {

	        Student student = findStudentById(id);

	        repo.delete(student);

	        return "Student Deleted Successfully";
	    }

	}
	
