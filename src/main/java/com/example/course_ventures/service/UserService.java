package com.example.course_ventures.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.course_ventures.entity.User;
import com.example.course_ventures.repository.StudentRepository;
import com.example.course_ventures.repository.UserRepository;
import com.example.course_ventures.util.OtpUtil;

@Service
public class UserService {
	
	@Autowired
	StudentRepository srepo;
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	OtpUtil util;
	
	@Autowired
	EmailSender sender;
	
	// Register User
	public User saveUser(User u) {

	    String otp = util.getOtp();

	    u.setOtp(otp);
	    u.setPassword(encoder.encode(u.getPassword()));
	    u.setVerified(false);
	    User save = repo.save(u);

	    System.out.println("================================");
	    System.out.println("Generated OTP : " + otp);
	    System.out.println("Email : " + u.getEmail());
	    System.out.println("================================");

	    try {
	        sender.sendMail(u.getEmail(), otp);
	        System.out.println("OTP sent successfully to " + u.getEmail());
	    } catch (Exception e) {
	        System.out.println("Failed to send OTP");
	        e.printStackTrace();
	    }

	    return save;
	}
	
    // verify otp
    public String verifyOtp(int id, String otp) {
    	User u=repo.findById(id).orElseThrow(()-> new UsernameNotFoundException("inavlid user")) ;
    	if(u.getOtp()!=null && u.getOtp().equals(otp))
    	{
    		   u.setVerified(true);
           u.setOtp(null);
           repo.save(u);
           return "OTP Verified Successfully";
    	}
           return "Invalid Otp";
    }
    
    // resend otp
    public String resendOtp(int id) {
    	User u=repo.findById(id).orElseThrow(()-> new UsernameNotFoundException("inavlid ")) ;
    	String otp=util.getOtp();
		u.setOtp(otp);
		repo.save(u);
		sender.sendMail(u.getEmail(),otp );
		return "Otp resend Succesfully";
    }
    
  //Request Forgot Password  
    public String requestForgetPassword(String email) {

        User u = repo.findByemail(email);

        if (u == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        String otp = util.getOtp();

        u.setOtp(otp);

        repo.save(u);

        sender.sendMail(u.getEmail(), otp);

        return "Password reset OTP sent successfully";
    }
    
    //Reset Password
    public String resetPassword(String email, String otp, String newPassword) {
        User u = repo.findByemail(email);
        		
        		if(u==null) {
        			throw new  UsernameNotFoundException("Invalid User");
        		}
        if (u.getOtp() != null && u.getOtp().equals(otp)) {
            u.setPassword(encoder.encode(newPassword));
            u.setOtp(null);
            repo.save(u);
            return "Password reset successfully";
        }
        return "Invalid OTP";
    }
    
    //  for find all records
    public List<User> findAllUsers() {
        return repo.findAll();
    }
 
}


