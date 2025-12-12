package com.histack.ltplatform.service;

import com.histack.ltplatform.dto.ProgressResponseDto;
import com.histack.ltplatform.entity.LessonCompletions;
import com.histack.ltplatform.entity.Lessons;
import com.histack.ltplatform.exceptions.LtPlatformException;
import com.histack.ltplatform.repository.LessonCompletionsRepository;
import com.histack.ltplatform.repository.LessonsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final LessonCompletionsRepository completionsRepository;
    private final LessonsRepository lessonsRepository;

    public ProgressServiceImpl(LessonCompletionsRepository completionsRepository, 
                               LessonsRepository lessonsRepository) {
        this.completionsRepository = completionsRepository;
        this.lessonsRepository = lessonsRepository;
    }

    @Override
    public LessonCompletions completeLesson(Long lessonId, Long userId) {
        // Validate lesson exists
        lessonsRepository.findById(lessonId)
                .orElseThrow(() -> new LtPlatformException("Lesson not found with ID: " + lessonId));

        // Check if already completed by this user
        if (completionsRepository.findByLessonIdAndUserId(lessonId, userId).isPresent()) {
            throw new LtPlatformException("Lesson already completed by this user");
        }

        // Create completion record (ID auto-generated)
        LessonCompletions completion = new LessonCompletions(lessonId, userId, LocalDateTime.now());
        return completionsRepository.save(completion);
    }

    @Override
    public ProgressResponseDto getCourseProgress(Long courseId, Long userId) {
        // Get all lessons for course
        List<Lessons> allLessons = lessonsRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
        
        if (allLessons.isEmpty()) {
            throw new LtPlatformException("No lessons found for course ID: " + courseId);
        }

        // Get completed lessons for this user in this course
        List<Long> completedLessonIds = completionsRepository
                .findCompletedLessonIdsByUserIdAndCourseId(userId, courseId);
        
        int totalLessons = allLessons.size();
        int completedLessons = completedLessonIds.size();
        double progressPercentage = totalLessons > 0 ? 
            (double) completedLessons / totalLessons * 100 : 0;

        // Extract course title from first lesson
        String courseTitle = allLessons.get(0).getTitle().split(" - ")[0];

        return new ProgressResponseDto(
            courseId,
            courseTitle,
            Math.round(progressPercentage * 100.0) / 100.0,  // 2 decimal places
            totalLessons,
            completedLessons,
            completedLessonIds
        );
    }

    @Override
    public List<Lessons> getLessonsByCourse(Long courseId) {
        try {
            List<Lessons> lessons = lessonsRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
            if (lessons == null || lessons.isEmpty()) {
                return new ArrayList<>();
            }
            return lessons;
        } catch (Exception e) {
            throw new LtPlatformException("Failed to fetch lessons for course: " + courseId);
        }
    }
}
