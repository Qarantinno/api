package com.qarantinno.api.web.controller;

import com.qarantinno.api.domain.execption.IllegalOperationException;
import com.qarantinno.api.domain.execption.ResourceNotFoundException;
import com.qarantinno.api.web.dto.error.ErrorPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;
import java.util.stream.Collectors;

import static com.qarantinno.api.web.dto.error.ErrorPayload.InternalStatuses.BAD_REQUEST;
import static com.qarantinno.api.web.dto.error.ErrorPayload.InternalStatuses.ILLEGAL_OPERATION_EXCEPTION;
import static com.qarantinno.api.web.dto.error.ErrorPayload.InternalStatuses.INTERNAL_SERVER_ERROR;
import static com.qarantinno.api.web.dto.error.ErrorPayload.InternalStatuses.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class WebExceptionHandler {

    @ExceptionHandler(IllegalOperationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody ErrorPayload handleIllegalOperationException(IllegalOperationException e) {
        return new ErrorPayload(ILLEGAL_OPERATION_EXCEPTION.getStatus(), e.getType().getMessage());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody ErrorPayload handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        return new ErrorPayload(ILLEGAL_OPERATION_EXCEPTION.getStatus(), e.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorPayload handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return new ErrorPayload(BAD_REQUEST.getStatus(), e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorPayload handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ErrorPayload(NOT_FOUND.getStatus(), e.getType().getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorPayload handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorPayload result = new ErrorPayload(BAD_REQUEST.getStatus(), BAD_REQUEST.getMessage());
        Set<ErrorPayload.FieldErrorPayload> fieldErrorPayloads = retrieveFieldErrorPayloads(e.getBindingResult());
        result.setFieldErrorPayloads(fieldErrorPayloads);
        return result;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorPayload handleMethodArgumentNotValidException(BindException e) {
        ErrorPayload result = new ErrorPayload(BAD_REQUEST.getStatus(), BAD_REQUEST.getMessage());
        Set<ErrorPayload.FieldErrorPayload> fieldErrorPayloads = retrieveFieldErrorPayloads(e.getBindingResult());
        result.setFieldErrorPayloads(fieldErrorPayloads);
        return result;
    }

    private Set<ErrorPayload.FieldErrorPayload> retrieveFieldErrorPayloads(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                            .map(fieldError -> new ErrorPayload.FieldErrorPayload(fieldError.getField(), fieldError.getDefaultMessage()))
                            .collect(Collectors.toSet());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorPayload handleNotTrackedExceptions(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorPayload(INTERNAL_SERVER_ERROR.getStatus(), INTERNAL_SERVER_ERROR.getMessage());
    }
}
