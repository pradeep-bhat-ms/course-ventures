package com.example.course_ventures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.course_ventures.entity.Module;

public interface ModuleRepository extends JpaRepository<Module, Integer> {

}