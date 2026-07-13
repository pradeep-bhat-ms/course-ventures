package com.example.course_ventures.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

	@Autowired
	JavaMailSender javaMailSender;
	
	public void sendMail(String toEmail,String otp)
	{
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		mailMessage.setFrom("pradeephsg@gmail.com");
		mailMessage.setTo(toEmail);
		mailMessage.setSubject("Course_ventures otp sent succesfully");
		mailMessage.setText(otp);
		javaMailSender.send(mailMessage);	
	}
}
