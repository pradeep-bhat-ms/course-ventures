package com.example.course_ventures.payment.entity;

import java.time.LocalDateTime;

import com.example.course_ventures.entity.Course;
import com.example.course_ventures.entity.Student;
import com.example.course_ventures.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Payment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime paymentTime = LocalDateTime.now();

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;


}
