package com.epicman.rideshare.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Validation errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest req) {

        String msg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .orElse("Invalid input");

        return new ApiError(400, msg, req.getRequestURI());
    }

    // 404 errors
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleNotFound(NotFoundException ex, HttpServletRequest req) {
        return new ApiError(404, ex.getMessage(), req.getRequestURI());
    }

    // 400 errors
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleBadRequest(BadRequestException ex, HttpServletRequest req) {
        return new ApiError(400, ex.getMessage(), req.getRequestURI());
    }

    // Conflict errors
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleConflict(ConflictException ex, HttpServletRequest req) {
        return new ApiError(409, ex.getMessage(), req.getRequestURI());
    }

    // Conflict errors
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiError handleForbidden(ForbiddenException ex, HttpServletRequest req) {
        return new ApiError(403, ex.getMessage(), req.getRequestURI());
    }

    // Unknown errors
    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiError handleInternalServerError(InternalServerErrorException ex, HttpServletRequest req) {
        return new ApiError(500, ex.getMessage(), req.getRequestURI());
    }
}
