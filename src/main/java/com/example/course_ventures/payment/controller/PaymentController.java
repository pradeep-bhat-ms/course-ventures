package com.example.course_ventures.payment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.course_ventures.entity.Student;
import com.example.course_ventures.entity.User;
import com.example.course_ventures.enums.Role;
import com.example.course_ventures.payment.service.PaymentService;
import com.example.course_ventures.repository.StudentRepository;
import com.example.course_ventures.repository.UserRepository;
import com.example.course_ventures.response.ResponseStructure;
import com.razorpay.Order;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Value("${razorpay.key}")
    private String razorpayKey;

    @PostMapping("/create-order/{courseId}")
    public ResponseEntity<ResponseStructure<Map<String, Object>>> createOrder(
            @PathVariable int courseId,
            Authentication authentication) throws Exception {

        Student student = getLoggedInStudent(authentication);

        Order order = paymentService.createOrder(student.getId(), courseId);

        Map<String, Object> data = new HashMap<>();
        data.put("orderId", order.get("id"));
        data.put("amount", order.get("amount"));
        data.put("currency", order.get("currency"));
        data.put("key", razorpayKey);

        ResponseStructure<Map<String, Object>> rs = new ResponseStructure<>();
        rs.setStatus(HttpStatus.CREATED.value());
        rs.setMessage("Order created successfully");
        rs.setData(data);

        return new ResponseEntity<>(rs, HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public ResponseEntity<ResponseStructure<String>> verifyPayment(
            @RequestParam String razorpayOrderId,
            @RequestParam String razorpayPaymentId,
            @RequestParam String razorpaySignature,
            Authentication authentication) {

        Student student = getLoggedInStudent(authentication);

        boolean verified = paymentService.verifyPayment(
                razorpayOrderId,
                razorpayPaymentId,
                razorpaySignature,
                student.getId());

        ResponseStructure<String> rs = new ResponseStructure<>();

        if (verified) {
            rs.setStatus(HttpStatus.OK.value());
            rs.setMessage("Payment verified successfully, student enrolled.");
            rs.setData("SUCCESS");
            return new ResponseEntity<>(rs, HttpStatus.OK);
        }

        rs.setStatus(HttpStatus.BAD_REQUEST.value());
        rs.setMessage("Payment verification failed.");
        rs.setData("FAILURE");

        return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
    }

    private Student getLoggedInStudent(Authentication authentication) {

        User user = userRepository.findByemail(authentication.getName());

        if (user == null || user.getRole() != Role.STUDENT) {
            throw new IllegalStateException("Only students can make payments.");
        }

        return studentRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalStateException("Student record not found."));
    }
}