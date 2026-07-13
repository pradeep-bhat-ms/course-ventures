package com.example.course_ventures.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_ventures.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

}
