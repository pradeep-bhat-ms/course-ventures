package com.example.course_ventures.service;

	import java.util.List;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

import com.example.course_ventures.entity.Course;
import com.example.course_ventures.entity.Enrollement;
import com.example.course_ventures.entity.Student;
import com.example.course_ventures.repository.EnrollementRepository;

	@Service
	public class EnrollementService {

		@Autowired
	    EnrollementRepository  enrollmentRepository;

		@Autowired
		StudentService studentService;

		@Autowired
		CourseService courseService;

		public Enrollement enrollCourse(int studentId, int courseId) {

			Enrollement existingEnrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
					.orElse(null);
			if (existingEnrollment != null) {
				existingEnrollment.setStatus("ENROLLED");
				return enrollmentRepository.save(existingEnrollment);
			}

			Student student = studentService.findStudentById(studentId);

			Course course = courseService.findCourseById(courseId);

			Enrollement enrollment = new Enrollement();

			enrollment.setStudent(student);

			enrollment.setCourse(course);

			enrollment.setStatus("ENROLLED");

			return enrollmentRepository.save(enrollment);
		}

		public List<Enrollement> getAllEnrollments() {

			return enrollmentRepository.findAll();
		}


}
