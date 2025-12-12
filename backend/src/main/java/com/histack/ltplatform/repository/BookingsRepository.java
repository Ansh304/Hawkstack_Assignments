package com.histack.ltplatform.repository;

import com.histack.ltplatform.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Long> {
    
    List<Bookings> findByUserId(Long userId);
    
    @Query("SELECT COUNT(b) FROM Bookings b WHERE b.trainingId = :trainingId")
    Long countByTrainingId(@Param("trainingId") Long trainingId);
    
    @Query("SELECT b FROM Bookings b WHERE b.trainingId = :trainingId AND b.userId = :userId")
    List<Bookings> findByTrainingIdAndUserId(@Param("trainingId") Long trainingId, @Param("userId") Long userId);
}
