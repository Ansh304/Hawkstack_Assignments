package com.histack.ltplatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "training_id")
    private Long trainingId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Bookings() {}
    public Bookings(Long trainingId, Long userId, LocalDateTime createdAt) {
        this.trainingId = trainingId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public Bookings(Long id, Long trainingId, Long userId, LocalDateTime createdAt) {
        super();
        this.id = id;
        this.trainingId = trainingId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getTrainingId() { return trainingId; }
    public void setTrainingId(Long trainingId) { this.trainingId = trainingId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Bookings [id=" + id + ", trainingId=" + trainingId + ", userId=" + userId + ", createdAt=" + createdAt + "]";
    }
}
