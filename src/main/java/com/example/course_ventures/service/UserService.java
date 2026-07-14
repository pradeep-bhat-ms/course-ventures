package com.example.course_ventures.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.course_ventures.entity.User;
import com.example.course_ventures.repository.UserRepository;
import com.example.course_ventures.util.OtpUtil;

@Service
public class UserService {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	OtpUtil util;
	
	@Autowired
	EmailSender sender;
	
	public User saveUser(User u)
	{
		String otp=util.getOtp();
		u.setOtp(otp);
		u.setPassword(encoder.encode(u.getPassword()));
		u.setVerified(false);
		User save=repo.save(u);
		sender.sendMail(u.getEmail(),otp );
		return save;	
	}
}
