package com.example.course_ventures.payment.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.course_ventures.entity.Course;
import com.example.course_ventures.entity.Student;
import com.example.course_ventures.enums.PaymentStatus;
import com.example.course_ventures.payment.entity.Payment;
import com.example.course_ventures.payment.repository.PaymentRepository;
import com.example.course_ventures.repository.CourseRepository;
import com.example.course_ventures.repository.StudentRepository;
import com.example.course_ventures.service.EnrollementService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

public class PaymentService {

	@Autowired
	private RazorpayClient razorpayClient;

	@Value("${razorpay.secret}")
	private String secret;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private EnrollementService enrollmentService;


	public Order createOrder(int studentId,
	                         int courseId) throws Exception {

	    Student student =studentRepository.findById(studentId).get();

	    Course course =courseRepository.findById(courseId).get();

	    JSONObject orderRequest = new JSONObject();

	    orderRequest.put("amount",
	            Math.round(course.getPrice() * 100));

	    orderRequest.put("currency", "INR");

	    orderRequest.put("receipt",
	            "student_" + student.getId() + "_course_" + course.getId());

	    Order order = razorpayClient.orders.create(orderRequest);

	    Payment payment = new Payment();

	    payment.setAmount(course.getPrice());

	    payment.setRazorpayOrderId(
	            order.get("id"));

	    payment.setStatus(PaymentStatus.CREATED);

	    payment.setStudent(student);

	    payment.setCourse(course);

	    paymentRepository.save(payment);

	    return order;
	}

	public boolean verifyPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature, int studentId) {
		try {
			Payment payment = paymentRepository.findByRazorpayOrderId(razorpayOrderId);
			if (payment == null || payment.getStudent().getId() != studentId) {
				return false;
			}

			JSONObject options = new JSONObject();
			options.put("razorpay_order_id", razorpayOrderId);
			options.put("razorpay_payment_id", razorpayPaymentId);
			options.put("razorpay_signature", razorpaySignature);

			boolean isValid = Utils.verifyPaymentSignature(options, secret);
			if (isValid) {
				payment.setRazorpayPaymentId(razorpayPaymentId);
				payment.setStatus(PaymentStatus.SUCCESS);
				paymentRepository.save(payment);

				enrollmentService.enrollCourse(payment.getStudent().getId(), payment.getCourse().getId());
				return true;
			} else {
				payment.setStatus(PaymentStatus.FAILED);
				paymentRepository.save(payment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}