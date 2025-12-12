package com.histack.ltplatform.controller;

import com.histack.ltplatform.dto.CourseResponseDto;
import com.histack.ltplatform.exceptions.LtPlatformException;
import com.histack.ltplatform.service.CatalogService;
import com.histack.ltplatform.util.ApiResponse;
import com.histack.ltplatform.util.ResponseUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/catalog")
public class CatalogController {
    
    private final ResponseUtil responseUtil;
    private final CatalogService catalogService;
    
    public CatalogController(ResponseUtil responseUtil, CatalogService catalogService) {
        this.responseUtil = responseUtil;
        this.catalogService = catalogService;
    }
    
    @GetMapping("/courses")
    public ResponseEntity<ApiResponse<CourseResponseDto>> getCourses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String search,          // Base64 encoded
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer elementsPerPage,
            @RequestParam(required = false) String sortBy) {

        try {
            // Basic input validation
            if (pageNumber == null || pageNumber < 0) {
                return responseUtil.setErrorResponse("pageNumber must be >= 0", HttpStatus.BAD_REQUEST);
            }

            if (elementsPerPage == null || elementsPerPage <= 0 || elementsPerPage > 100) {
                return responseUtil.setErrorResponse("elementsPerPage must be between 1 and 100", HttpStatus.BAD_REQUEST);
            }

            if (category != null && category.trim().length() > 50) {
                return responseUtil.setErrorResponse("category is too long", HttpStatus.BAD_REQUEST);
            }

            if (level != null && level.trim().length() > 50) {
                return responseUtil.setErrorResponse("level is too long", HttpStatus.BAD_REQUEST);
            }

            // Decode Base64 search (if present)
            String decodedSearch = null;
            if (search != null && !search.isBlank()) {
                try {
                    byte[] decodedBytes = Base64.getDecoder().decode(search);
                    decodedSearch = new String(decodedBytes, StandardCharsets.UTF_8).trim();
                } catch (IllegalArgumentException ex) {
                    return responseUtil.setErrorResponse("Invalid Base64 for search parameter", HttpStatus.BAD_REQUEST);
                }

                if (decodedSearch.length() > 100) {
                    return responseUtil.setErrorResponse("search text is too long", HttpStatus.BAD_REQUEST);
                }
            }


            CourseResponseDto  courses = catalogService.getCourses(
                    category != null ? category.trim() : null,
                    level != null ? level.trim() : null,
                    decodedSearch,
                    pageNumber,
                    elementsPerPage,
                    sortBy
            );

            return responseUtil.setSuccessResponse(courses, "Courses fetched successfully");

        } catch (LtPlatformException e) {
            return responseUtil.setErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return responseUtil.setErrorResponse(
                    "Something went wrong. Please try again later.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
