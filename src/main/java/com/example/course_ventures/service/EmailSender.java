package com.example.course_ventures.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String toEmail, String otp) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom("pradeephsgr@gmail.com");
            mailMessage.setTo(toEmail);
            mailMessage.setSubject("CourseHub Email Verification OTP");
            mailMessage.setText("Your OTP is : " + otp);

            javaMailSender.send(mailMessage);

            System.out.println("==================================");
            System.out.println("EMAIL SENT SUCCESSFULLY");
            System.out.println("To : " + toEmail);
            System.out.println("OTP : " + otp);
            System.out.println("==================================");

        } catch (Exception e) {

            System.out.println("==================================");
            System.out.println("EMAIL SENDING FAILED");
            e.printStackTrace();
            System.out.println("==================================");
        }
    }
}