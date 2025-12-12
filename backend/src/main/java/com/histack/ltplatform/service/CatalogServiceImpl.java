package com.histack.ltplatform.service;

import com.histack.ltplatform.dto.CourseDto;
import com.histack.ltplatform.dto.CourseResponseDto;
import com.histack.ltplatform.entity.Courses;
import com.histack.ltplatform.exceptions.LtPlatformException;
import com.histack.ltplatform.repository.CoursesRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImpl implements CatalogService {
    
    private final CoursesRepository coursesRepository;
    
    public CatalogServiceImpl(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }
    @Override
    public CourseResponseDto getCourses(String category, String level, String search,
                                       int pageNumber, int elementsPerPage, String sortBy) {
        try {
            Sort sort = parseSort(sortBy);
            Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, sort);

            String trimmedCategory = category != null ? category.trim() : null;
            String trimmedLevel = level != null ? level.trim() : null;
            String trimmedSearch = search != null && !search.trim().isEmpty() ? search.trim() : "";

            Page<Courses> coursePage;
            if (trimmedCategory != null && trimmedLevel != null) {
                coursePage = coursesRepository
                        .findByCategoryAndLevelAndTitleContainingIgnoreCase(trimmedCategory, trimmedLevel, trimmedSearch, pageable);
            } else if (trimmedCategory != null) {
                coursePage = coursesRepository
                        .findByCategoryAndTitleContainingIgnoreCase(trimmedCategory, trimmedSearch, pageable);
            } else if (trimmedLevel != null) {
                coursePage = coursesRepository
                        .findByLevelAndTitleContainingIgnoreCase(trimmedLevel, trimmedSearch, pageable);
            } else {
                coursePage = coursesRepository.findByTitleContainingIgnoreCase(trimmedSearch, pageable);
            }

            List<CourseDto> courseDtos = coursePage.getContent().stream()
                    .map(CourseDto::fromEntity)
                    .collect(Collectors.toList());

            return new CourseResponseDto(courseDtos, coursePage.getTotalElements());

        } catch (Exception e) {
            throw new LtPlatformException("Failed to fetch courses: " + e.getMessage());
        }
    }

    private Sort parseSort(String sortBy) {
        if (sortBy == null || sortBy.isEmpty()) {
            return Sort.by("title").ascending();
        }
        
        List<Sort.Order> orders = Arrays.stream(sortBy.split(","))
            .map(e -> parseSortColumn(e))
            .collect(Collectors.toList());
            
        return Sort.by(orders);
    }
    
    private Sort.Order parseSortColumn(String sortParam) {
        String[] parts = sortParam.split(":");
        if (parts.length != 2) {
            throw new LtPlatformException("Invalid sort format: " + sortParam + ". Use column:ASC or column:DESC");
        }
        
        String column = parts[0];
        String direction = parts[1].toUpperCase();
        
        Sort.Direction dir = Sort.Direction.fromString(direction);
        return new Sort.Order(dir, column);
    }
}
