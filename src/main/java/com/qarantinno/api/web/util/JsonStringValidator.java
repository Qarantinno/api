package com.qarantinno.api.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class JsonStringValidator implements ConstraintValidator<JsonString, String> {

    private final ObjectMapper mapper;

    public JsonStringValidator() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public void initialize(JsonString constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || isJsonString(value);
    }

    private boolean isJsonString(String value) {
        try {
            mapper.readTree(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
