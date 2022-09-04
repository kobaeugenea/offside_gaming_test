package ru.kobaevgenii.testtask.offsidegaming.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import ru.kobaevgenii.testtask.offsidegaming.domain.Measurement;
import ru.kobaevgenii.testtask.offsidegaming.domain.User;
import ru.kobaevgenii.testtask.offsidegaming.dto.request.MeasurementRequest;
import ru.kobaevgenii.testtask.offsidegaming.dto.response.ErrorResponse;
import ru.kobaevgenii.testtask.offsidegaming.dto.response.IdResponse;
import ru.kobaevgenii.testtask.offsidegaming.dto.response.MeasurementResponse;
import ru.kobaevgenii.testtask.offsidegaming.dto.response.PageResponse;
import ru.kobaevgenii.testtask.offsidegaming.exception.IllegalRequestParameterValueException;
import ru.kobaevgenii.testtask.offsidegaming.exception.UserNotFoundException;
import ru.kobaevgenii.testtask.offsidegaming.mapper.MeasurementMapper;
import ru.kobaevgenii.testtask.offsidegaming.service.MeasurementService;
import ru.kobaevgenii.testtask.offsidegaming.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MonitoringController {

    @Value("${api.history.maxPageSize}")
    private int maxPageSize;

    @Value("${api.history.defaultPageSize}")
    private int defaultPageSize;

    private final MeasurementService measurementService;
    private final UserService userService;

    private final MeasurementMapper measurementMapper;

    @Operation(summary = "Submit measurement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Measurement submitted"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping(value = "submit-measurement", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IdResponse> submitMeasurement(@Valid @RequestBody MeasurementRequest measurementRequest) {
        Optional<User> user = userService.getUser(measurementRequest.getUsername());
        if (!user.isPresent()) {
            throw new UserNotFoundException(measurementRequest.getUsername());
        }

        Measurement measurement = measurementMapper.fromDTO(measurementRequest, user.get());
        Measurement savedMeasurement = measurementService.submitMeasurement(measurement);

        return ResponseEntity.ok(new IdResponse(savedMeasurement.getId()));
    }

    @Operation(summary = "History of measurements by user with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Measurement submitted"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping(value = "submitted-measurements-history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponse<MeasurementResponse>> getSubmittedMeasurementsHistory(
            @RequestParam @NotEmpty String username,
            @RequestParam(required = false) @Min(value = 0, message = "Must be greater or equal to 0") Integer pageNum,
            @RequestParam(required = false) @Min(value = 1, message = "Must be greater or equal to 0") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 0;
        }
        if (pageSize == null) {
            pageSize = defaultPageSize;
        }
        if (pageSize > maxPageSize) {
            throw new IllegalRequestParameterValueException("pageSize", String.format("Must be less or equal to %d", maxPageSize));
        }

        Optional<User> user = userService.getUser(username);
        if (!user.isPresent()) {
            throw new UserNotFoundException(username);
        }

        Page<Measurement> measurementsPage = measurementService.getSubmittedMeasurementsHistory(user.get(), pageNum, pageSize);
        List<MeasurementResponse> measurementResponseList = measurementsPage.stream()
                                                                            .map(measurementMapper::toDTO)
                                                                            .collect(Collectors.toList());
        return ResponseEntity.ok(new PageResponse<>(measurementResponseList, measurementsPage.getTotalPages()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(Exception exception) {
        if (exception.getClass() == UserNotFoundException.class) {
            String message = String.format("User %s wasn't found", ((UserNotFoundException) exception).getUsername());
            return ResponseEntity.badRequest().body(new ErrorResponse(message));
        } else if (exception.getClass() == IllegalRequestParameterValueException.class) {
            IllegalRequestParameterValueException castedException = ((IllegalRequestParameterValueException) exception);
            String message = String.format("%s param is invalid, %s", castedException.getField(), castedException.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(message));
        } else if (exception.getClass() == MethodArgumentNotValidException.class ||
                   exception.getClass() == MissingServletRequestParameterException.class ||
                   exception.getClass() == IllegalArgumentException.class) {
            return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
        } else {
            String message = String.format("Unexpected sever error, %s", exception.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse(message));
        }
    }

}
