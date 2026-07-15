package com.example.course_ventures.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerifyOtpRequest {
    private String email;
    private String otp;

}
