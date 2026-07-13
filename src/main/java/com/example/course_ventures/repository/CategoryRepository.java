package com.example.course_ventures.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_ventures.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
