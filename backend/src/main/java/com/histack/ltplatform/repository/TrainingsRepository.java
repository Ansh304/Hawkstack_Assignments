package com.histack.ltplatform.repository;

import com.histack.ltplatform.entity.Trainings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrainingsRepository extends JpaRepository<Trainings, Long> {
    
    List<Trainings> findByStartDateTimeAfter(LocalDateTime now);
}
