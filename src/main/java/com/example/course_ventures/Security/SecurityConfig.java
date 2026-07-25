package com.example.course_ventures.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable()) // Disable CSRF for simple REST & Form interactions
			.authorizeHttpRequests(auth -> auth
				// Static assets
				.requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
				
				// Publicly accessible view endpoints
				.requestMatchers("/", "/home", "/login", "/register", "/verify-otp", "/forgot-password").permitAll()
				
				// Public API endpoints
				.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
				.requestMatchers("/user/save", "/user/verify", "/user/resend-otp", "/user/forgot-password/request", "/user/forgot-password/reset").permitAll()
				.requestMatchers("/student/save").permitAll()
				.requestMatchers("/trainer/save").permitAll()

				// Admin role restrictions
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/user/fetch", "/student/fetch", "/trainer/fetch").hasRole("ADMIN")

				// Trainer role restrictions
				.requestMatchers("/category/**", "/course/save", "/course/update/**", "/course/delete/**", "/mock-test/save", "/mock-test/*/add-question").hasAnyRole("TRAINER", "ADMIN")
				// Students (and previewing trainers/admins) need read access to module & lesson
				// content to view "Course Content" on a purchased course; only writes (create/
				// update/delete) are restricted to trainers.
				.requestMatchers(HttpMethod.GET, "/module/**", "/lesson/**").authenticated()
				.requestMatchers("/module/**", "/lesson/**").hasAnyRole("TRAINER", "ADMIN")
				.requestMatchers("/trainer/dashboard").hasAnyRole("TRAINER", "ADMIN")

				// Student role restrictions (REST & views).
				// NOTE: these narrower /mock-test/... and /course/... student rules must be
				// declared BEFORE the broad "/course/**"/"/mock-test/**" trainer-only rule
				// further below, since Spring Security uses first-match-wins.
				.requestMatchers("/payment/create-order/**", "/payment/verify").hasRole("STUDENT")
				.requestMatchers("/mock-attempt/submit", "/mock-attempt/student/**").hasRole("STUDENT")
				.requestMatchers("/mock-test/*/take").hasAnyRole("STUDENT", "TRAINER", "ADMIN")
				.requestMatchers("/enrollement/**").hasRole("STUDENT")
				// Reading progress (e.g. so a trainer/admin previewing a course, or the
				// student themself, can see completion state) is open to any authenticated
				// user; only submitting/updating progress is restricted to students.
				.requestMatchers(HttpMethod.GET, "/course-progress/**", "/module-progress/**").authenticated()
				.requestMatchers("/course-progress/**", "/module-progress/**").hasRole("STUDENT")
				.requestMatchers("/certificate/**").hasRole("STUDENT")
				.requestMatchers(HttpMethod.GET, "/review/**").authenticated()
				.requestMatchers("/review/**").hasRole("STUDENT")
				.requestMatchers("/student/dashboard").hasRole("STUDENT")

				// Catch-all: any other /course/** or /mock-test/** endpoint (e.g. update,
				// delete, add-question) is a trainer-management action. Declared last among
				// role rules so it never shadows the narrower student-facing rules above.
				.requestMatchers("/course/**", "/mock-test/**").hasAnyRole("TRAINER", "ADMIN")

				// All other requests require authentication
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.successHandler(roleBasedSuccessHandler())
				.failureUrl("/login?error=true")
				.permitAll()
			)
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout=true")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll()
			);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationSuccessHandler roleBasedSuccessHandler() {
		return (request, response, authentication) -> {
			String targetUrl = getTargetUrl(authentication);
			response.sendRedirect(targetUrl);
		};
	}

	private String getTargetUrl(Authentication authentication) {
		boolean adminOrTrainer = authentication.getAuthorities().stream()
				.anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()) || "ROLE_TRAINER".equals(a.getAuthority()));
		return adminOrTrainer ? "/trainer/dashboard" : "/";
	}
}

