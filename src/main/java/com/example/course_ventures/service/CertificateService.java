package com.example.course_ventures.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_ventures.dto.CertificateRequestDto;
import com.example.course_ventures.dto.CertificateResponseDto;
import com.example.course_ventures.dto.CourseProgressResponseDto;
import com.example.course_ventures.entity.Certificate;
import com.example.course_ventures.entity.Course;
import com.example.course_ventures.entity.MockTest;
import com.example.course_ventures.entity.MocktestAttempt;
import com.example.course_ventures.entity.Student;
import com.example.course_ventures.repository.CertificateRepository;
import com.example.course_ventures.repository.MockTestAttemptRepository;

import com.example.course_ventures.dto.StudentResponseDto;
import com.example.course_ventures.dto.CourseResponseDto;

@Service
public class CertificateService {

	@Autowired
	CertificateRepository certificateRepository;

	@Autowired
	StudentService studentService;

	@Autowired
	CourseService courseService;

	@Autowired
	CourseProgressService courseProgressService;

	@Autowired
	MockTestService mockTestService;

	@Autowired
	MockTestAttemptRepository mockTestAttemptRepository;

	public CertificateResponseDto generateCertificate(CertificateRequestDto requestDto) {
		Student student = studentService.findStudentById(requestDto.getStudentId());
		Course course = courseService.findCourseById(requestDto.getCourseId());

		CourseProgressResponseDto progress = courseProgressService.getProgress(requestDto.getStudentId(), requestDto.getCourseId());

		if (!progress.isCompleted()) {
			throw new RuntimeException("Course not completed yet. Cannot generate certificate.");
		}

		// Verify student has attended all mock tests related to the course
		List<MockTest> mockTests = mockTestService.getMockTestsByCourseId(requestDto.getCourseId());
		if (mockTests != null && !mockTests.isEmpty()) {
			List<MocktestAttempt> attempts = mockTestAttemptRepository.findByStudentId(requestDto.getStudentId());
			for (MockTest mockTest : mockTests) {
				boolean attempted = attempts.stream()
						.anyMatch(a -> a.getMockTest().getId() == mockTest.getId());
				if (!attempted) {
					throw new RuntimeException("You must attend all mock tests related to this course before generating the certificate.");
				}
			}
		}

		Optional<Certificate> existingCertificate = certificateRepository
				.findByStudentIdAndCourseId(requestDto.getStudentId(), requestDto.getCourseId());
		if (existingCertificate.isPresent()) {
			return convertToResponseDto(existingCertificate.get());
		}

		String certificateNumber = "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

		Certificate certificate = new Certificate();
		certificate.setStudent(student);
		certificate.setCourse(course);
		certificate.setCertificateNumber(certificateNumber);
		certificate.setIssueDate(LocalDateTime.now());
		certificate.setCertificateUrl("/certificate/download/" + certificateNumber);

		Certificate savedCertificate = certificateRepository.save(certificate);

		return convertToResponseDto(savedCertificate);
	}

	public Certificate getCertificateEntityByNumber1(String certificateNumber) {
	    return certificateRepository.findByCertificateNumber(certificateNumber)
	            .orElseThrow(() -> new RuntimeException("Certificate not found"));
	}

	public Certificate getCertificateEntityByNumber(String certificateNumber) {
		return certificateRepository.findByCertificateNumber(certificateNumber)
				.orElseThrow(() -> new RuntimeException("Certificate not found"));
	}

	/**
	 * Renders the certificate as a PDF on the fly using PDFBox. Generating on
	 * demand (rather than writing a file to disk at issue time) avoids relying
	 * on a persistent/writable filesystem, which classpath static resources and
	 * most deployment targets don't reliably offer.
	 */
	public byte[] renderCertificatePdf(Certificate certificate) {
		try (PDDocument document = new PDDocument()) {
			PDPage page = new PDPage(PDRectangle.A4.getWidth() > PDRectangle.A4.getHeight()
					? PDRectangle.A4
					: new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
			document.addPage(page);

			float pageWidth = page.getMediaBox().getWidth();
			float pageHeight = page.getMediaBox().getHeight();

			String studentName = certificate.getStudent() != null ? certificate.getStudent().getName() : "Student";
			String courseTitle = certificate.getCourse() != null ? certificate.getCourse().getTitle() : "Course";
			String trainerName = (certificate.getCourse() != null && certificate.getCourse().getTrainer() != null)
					? certificate.getCourse().getTrainer().getName()
					: "CourseHub";

			try (PDPageContentStream cs = new PDPageContentStream(document, page)) {
				// Border
				cs.setLineWidth(3f);
				cs.addRect(30, 30, pageWidth - 60, pageHeight - 60);
				cs.stroke();
				cs.setLineWidth(1f);
				cs.addRect(45, 45, pageWidth - 90, pageHeight - 90);
				cs.stroke();

				drawCentered(cs, document, "CERTIFICATE OF COMPLETION", pageHeight - 130, 30, true, pageWidth);
				drawCentered(cs, document, "CourseHub Platform", pageHeight - 165, 14, false, pageWidth);

				drawCentered(cs, document, "This is to certify that", pageHeight - 230, 14, false, pageWidth);
				drawCentered(cs, document, studentName, pageHeight - 270, 26, true, pageWidth);
				drawCentered(cs, document, "has successfully completed the course", pageHeight - 310, 14, false, pageWidth);
				drawCentered(cs, document, courseTitle, pageHeight - 350, 20, true, pageWidth);

				drawCentered(cs, document, "Trainer: " + trainerName, pageHeight - 410, 12, false, pageWidth);
				drawCentered(cs, document, "Issue Date: " + certificate.getIssueDate(), pageHeight - 430, 12, false, pageWidth);
				drawCentered(cs, document, "Certificate No: " + certificate.getCertificateNumber(), pageHeight - 450, 12, false, pageWidth);
			}

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			document.save(out);
			return out.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("Failed to generate certificate PDF: " + e.getMessage(), e);
		}
	}

	private void drawCentered(PDPageContentStream cs, PDDocument document, String text, float y, float fontSize, boolean bold, float pageWidth) throws java.io.IOException {
		PDFont font = bold ? PDType1Font.HELVETICA_BOLD : PDType1Font.HELVETICA;
		float textWidth = font.getStringWidth(text) / 1000 * fontSize;
		float x = (pageWidth - textWidth) / 2;
		cs.beginText();
		cs.setFont(font, fontSize);
		cs.newLineAtOffset(x, y);
		cs.showText(text);
		cs.endText();
	}

	private CertificateResponseDto convertToResponseDto(Certificate certificate) {
		CertificateResponseDto responseDto = new CertificateResponseDto();
		responseDto.setId(certificate.getId());
		responseDto.setCertificateNumber(certificate.getCertificateNumber());
		responseDto.setIssueDate(certificate.getIssueDate());
		responseDto.setCertificateUrl(certificate.getCertificateUrl());
		responseDto.setStudent(toStudentDto(certificate.getStudent()));
		responseDto.setCourse(toCourseDto(certificate.getCourse()));
		return responseDto;
	}

	private StudentResponseDto toStudentDto(Student student) {
		if (student == null) {
			return null;
		}
		StudentResponseDto dto = new StudentResponseDto();
		dto.setId(student.getId());
		dto.setName(student.getName());
		dto.setEmail(student.getEmail());
		dto.setName(student.getCollegename());
		dto.setQualification(student.getQualification());
		dto.setVerified(student.isVerified());
		return dto;
	}

	private CourseResponseDto toCourseDto(Course course) {
		if (course == null) {
			return null;
		}
		CourseResponseDto dto = new CourseResponseDto();
		dto.setId(course.getId());
		dto.setTitle(course.getTitle());
		dto.setDiscription(course.getDescription());
		dto.setPrice(course.getPrice());
		dto.setDuration(course.getDuration());
		dto.setCreatedDate(course.getCreatedDate());
		return dto;
	}
}
