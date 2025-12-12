package com.histack.ltplatform.controller;

import com.histack.ltplatform.dto.ProgressResponseDto;
import com.histack.ltplatform.entity.LessonCompletions;
import com.histack.ltplatform.entity.Lessons;
import com.histack.ltplatform.exceptions.LtPlatformException;
import com.histack.ltplatform.service.ProgressService;
import com.histack.ltplatform.util.ApiResponse;
import com.histack.ltplatform.util.ResponseUtil;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ResponseUtil responseUtil;
    private final ProgressService progressService;

    private static final Long DEMO_USER_ID = 1L;

    public ProgressController(ResponseUtil responseUtil, ProgressService progressService) {
        this.responseUtil = responseUtil;
        this.progressService = progressService;
    }

    @PostMapping("/lessons/{lessonId}/complete")
    public ResponseEntity<ApiResponse<LessonCompletions>> completeLesson(@PathVariable Long lessonId) {
        try {
            // Input validation
            if (lessonId == null || lessonId <= 0) {
                return responseUtil.setErrorResponse("lessonId must be a positive number", HttpStatus.BAD_REQUEST);
            }

            LessonCompletions lesson = progressService.completeLesson(lessonId, DEMO_USER_ID);
            return responseUtil.setSuccessResponse(lesson, "Lesson marked as complete successfully");

        } catch (LtPlatformException e) {
            return responseUtil.setErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return responseUtil.setErrorResponse("Something went wrong. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/courses/{courseId}/progress")
    public ResponseEntity<ApiResponse<ProgressResponseDto>> getCourseProgress(@PathVariable Long courseId) {
        try {
            // Input validation
            if (courseId == null || courseId <= 0) {
                return responseUtil.setErrorResponse("courseId must be a positive number", HttpStatus.BAD_REQUEST);
            }

            ProgressResponseDto progress = progressService.getCourseProgress(courseId, DEMO_USER_ID);
            return responseUtil.setSuccessResponse(progress, "Course progress fetched successfully");

        } catch (LtPlatformException e) {
            return responseUtil.setErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return responseUtil.setErrorResponse("Something went wrong. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("/courses/lesson/{courseId}")
    public ResponseEntity<ApiResponse<List<Lessons>>> getLessonsByCourse(@PathVariable Long courseId) {
        try {
            if (courseId == null || courseId <= 0) {
                return responseUtil.setErrorResponse("courseId must be a positive number", HttpStatus.BAD_REQUEST);
            }

            List<Lessons> lessons = progressService.getLessonsByCourse(courseId);
            return responseUtil.setSuccessResponse(lessons, "Lessons fetched successfully");

        } catch (LtPlatformException e) {
            return responseUtil.setErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return responseUtil.setErrorResponse("Something went wrong. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
