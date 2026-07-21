package com.example.course_ventures.dto;

import java.time.LocalDateTime;

import com.example.course_ventures.entity.Course;
import com.example.course_ventures.entity.Student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateResponseDto {
	private int id;
	    private Student student;
	    private Course course;
	    private String certificateDescription;
	    private String certificateName;
	    private LocalDateTime issueDate;
	    private String certificateUrl;

}
