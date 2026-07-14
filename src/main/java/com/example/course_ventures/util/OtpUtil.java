package com.example.course_ventures.util;

import java.util.Random;

import org.springframework.stereotype.Component;
@Component
public class OtpUtil {
	public String getOtp()
	{
		Random r=new Random();
		int otp=100000+r.nextInt(900000);
		return String.valueOf(otp);
	}
}
