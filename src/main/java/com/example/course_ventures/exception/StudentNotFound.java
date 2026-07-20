package com.example.course_ventures.exception;

public class StudentNotFound extends RuntimeException {

    public StudentNotFound() {
        super("Student Not Found");
    }

    public StudentNotFound(String message) {
        super(message);
    }
}