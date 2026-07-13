package com.example.course_ventures.dto;

import java.time.LocalDateTime;

import com.example.course_ventures.entity.Course;
import com.example.course_ventures.entity.Student;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CertificateResponse {
	    private Student student;
	    private Course course;
	    private String certificateDescription;
	    private String certificateName;
	    private LocalDateTime issueDate=LocalDateTime.now();

}
