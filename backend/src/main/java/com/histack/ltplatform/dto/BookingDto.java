package com.histack.ltplatform.dto;

import java.time.LocalDateTime;

public class BookingDto {
    private Long id;
    private Long trainingId;
    private String trainingTitle;
    private LocalDateTime trainingStartDateTime;
    private LocalDateTime createdAt;

    public BookingDto() {}

    public BookingDto(Long id, Long trainingId, String trainingTitle, 
                      LocalDateTime trainingStartDateTime, LocalDateTime createdAt) {
        this.id = id;
        this.trainingId = trainingId;
        this.trainingTitle = trainingTitle;
        this.trainingStartDateTime = trainingStartDateTime;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getTrainingId() { return trainingId; }
    public void setTrainingId(Long trainingId) { this.trainingId = trainingId; }
    
    public String getTrainingTitle() { return trainingTitle; }
    public void setTrainingTitle(String trainingTitle) { this.trainingTitle = trainingTitle; }
    
    public LocalDateTime getTrainingStartDateTime() { return trainingStartDateTime; }
    public void setTrainingStartDateTime(LocalDateTime trainingStartDateTime) { this.trainingStartDateTime = trainingStartDateTime; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}