package com.example.course_ventures.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course_ventures.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	Payment findByRazorpayOrderId(String razorpayOrderId);
}
