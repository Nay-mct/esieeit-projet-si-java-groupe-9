package com.esieeit.projetsi.api.error;

import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.exception.InvalidDataException;
import com.esieeit.projetsi.domain.exception.ResourceNotFoundException;
import com.esieeit.projetsi.domain.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidation(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {
                List<FieldErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
                                .map(fieldError -> new FieldErrorDetail(fieldError.getField(),
                                                fieldError.getDefaultMessage()))
                                .toList();

                ErrorResponse body = new ErrorResponse(
                                Instant.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                "VALIDATION_ERROR",
                                "La requête est invalide",
                                request.getRequestURI(),
                                details);

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
                return build(
                                HttpStatus.NOT_FOUND,
                                "NOT_FOUND",
                                ex.getMessage(),
                                request.getRequestURI(),
                                List.of());
        }

        @ExceptionHandler(InvalidDataException.class)
        public ResponseEntity<ErrorResponse> handleInvalidData(InvalidDataException ex, HttpServletRequest request) {
                return build(
                                HttpStatus.BAD_REQUEST,
                                "INVALID_DATA",
                                ex.getMessage(),
                                request.getRequestURI(),
                                List.of());
        }

        @ExceptionHandler(ValidationException.class)
        public ResponseEntity<ErrorResponse> handleDomainValidation(ValidationException ex,
                        HttpServletRequest request) {
                return build(
                                HttpStatus.BAD_REQUEST,
                                "INVALID_DATA",
                                ex.getMessage(),
                                request.getRequestURI(),
                                List.of());
        }

        @ExceptionHandler(BusinessRuleException.class)
        public ResponseEntity<ErrorResponse> handleBusinessRule(BusinessRuleException ex, HttpServletRequest request) {
                return build(
                                HttpStatus.CONFLICT,
                                "BUSINESS_RULE_VIOLATION",
                                ex.getMessage(),
                                request.getRequestURI(),
                                List.of());
        }

        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<ErrorResponse> handleNoResource(NoResourceFoundException ex, HttpServletRequest request) {
                return build(
                                HttpStatus.NOT_FOUND,
                                "NOT_FOUND",
                                "Resource not found",
                                request.getRequestURI(),
                                List.of());
        }

        @ExceptionHandler(ResponseStatusException.class)
        public ResponseEntity<ErrorResponse> handleResponseStatus(ResponseStatusException ex,
                        HttpServletRequest request) {
                HttpStatusCode statusCode = ex.getStatusCode();
                HttpStatus status = HttpStatus.resolve(statusCode.value());
                if (status == null) {
                        status = HttpStatus.INTERNAL_SERVER_ERROR;
                }
                String error = status == HttpStatus.NOT_FOUND ? "NOT_FOUND" : status.name();
                String message = ex.getReason() != null ? ex.getReason() : ex.getMessage();
                return build(
                                status,
                                error,
                                message,
                                request.getRequestURI(),
                                List.of());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, HttpServletRequest request) {
                return build(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "INTERNAL_ERROR",
                                "Une erreur inattendue est survenue",
                                request.getRequestURI(),
                                List.of());
        }

        private ResponseEntity<ErrorResponse> build(
                        HttpStatus status,
                        String error,
                        String message,
                        String path,
                        List<FieldErrorDetail> details) {
                ErrorResponse body = new ErrorResponse(
                                Instant.now(),
                                status.value(),
                                error,
                                message,
                                path,
                                details);
                return ResponseEntity.status(status).body(body);
        }
}