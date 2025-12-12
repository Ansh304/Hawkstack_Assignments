package com.histack.ltplatform.service;

import com.histack.ltplatform.dto.BookingDto;
import com.histack.ltplatform.dto.BookingResponseDto;
import com.histack.ltplatform.dto.TrainingDto;
import com.histack.ltplatform.entity.Bookings;
import com.histack.ltplatform.entity.Trainings;
import com.histack.ltplatform.exceptions.LtPlatformException;
import com.histack.ltplatform.repository.BookingsRepository;
import com.histack.ltplatform.repository.TrainingsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final TrainingsRepository trainingsRepository;
    private final BookingsRepository bookingsRepository;

    public BookingServiceImpl(TrainingsRepository trainingsRepository, BookingsRepository bookingsRepository) {
        this.trainingsRepository = trainingsRepository;
        this.bookingsRepository = bookingsRepository;
    }

    @Override
    public List<TrainingDto> getTrainings() {
        List<Trainings> trainings = trainingsRepository.findAll(Sort.by(Sort.Direction.ASC, "startDateTime"));
        
        return trainings.stream()
                .map(training -> {
                    Long bookedCount = bookingsRepository.countByTrainingId(training.getId());
                    return TrainingDto.fromEntity(training, bookedCount);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void bookTraining(Long trainingId, Long userId) {
        Trainings training = trainingsRepository.findById(trainingId)
                .orElseThrow(() -> new LtPlatformException("Training not found: " + trainingId));

        Long bookedCount = bookingsRepository.countByTrainingId(trainingId);
        if (bookedCount >= training.getCapacity()) {
            throw new LtPlatformException("Training is fully booked. Capacity: " + training.getCapacity());
        }

        if (bookingsRepository.findByTrainingIdAndUserId(trainingId, userId).size() > 0) {
            throw new LtPlatformException("You have already booked this training");
        }

        Bookings booking = new Bookings(trainingId, userId, LocalDateTime.now());
        bookingsRepository.save(booking);
    }

    @Override
    public BookingResponseDto getUserBookings(Long userId) {
        List<Bookings> bookings = bookingsRepository.findByUserId(userId);
        
        List<BookingDto> bookingDtos = bookings.stream()
                .map(booking -> {
                    Trainings training = trainingsRepository.findById(booking.getTrainingId()).orElse(null);
                    return new BookingDto(
                        booking.getId(),
                        booking.getTrainingId(),
                        training != null ? training.getTitle() : "Training not found",
                        training != null ? training.getStartDateTime() : null,
                        booking.getCreatedAt()
                    );
                })
                .collect(Collectors.toList());

        return new BookingResponseDto(bookingDtos, bookingDtos.size());
    }
}
