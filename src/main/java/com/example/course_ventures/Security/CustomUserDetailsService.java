package com.example.course_ventures.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.course_ventures.enums.Role;
import com.example.course_ventures.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        com.example.course_ventures.entity.User user =
                userRepository.findByemail(email != null ? email.trim().toLowerCase() : null);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Email verification
        if (!user.isVerified()) {
            throw new DisabledException(
                    "User email verification is pending. Please verify using OTP first.");
        }

        // Admin approval
        if ((user.getRole() == Role.STUDENT || user.getRole() == Role.TRAINER)
                && !user.isApproved()) {

            throw new DisabledException("Your account is pending admin approval.");
        }

        if (user.getRole() == null) {
            throw new DisabledException("User role is not assigned.");
        }

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}