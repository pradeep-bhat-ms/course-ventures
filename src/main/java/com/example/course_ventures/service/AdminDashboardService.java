package com.example.course_ventures.service;


	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

import com.example.course_ventures.entity.Course;
import com.example.course_ventures.entity.Enrollement;
import com.example.course_ventures.entity.Student;
import com.example.course_ventures.entity.Trainer;
import com.example.course_ventures.entity.User;
import com.example.course_ventures.repository.CourseProgressRepository;
import com.example.course_ventures.repository.EnrollementRepository;
import com.example.course_ventures.repository.StudentRepository;
import com.example.course_ventures.repository.TrainerRepository;
import com.example.course_ventures.repository.UserRepository;

	@Service
	public class AdminDashboardService {

		@Autowired
		UserRepository userRepository;

		@Autowired
		StudentRepository studentRepository;

		@Autowired
		TrainerRepository trainerRepository;

		@Autowired
		EnrollementRepository enrollementRepository;

		@Autowired
		CourseProgressRepository courseProgressRepository;

		@Autowired
		CourseService courseService;

		public Map<String, Object> getDashboardStatistics() {
			Map<String, Object> statistics = new HashMap<>();

			long totalStudents = studentRepository.count();
			long totalTrainers = trainerRepository.count();
			long totalCourses = courseService.getAllCourses().size();
			long totalEnrollments = enrollementRepository.count();
			long completedCourses = courseProgressRepository.findByCompletedTrue().size();

			statistics.put("totalStudents", totalStudents);
			statistics.put("totalTrainers", totalTrainers);
			statistics.put("totalCourses", totalCourses);
			statistics.put("totalEnrollments", totalEnrollments);
			statistics.put("completedCourses", completedCourses);

			return statistics;
		}

		public List<Student> getAllStudents() {
			return studentRepository.findAll();
		}

		public List<Trainer> getAllTrainers() {
			return trainerRepository.findAll();
		}

		public List<Course> getAllCourses() {
			return (List<Course>) courseService.getAllCourses();
		}

		public List<Enrollement> getAllEnrollments() {
			return enrollementRepository.findAll();
		}

		public void approveUser(int userId) {
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
			user.setApproved(true);
			userRepository.save(user);
		}
	

}
