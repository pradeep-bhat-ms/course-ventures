package com.example.course_ventures.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.CourseHub.dto.CertificateRequestDto;
import com.jsp.CourseHub.dto.CertificateResponseDto;
import com.jsp.CourseHub.entity.Certificate;
import com.jsp.CourseHub.repository.CertificateRepository;
import com.jsp.CourseHub.service.CertificateService;

@RestController
@RequestMapping("/certificate")
public class CertificateController {


	@Autowired
	CertificateService certificateService;

	@Autowired
	CertificateRepository certificateRepository;

	@PostMapping("/generate")
	public CertificateResponseDto generateCertificate(@RequestBody CertificateRequestDto requestDto) throws Exception {
		return certificateService.generateCertificate(requestDto);
	}

	@GetMapping("/verify")
	public CertificateResponseDto verifyCertificate(@RequestParam String certificateNumber) {
		return certificateService.getCertificateByNumber(certificateNumber);
	}

	@GetMapping("/student")
	public List<Certificate> getStudentCertificates(@RequestParam int studentId) {
		return certificateRepository.findByStudentId(studentId);
	}

	@GetMapping("/download/{certificateNumber}")
	public ResponseEntity<byte[]> downloadCertificate(@PathVariable String certificateNumber) {
		Certificate certificate = certificateService.getCertificateEntityByNumber(certificateNumber);
		byte[] pdfBytes = certificateService.renderCertificatePdf(certificate);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(
				org.springframework.http.ContentDisposition.attachment()
						.filename(certificateNumber + ".pdf")
						.build());

		return ResponseEntity.ok().headers(headers).body(pdfBytes);
	}
}
