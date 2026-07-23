package com.example.course_ventures.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsp.CourseHub.entity.Course;
import com.jsp.CourseHub.entity.Enrollement;
import com.jsp.CourseHub.entity.MockTest;
import com.jsp.CourseHub.entity.Student;
import com.jsp.CourseHub.entity.Trainer;
import com.jsp.CourseHub.entity.User;
import com.jsp.CourseHub.repository.EnrollementRepository;
import com.jsp.CourseHub.repository.UserRepository;
import com.jsp.CourseHub.service.CategoryService;
import com.jsp.CourseHub.service.CourseService;
import com.jsp.CourseHub.service.MockTestService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class WebViewController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CourseService courseService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MockTestService mockTestService;

	@Autowired
	private EnrollementRepository enrollmentRepository;

	private User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
			return null;
		}
		return userRepository.findByEmail(auth.getName());
	}

	@GetMapping("/login")
	public String loginPage(Model model, @RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			HttpServletRequest request) {
		if (getCurrentUser() != null) {
			return "redirect:/";
		}
		if (error != null) {
			String errorMessage = "Invalid email or password / Account not verified.";
			HttpSession session = request.getSession(false);
			if (session != null) {
				Object exception = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
				if (exception instanceof org.springframework.security.core.AuthenticationException) {
					errorMessage = ((org.springframework.security.core.AuthenticationException) exception).getMessage();
				}
			}
			model.addAttribute("loginError", errorMessage);
		}
		if (logout != null) {
			model.addAttribute("logoutMessage", "You have been logged out successfully.");
		}
		return "login";
	}

	@GetMapping("/forgot-password")
	public String forgotPasswordPage() {
		if (getCurrentUser() != null) {
			return "redirect:/";
		}
		return "forgot-password";
	}

	@GetMapping("/register")
	public String registerPage() {
		if (getCurrentUser() != null) {
			return "redirect:/";
		}
		return "register";
	}

	@GetMapping("/verify-otp")
	public String verifyOtpPage(Model model, @RequestParam(value = "userId", required = false) Integer userId) {
		model.addAttribute("userId", userId);
		return "verify-otp";
	}

	@GetMapping({"/", "/home"})
	public String homePage(Model model) {
		User user = getCurrentUser();
		List<Course> courses = courseService.getAllCourses();

		model.addAttribute("user", user);
		model.addAttribute("courses", courses);

		if (user != null) {
			model.addAttribute("role", user.getRole().name());
			if (user instanceof Student) {
				Student student = (Student) user;
				List<Enrollement> enrollments = student.getEnrollments();
				List<Integer> enrolledCourseIds = enrollments != null ? 
						enrollments.stream()
							.filter(e -> "ENROLLED".equalsIgnoreCase(e.getStatus()))
							.map(e -> e.getCourse().getId())
							.collect(Collectors.toList()) : List.of();
				model.addAttribute("enrolledCourseIds", enrolledCourseIds);
			}
		}
		return "index";
	}

	@GetMapping("/courses/{id}")
	public String courseDetailsPage(@PathVariable int id, Model model) {
		User user = getCurrentUser();
		Course course = courseService.getCourseById(id);
		List<MockTest> mockTests = mockTestService.getMockTestsByCourseId(id);

		boolean isPurchased = false;
		if (user != null) {
			if (user instanceof Student) {
				Optional<Enrollement> enrollment = enrollmentRepository.findByStudentIdAndCourseId(user.getId(), id);
				isPurchased = enrollment.isPresent() && "ENROLLED".equalsIgnoreCase(enrollment.get().getStatus());
			} else if (user instanceof Trainer || "ADMIN".equals(user.getRole().name())) {
				isPurchased = true; // trainers and admins can preview
			}
		}

		model.addAttribute("user", user);
		model.addAttribute("course", course);
		model.addAttribute("mockTests", mockTests);
		model.addAttribute("isPurchased", isPurchased);
		return "course-details";
	}

	@GetMapping("/mock-test/{id}/take")
	public String takeMockPage(@PathVariable int id, Model model) {
		User user = getCurrentUser();
		if (user == null) {
			return "redirect:/login";
		}
		MockTest mockTest = mockTestService.getMockTestById(id);

		// Double check enrollment
		boolean isPurchased = false;
		if (user instanceof Student) {
			Optional<Enrollement> enrollment = enrollmentRepository.findByStudentIdAndCourseId(
					user.getId(), mockTest.getCourse().getId());
			isPurchased = enrollment.isPresent() && "ENROLLED".equalsIgnoreCase(enrollment.get().getStatus());
		} else {
			isPurchased = true; // trainers/admins can take mock tests
		}

		if (!isPurchased) {
			return "redirect:/courses/" + mockTest.getCourse().getId() + "?error=unpurchased";
		}

		model.addAttribute("user", user);
		model.addAttribute("mockTest", mockTest);
		return "take-mock";
	}

	@GetMapping("/trainer/dashboard")
	public String trainerDashboard(Model model) {
		User user = getCurrentUser();
		if (user == null || (!(user instanceof Trainer) && !"ADMIN".equals(user.getRole().name()))) {
			return "redirect:/";
		}

		List<Course> courses = courseService.getAllCourses();
		if (user instanceof Trainer) {
			Trainer trainer = (Trainer) user;
			courses = courses.stream()
					.filter(c -> c.getTrainer() != null && c.getTrainer().getId() == trainer.getId())
					.collect(Collectors.toList());
		}

		List<com.jsp.CourseHub.entity.Category> categories = categoryService.getAllCategories();

		model.addAttribute("user", user);
		model.addAttribute("courses", courses);
		model.addAttribute("categories", categories);
		return "trainer-dashboard";
	}

	@GetMapping("/student/dashboard")
	public String studentDashboard(Model model) {
		User user = getCurrentUser();
		if (user == null || !(user instanceof Student)) {
			return "redirect:/";
		}

		model.addAttribute("user", user);
		return "student-dashboard";
	}
}


}
