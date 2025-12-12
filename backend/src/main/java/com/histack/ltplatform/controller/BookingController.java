package com.histack.ltplatform.controller;

import com.histack.ltplatform.dto.BookingResponseDto;
import com.histack.ltplatform.dto.TrainingDto;
import com.histack.ltplatform.exceptions.LtPlatformException;
import com.histack.ltplatform.service.BookingService;
import com.histack.ltplatform.util.ApiResponse;
import com.histack.ltplatform.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final ResponseUtil responseUtil;
    private final BookingService bookingService;

    private static final Long DEMO_USER_ID = 1L;

    public BookingController(ResponseUtil responseUtil, BookingService bookingService) {
        this.responseUtil = responseUtil;
        this.bookingService = bookingService;
    }

    @GetMapping("/trainings")
    public ResponseEntity<ApiResponse<List<TrainingDto>>> getTrainings() {
        try {
            List<TrainingDto> trainings = bookingService.getTrainings();
            return responseUtil.setSuccessResponse(trainings, "Trainings fetched successfully");
        } catch (Exception e) {
            return responseUtil.setErrorResponse("Something went wrong. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/trainings/{trainingId}/book")
    public ResponseEntity<ApiResponse<Void>> bookTraining(@PathVariable Long trainingId) {
        try {
            if (trainingId == null || trainingId <= 0) {
                return responseUtil.setErrorResponse("Invalid trainingId", HttpStatus.BAD_REQUEST);
            }

            bookingService.bookTraining(trainingId, DEMO_USER_ID);
            return responseUtil.setSuccessResponse(null, "Training booked successfully");

        } catch (LtPlatformException e) {
            return responseUtil.setErrorResponse(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return responseUtil.setErrorResponse("Something went wrong. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{userId}/bookings")
    public ResponseEntity<ApiResponse<BookingResponseDto>> getUserBookings(@PathVariable Long userId) {
        try {
            if (userId == null || userId <= 0) {
                return responseUtil.setErrorResponse("Invalid userId", HttpStatus.BAD_REQUEST);
            }

            BookingResponseDto bookings = bookingService.getUserBookings(userId);
            return responseUtil.setSuccessResponse(bookings, "User bookings fetched successfully");

        } catch (LtPlatformException e) {
            return responseUtil.setErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return responseUtil.setErrorResponse("Something went wrong. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
