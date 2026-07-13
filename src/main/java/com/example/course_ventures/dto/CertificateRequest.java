package com.example.course_ventures.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CertificateRequest {
	
    private int studentId;
    private int courseId;
	
}
