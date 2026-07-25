package com.example.course_ventures.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.course_ventures.entity.Category;
import com.example.course_ventures.entity.User;
import com.example.course_ventures.enums.Role;
import com.example.course_ventures.repository.CategoryRepository;
import com.example.course_ventures.repository.UserRepository;


@Component
public class AdminInitializer {

	
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final PasswordEncoder passwordEncoder;

	// Set app.seed-admin=false / app.seed-default-categories=false in
	// application.properties if you want a fresh database to start
	// completely empty (no auto-created admin, no default categories).
	@Value("${app.seed-admin:true}")
	private boolean seedAdmin;

	@Value("${app.seed-default-categories:true}")
	private boolean seedDefaultCategories;

	public AdminInitializer(UserRepository userRepository, CategoryRepository categoryRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void run(String... args) {
		if (seedAdmin && userRepository.findByemail("admin@coursehub.com") == null) {
			User admin = new User();
			admin.setName("Admin");
			admin.setEmail("admin@coursehub.com");
			admin.setPassword(passwordEncoder.encode("Admin@123"));
			admin.setRole(Role.ADMIN);
			admin.setVerified(true);
			admin.setApproved(true);

			userRepository.save(admin);
		}
		if (seedDefaultCategories && categoryRepository.count() == 0) {
			saveCategory("Java Development", "Core Java, Spring Boot, and backend development courses.");
			saveCategory("Web Development", "HTML, CSS, JavaScript, and full stack web courses.");
			saveCategory("Database", "SQL, PostgreSQL, MySQL, and database design courses.");
			saveCategory("Cloud Computing", "Cloud, deployment, and DevOps learning tracks.");
		}
	}

	private void saveCategory(String categoryName, String description) {
		Category category = new Category();
		category.setCategoryName(categoryName);
		category.setCategoryDescription(description);
		categoryRepository.save(category);
	}
}
