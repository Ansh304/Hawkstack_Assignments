package com.histack.ltplatform.config;

import com.histack.ltplatform.entity.*;
import com.histack.ltplatform.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            UsersRepository usersRepo,
            CoursesRepository coursesRepo,
            LessonsRepository lessonsRepo,
            LessonCompletionsRepository completionsRepo,
            TrainingsRepository trainingsRepo,
            BookingsRepository bookingsRepo) {

        return args -> {
            System.out.println("Starting data initialization...");

            // 1. USERS (5 users for demo)
            usersRepo.save(new Users("John Doe"));
            usersRepo.save(new Users("Jane Smith"));
            usersRepo.save(new Users("Mike Johnson"));
            usersRepo.save(new Users("Sarah Wilson"));
            usersRepo.save(new Users("David Brown"));
            // 2. COURSES (8 courses across categories/levels)
            coursesRepo.save(new Courses("Java Spring Boot Fundamentals", "Backend", "Beginner"));
            coursesRepo.save(new Courses("Advanced Spring Security", "Backend", "Advanced"));
            coursesRepo.save(new Courses("Vue.js Complete Guide", "Frontend", "Beginner"));
            coursesRepo.save(new Courses("React + TypeScript Masterclass", "Frontend", "Intermediate"));
            coursesRepo.save(new Courses("Docker & Kubernetes Deep Dive", "DevOps", "Intermediate"));
            coursesRepo.save(new Courses("Microservices with Spring Cloud", "Backend", "Advanced"));
            coursesRepo.save(new Courses("PostgreSQL Administration", "Database", "Intermediate"));
            coursesRepo.save(new Courses("AWS Cloud Practitioner", "Cloud", "Beginner"));

            // 3. LESSONS (4-5 lessons per course = 35 total)
            // Course 1: Spring Boot Fundamentals (ID=1)
            lessonsRepo.save(new Lessons(1L, "Spring Boot Introduction", 1));
            lessonsRepo.save(new Lessons(1L, "REST API Development", 2));
            lessonsRepo.save(new Lessons(1L, "Database with JPA", 3));
            lessonsRepo.save(new Lessons(1L, "Spring Security Basics", 4));
            lessonsRepo.save(new Lessons(1L, "Testing Spring Apps", 5));

            // Course 2: Advanced Spring Security (ID=2)  
            lessonsRepo.save(new Lessons(2L, "OAuth2 & JWT", 1));
            lessonsRepo.save(new Lessons(2L, "Role-Based Access", 2));
            lessonsRepo.save(new Lessons(2L, "Custom Filters", 3));

            // Course 3: Vue.js Guide (ID=3)
            lessonsRepo.save(new Lessons(3L, "Vue 3 Setup", 1));
            lessonsRepo.save(new Lessons(3L, "Components & Props", 2));
            lessonsRepo.save(new Lessons(3L, "Vue Router", 3));
            lessonsRepo.save(new Lessons(3L, "Vuex/Pinia State", 4));

            // Course 4: React + TypeScript (ID=4)
            lessonsRepo.save(new Lessons(4L, "React Hooks", 1));
            lessonsRepo.save(new Lessons(4L, "TypeScript Basics", 2));
            lessonsRepo.save(new Lessons(4L, "Custom Hooks", 3));
            lessonsRepo.save(new Lessons(4L, "React Router", 4));

            // Course 5: Docker & K8s (ID=5)
            lessonsRepo.save(new Lessons(5L, "Docker Basics", 1));
            lessonsRepo.save(new Lessons(5L, "Docker Compose", 2));
            lessonsRepo.save(new Lessons(5L, "Kubernetes Pods", 3));
            lessonsRepo.save(new Lessons(5L, "Deployments", 4));

            // Course 6: Microservices (ID=6)
            lessonsRepo.save(new Lessons(6L, "Microservices Patterns", 1));
            lessonsRepo.save(new Lessons(6L, "Spring Cloud Config", 2));
            lessonsRepo.save(new Lessons(6L, "Service Discovery", 3));

            // Course 7: PostgreSQL (ID=7)
            lessonsRepo.save(new Lessons(7L, "PostgreSQL Basics", 1));
            lessonsRepo.save(new Lessons(7L, "Advanced Queries", 2));
            lessonsRepo.save(new Lessons(7L, "Performance Tuning", 3));

            // Course 8: AWS Cloud (ID=8)
            lessonsRepo.save(new Lessons(8L, "AWS EC2 Basics", 1));
            lessonsRepo.save(new Lessons(8L, "S3 Storage", 2));
            lessonsRepo.save(new Lessons(8L, "Lambda Functions", 3));

            // 4. Sample LESSON COMPLETIONS (John Doe = User ID 1 has partial progress)
            completionsRepo.save(new LessonCompletions(1L, 1L, LocalDateTime.now().minusDays(5)));
            completionsRepo.save(new LessonCompletions(2L, 1L, LocalDateTime.now().minusDays(3)));
            completionsRepo.save(new LessonCompletions(1L, 3L, LocalDateTime.now().minusDays(2)));
            completionsRepo.save(new LessonCompletions(3L, 3L, LocalDateTime.now()));
            System.out.println("✅ Sample completions seeded");

            // 5. TRAININGS (6 upcoming trainings)
            trainingsRepo.save(new Trainings("Docker & Kubernetes Workshop", 
                "Hands-on container orchestration workshop", LocalDateTime.now().plusDays(3), 15));
            trainingsRepo.save(new Trainings("React Advanced Patterns", 
                "Master React hooks, context, and performance", LocalDateTime.now().plusDays(7), 12));
            trainingsRepo.save(new Trainings("AWS Cloud Practitioner Prep", 
                "Get certified in 2 days!", LocalDateTime.now().plusDays(10), 20));
            trainingsRepo.save(new Trainings("Spring Boot Microservices", 
                "Build scalable services", LocalDateTime.now().plusDays(14), 10));
            trainingsRepo.save(new Trainings("Vue.js + Nuxt.js Masterclass", 
                "Full-stack Vue development", LocalDateTime.now().plusDays(18), 8));
            trainingsRepo.save(new Trainings("PostgreSQL Performance", 
                "Database optimization techniques", LocalDateTime.now().plusDays(22), 15));
            System.out.println("✅ 6 Trainings seeded");

            // 6. Sample BOOKINGS (User 1 booked 2 trainings)
            bookingsRepo.save(new Bookings(1L, 1L, LocalDateTime.now().minusDays(2)));
            bookingsRepo.save(new Bookings(2L, 3L, LocalDateTime.now().minusDays(1)));
            System.out.println("Sample bookings seeded");
        };
    }
}
