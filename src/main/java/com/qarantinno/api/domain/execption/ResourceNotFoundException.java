package com.qarantinno.api.domain.execption;

public class ResourceNotFoundException extends ApplicationException {

    public enum ResourceNotFoundExceptionType implements ExceptionType {

        PLACE_NOT_FOUND("Place does not exist");

        private final String message;

        ResourceNotFoundExceptionType(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    public ResourceNotFoundException(ExceptionType type, String message) {
        super(type, message);
    }
}
