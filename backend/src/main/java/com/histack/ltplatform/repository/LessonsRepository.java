package com.histack.ltplatform.repository;

import com.histack.ltplatform.entity.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LessonsRepository extends JpaRepository<Lessons, Long> {
    
    List<Lessons> findByCourseIdOrderByOrderIndexAsc(Long courseId);


}
