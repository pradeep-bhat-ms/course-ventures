package com.example.course_ventures.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {

    private int id;
    private StudentResponseDto student;
    private CourseResponseDto course;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

}
