package com.example.course_ventures.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgressRequestDto {

    @NotNull(message = "Student ID is required")
    private int studentId;
    
    @NotNull(message = "Course ID is required")
    private int courseId;
    
    @Min(value = 0, message = "Progress cannot be negative")
    @Max(value = 100, message = "Progress cannot exceed 100")
    private int progressPercentage;

}
