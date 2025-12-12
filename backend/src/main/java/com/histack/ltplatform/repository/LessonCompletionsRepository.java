package com.histack.ltplatform.repository;

import com.histack.ltplatform.entity.LessonCompletions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonCompletionsRepository extends JpaRepository<LessonCompletions, Long> {
    
    Optional<LessonCompletions> findByLessonIdAndUserId(Long lessonId, Long userId);
    
    @Query("SELECT lc.lessonId FROM LessonCompletions lc WHERE lc.userId = :userId AND lc.lessonId IN " +
           "(SELECT l.id FROM Lessons l WHERE l.courseId = :courseId)")
    List<Long> findCompletedLessonIdsByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);
}
