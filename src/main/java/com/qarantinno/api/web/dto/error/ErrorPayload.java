package com.qarantinno.api.web.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorPayload {

    private int status;
    private String message;
    private Set<FieldErrorPayload> fieldErrorPayloads;

    public ErrorPayload(int status) {
        this.status = status;
    }

    public ErrorPayload(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldErrorPayload {

        private String fieldName;
        private String message;

    }

    @Getter
    public enum InternalStatuses {

        BAD_REQUEST(100, "Invalid request fields"),
        ILLEGAL_OPERATION_EXCEPTION(103),
        NOT_FOUND(104),

        INTERNAL_SERVER_ERROR(500, "Internal server error was occurred. Try again later :(");

        private final int status;
        private String message;

        InternalStatuses(int status) {
            this.status = status;
        }

        InternalStatuses(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
