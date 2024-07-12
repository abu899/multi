package com.study.multidb.domains.student.dto;

import com.study.multidb.domains.student.StudentEntity;

public record Student(Long id, String name) {
	public static Student of(StudentEntity studentEntity) {
		return new Student(studentEntity.getId(), studentEntity.getName());
	}
}
