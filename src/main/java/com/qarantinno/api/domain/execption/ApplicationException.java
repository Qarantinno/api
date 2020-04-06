package com.qarantinno.api.domain.execption;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private ExceptionType type;

    public interface ExceptionType {
        String getMessage();
    }

    public ApplicationException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }

}
