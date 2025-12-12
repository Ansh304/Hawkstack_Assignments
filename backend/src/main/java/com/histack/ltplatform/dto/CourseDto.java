package com.histack.ltplatform.dto;

import com.histack.ltplatform.entity.Courses;

public class CourseDto {

    private Long id;
    private String title;
    private String category;
    private String level;

    // Default constructor (for JSON deserialization)
    public CourseDto() {}

    // All-args constructor
    public CourseDto(Long id, String title, String category, String level) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.level = level;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getLevel() {
        return level;
    }

    // Setters (if needed for JSON binding)
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    // Static factory method (production pattern)
    public static CourseDto fromEntity(Courses course) {
        return new CourseDto(
            course.getId(),
            course.getTitle(),
            course.getCategory(),
            course.getLevel()
        );
    }

    @Override
    public String toString() {
        return "CourseDto [id=" + id + ", title=" + title + ", category=" + category + ", level=" + level + "]";
    }
}
