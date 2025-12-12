package com.histack.ltplatform.service;

import java.util.List;

import com.histack.ltplatform.dto.ProgressResponseDto;
import com.histack.ltplatform.entity.LessonCompletions;
import com.histack.ltplatform.entity.Lessons;

public interface ProgressService {
	LessonCompletions completeLesson(Long lessonId, Long userId);
    ProgressResponseDto getCourseProgress(Long courseId, Long userId);
	List<Lessons> getLessonsByCourse(Long courseId);
}