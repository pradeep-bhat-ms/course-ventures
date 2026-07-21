package com.example.course_ventures.repository;

import java.security.cert.Certificate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

	 Optional<Certificate> findByStudentIdAndCourseId(int studentId, int courseId);
	    
	    List<Certificate> findByStudentId(int studentId);
	    
	    Optional<Certificate> findByCertificateNumber(String certificateNumber);
	
}
