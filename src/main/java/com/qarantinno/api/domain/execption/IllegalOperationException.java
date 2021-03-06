package com.qarantinno.api.domain.execption;

public class IllegalOperationException extends ApplicationException {

    public enum IllegalOperationExceptionType implements ExceptionType {

        ILLEGAL_PLACE_CREATE("Cannot create a place"),
        ILLEGAL_SHOT_CREATE("Cannot create a shot"),
        ILLEGAL_WEEK_DAY("Unsupported week day"),
        MUTUAL_EXCLUSIVE_VALUES("Some values are mutual exclusive"),
        ILLEGAL_PLACE_GET("Cannot get a place"),
        ILLEGAL_STATISTICS_GET("Cannot get a statistics");

        private final String message;

        IllegalOperationExceptionType(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    public IllegalOperationException(ExceptionType type, String message) {
        super(type, message);
    }
}
