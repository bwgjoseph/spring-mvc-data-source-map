package com.bwgjoseph.springmvcdatasourcemap.config;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ReferenceValidator.class})
@Documented
public @interface ValidReference {

    String message() default "Valid reference not present";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
