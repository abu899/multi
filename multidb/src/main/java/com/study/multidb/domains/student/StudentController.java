package com.study.multidb.domains.student;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.multidb.domains.student.dto.RegisterStudent;
import com.study.multidb.domains.student.dto.Student;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
	private final StudentRepository studentRepository;

	@GetMapping
	public ResponseEntity<Student> getStudent(Long id) {
		StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("학생이 없습니다."));
		return ResponseEntity.ok(Student.of(studentEntity));
	}

	@PostMapping
	public ResponseEntity<Student> register(@RequestBody RegisterStudent student) {

		StudentEntity studentEntity = StudentEntity.create(student);
		StudentEntity savedStudent = studentRepository.save(studentEntity);

		return ResponseEntity.ok(Student.of(savedStudent));
	}
}
