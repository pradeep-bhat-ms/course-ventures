package com.example.course_ventures.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateResponseDto {

	private int id;
	private StudentResponseDto student;
	private CourseResponseDto course;
	private String certificateNumber;
	private LocalDateTime issueDate;
	private String certificateUrl;

}