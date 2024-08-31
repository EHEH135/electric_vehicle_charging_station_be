package com.example.electricStation.exception;

import com.example.electricStation.common.CommonResponseService;
import com.example.electricStation.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final CommonResponseService commonResponseService;

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SingleResponse<String> handleIllegalStateException(IllegalStateException ex) {
        return commonResponseService.getSingleErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SingleResponse<String> handleNotFoundException(UserNotFoundException ex) {
        return commonResponseService.getSingleErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(LocationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SingleResponse<String> handleLocationException(LocationException ex) {
        return commonResponseService.getSingleErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(ApiServerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SingleResponse<String> handleApiServerException(ApiServerException ex) {
        return commonResponseService.getSingleErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
