package com.histack.ltplatform.service;

import com.histack.ltplatform.dto.BookingResponseDto;
import com.histack.ltplatform.dto.TrainingDto;
import java.util.List;

public interface BookingService {
    List<TrainingDto> getTrainings();
    void bookTraining(Long trainingId, Long userId);
    BookingResponseDto getUserBookings(Long userId);
}
