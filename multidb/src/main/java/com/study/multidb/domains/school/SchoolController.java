package com.study.multidb.domains.school;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.multidb.domains.school.dto.RegisterSchool;
import com.study.multidb.domains.school.dto.School;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/school")
public class SchoolController {
	private final SchoolRepository schoolRepository;

	@GetMapping
	public ResponseEntity<School> getSchool(Long id) {
		SchoolEntity schoolEntity = schoolRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("학교가 없습니다."));
		return ResponseEntity.ok(School.of(schoolEntity));
	}

	@PostMapping
	public ResponseEntity<School> register(@RequestBody RegisterSchool school) {

		SchoolEntity schoolEntity = SchoolEntity.create(school);
		SchoolEntity savedSchool = schoolRepository.save(schoolEntity);

		return ResponseEntity.ok(School.of(savedSchool));
	}
}
