package com.study.multidb.domains.school.dto;

import com.study.multidb.domains.school.SchoolEntity;

public record School(Long id, String name){
	public static School of(SchoolEntity savedSchool) {
		return new School(savedSchool.getId(), savedSchool.getName());
	}
}
