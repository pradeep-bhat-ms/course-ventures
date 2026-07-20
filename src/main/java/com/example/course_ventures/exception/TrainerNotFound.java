package com.example.course_ventures.exception;

public class TrainerNotFound extends RuntimeException {

    public TrainerNotFound() {
        super("Trainer Not Found");
    }

    public TrainerNotFound(String message) {
        super(message);
    }
}