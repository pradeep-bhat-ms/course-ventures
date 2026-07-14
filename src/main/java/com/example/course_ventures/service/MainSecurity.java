package com.example.course_ventures.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.course_ventures.entity.User;
import com.example.course_ventures.repository.UserRepository;

public class MainSecurity implements UserDetailsService {
	
	@Autowired
	UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u=repo.findByemail(username);
		if(u==null)
		{
			throw new UsernameNotFoundException("User not Found");
		}
		return org.springframework.security.core.userdetails.User
				.builder()
				.username(u.getEmail())
				.password(u.getPassword())
				.roles(u.getRole().name())
				.build();
	}
	


}
