package com.histack.ltplatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_completions")
public class LessonCompletions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public LessonCompletions() {}
    public LessonCompletions(Long lessonId, Long userId, LocalDateTime completedAt) {
        this.lessonId = lessonId;
        this.userId = userId;
        this.completedAt = completedAt;
    }

    public LessonCompletions(Long id, Long lessonId, Long userId, LocalDateTime completedAt) {
        super();
        this.id = id;
        this.lessonId = lessonId;
        this.userId = userId;
        this.completedAt = completedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getLessonId() { return lessonId; }
    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    @Override
    public String toString() {
        return "LessonCompletions [id=" + id + ", lessonId=" + lessonId + ", userId=" + userId + ", completedAt=" + completedAt + "]";
    }
}
