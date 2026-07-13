package com.example.course_ventures.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_ventures.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
