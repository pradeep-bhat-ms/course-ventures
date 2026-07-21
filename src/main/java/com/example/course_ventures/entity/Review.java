package com.example.course_ventures.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating should be at least 1")
    @Max(value = 5, message = "Rating should not be greater than 5")
    private int rating;

  @NotBlank(message = "Commment should not be empty")
    private String comment;

    @ManyToOne
    private Course course;
    
    @ManyToOne
    private Student student;
	private LocalDateTime createdAt = LocalDateTime.now();

}
