package com.example.course_ventures.dto;

import java.time.LocalDateTime;

import com.jsp.CourseHub.dto.StudentResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgressResponseDto {
	 private int id;
	    private StudentResponseDto student;
	    private CourseResponseDto course;
	    private int progressPercentage;
	    private LocalDateTime lastUpdated;
	    private boolean completed;
	    private LocalDateTime completionDate;

}
