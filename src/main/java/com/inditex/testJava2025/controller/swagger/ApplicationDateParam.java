package com.inditex.testJava2025.controller.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.Parameter;

import static com.inditex.testJava2025.controller.swagger.SwaggerConstants.*;

/**
 * Anotación compuesta para el parámetro applicationDate
 * Combina documentación Swagger, validación y formato de fecha
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Parameter(description = PARAM_APPLICATION_DATE_DESC, example = PARAM_APPLICATION_DATE_EXAMPLE, required = true)
@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
@NotNull(message = VALIDATION_APPLICATION_DATE_REQUIRED)
public @interface ApplicationDateParam {
}