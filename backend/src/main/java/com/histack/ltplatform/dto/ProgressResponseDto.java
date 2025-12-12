package com.histack.ltplatform.dto;

import java.util.List;

public class ProgressResponseDto {
    private Long courseId;
    private String courseTitle;
    private double progressPercentage;
    private int totalLessons;
    private int completedLessons;
    private List<Long> completedLessonIds;

    public ProgressResponseDto() {}

    public ProgressResponseDto(Long courseId, String courseTitle, double progressPercentage,
                               int totalLessons, int completedLessons, List<Long> completedLessonIds) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.progressPercentage = progressPercentage;
        this.totalLessons = totalLessons;
        this.completedLessons = completedLessons;
        this.completedLessonIds = completedLessonIds;
    }

    // Getters & Setters
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    
    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }
    
    public double getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(double progressPercentage) { this.progressPercentage = progressPercentage; }
    
    public int getTotalLessons() { return totalLessons; }
    public void setTotalLessons(int totalLessons) { this.totalLessons = totalLessons; }
    
    public int getCompletedLessons() { return completedLessons; }
    public void setCompletedLessons(int completedLessons) { this.completedLessons = completedLessons; }
    
    public List<Long> getCompletedLessonIds() { return completedLessonIds; }
    public void setCompletedLessonIds(List<Long> completedLessonIds) { this.completedLessonIds = completedLessonIds; }

    @Override
    public String toString() {
        return "ProgressResponseDto [courseId=" + courseId + ", progressPercentage=" + progressPercentage + 
               ", totalLessons=" + totalLessons + ", completedLessons=" + completedLessons + "]";
    }
}
