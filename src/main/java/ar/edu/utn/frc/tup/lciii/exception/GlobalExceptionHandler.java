package ar.edu.utn.frc.tup.lciii.exception;

import ar.edu.utn.frc.tup.lciii.dtos.common.ErrorApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorApi> handleCustomException(CustomException e) {
        ErrorApi error = buildError(e.getMessage(), e.getStatus());
        return ResponseEntity.status(e.getStatus()).body(error);
    }
    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorApi> handleCountryNotFoundException(CountryNotFoundException ex) {
        ErrorApi errorApi = ErrorApi.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorApi, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApi> handleGenericException(Exception ex) {
        ErrorApi errorApi = ErrorApi.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("An unexpected error occurred")
                .build();
        return new ResponseEntity<>(errorApi, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ErrorApi buildError(String message, HttpStatus status) {
        return ErrorApi.builder()
                .timestamp(String.valueOf(Timestamp.from(ZonedDateTime.now().toInstant())))
                .error(status.getReasonPhrase())
                .status(status.value())
                .message(message)
                .build();
    }
}