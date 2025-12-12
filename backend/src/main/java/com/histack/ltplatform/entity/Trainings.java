package com.histack.ltplatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "trainings")
public class Trainings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_datetime")
    private LocalDateTime startDateTime;

    @Column(name = "capacity")
    private Integer capacity;

    public Trainings() {}
    public Trainings(String title, String description, LocalDateTime startDateTime, Integer capacity) {
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.capacity = capacity;
    }

    public Trainings(Long id, String title, String description, LocalDateTime startDateTime, Integer capacity) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.capacity = capacity;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return "Trainings [id=" + id + ", title=" + title + ", description=" + description + ", startDateTime=" + startDateTime + ", capacity=" + capacity + "]";
    }
}
