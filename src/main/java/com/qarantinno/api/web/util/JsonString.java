package com.qarantinno.api.web.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Null or JSON value
 */
@Documented
@Constraint(validatedBy = JsonStringValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonString {

    String message() default "The string should be Json format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
