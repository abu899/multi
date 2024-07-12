package com.study.multidb.domains.student;

import com.study.multidb.domains.student.dto.RegisterStudent;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "student")
@Getter
@AllArgsConstructor
@Builder
public class StudentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int age;

	public static StudentEntity create(RegisterStudent student) {
		return StudentEntity.builder()
			.name(student.name())
			.age(student.age())
			.build();
	}
}
