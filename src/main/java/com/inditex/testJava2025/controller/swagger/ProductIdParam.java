package com.inditex.testJava2025.controller.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.v3.oas.annotations.Parameter;

import static com.inditex.testJava2025.controller.swagger.SwaggerConstants.*;

/**
 * Anotación compuesta para el parámetro productId
 * Combina documentación Swagger y validaciones
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Parameter(description = PARAM_PRODUCT_ID_DESC, example = PARAM_PRODUCT_ID_EXAMPLE, required = true)
@NotNull(message = VALIDATION_PRODUCT_ID_REQUIRED)
@Positive(message = VALIDATION_PRODUCT_ID_POSITIVE)
public @interface ProductIdParam {
}