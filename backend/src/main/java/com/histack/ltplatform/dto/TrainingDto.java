package com.histack.ltplatform.dto;

import java.time.LocalDateTime;

import com.histack.ltplatform.entity.Trainings;

public class TrainingDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private Integer capacity;
    private Long bookedCount;  
    private Integer availableSeats;

    public TrainingDto() {}

    public TrainingDto(Long id, String title, String description, LocalDateTime startDateTime, 
                       Integer capacity, Long bookedCount, Integer availableSeats) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.capacity = capacity;
        this.bookedCount = bookedCount;
        this.availableSeats = availableSeats;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDateTime getStartDateTime() { return startDateTime; }
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }
    
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    
    public Long getBookedCount() { return bookedCount; }
    public void setBookedCount(Long bookedCount) { this.bookedCount = bookedCount; }
    
    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

    public static TrainingDto fromEntity(Trainings training, Long bookedCount) {
        return new TrainingDto(
            training.getId(),
            training.getTitle(),
            training.getDescription(),
            training.getStartDateTime(),
            training.getCapacity(),
            bookedCount,
            Math.toIntExact(training.getCapacity() - bookedCount)
        );
    }

    @Override
    public String toString() {
        return "TrainingDto [id=" + id + ", title=" + title + ", availableSeats=" + availableSeats + "]";
    }
}
