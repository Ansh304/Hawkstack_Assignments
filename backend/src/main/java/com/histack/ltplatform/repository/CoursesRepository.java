package com.histack.ltplatform.repository;

import com.histack.ltplatform.entity.Courses;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long> {
	Page<Courses> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Courses> findByCategoryAndLevelAndTitleContainingIgnoreCase(String category, String level, String title, Pageable pageable);

    Page<Courses> findByCategoryAndTitleContainingIgnoreCase(String category, String title, Pageable pageable);

    Page<Courses> findByLevelAndTitleContainingIgnoreCase(String level, String title, Pageable pageable);

}
