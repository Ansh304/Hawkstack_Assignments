package com.histack.ltplatform.dto;

import java.util.List;

public class CourseResponseDto {

    private List<CourseDto> courses;
    private long totalCourses;

    public CourseResponseDto() {}

    public CourseResponseDto(List<CourseDto> courses, long totalCourses) {
        this.courses = courses;
        this.totalCourses = totalCourses;
    }

    // Getters
    public List<CourseDto> getCourses() {
        return courses;
    }

    public long getTotalCourses() {
        return totalCourses;
    }

    // Setters
    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }

    public void setTotalCourses(long totalCourses) {
        this.totalCourses = totalCourses;
    }

    @Override
    public String toString() {
        return "CourseResponseDto [courses=" + courses + ", totalCourses=" + totalCourses + "]";
    }
}
