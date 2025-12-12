package com.histack.ltplatform.service;

import com.histack.ltplatform.dto.CourseResponseDto;

public interface CatalogService {
	CourseResponseDto getCourses(String category, String level, String search,
            int pageNumber, int elementsPerPage, String sortBy);
	}
