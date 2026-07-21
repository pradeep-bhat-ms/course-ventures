package com.example.course_ventures.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CertificateRequestDto {
	
	@NotNull(message = "Student ID is required")
    private int studentId;
	
	@NotNull(message = "Course ID is required")
    private int courseId;
	
}
