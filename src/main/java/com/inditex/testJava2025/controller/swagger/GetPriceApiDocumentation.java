package com.inditex.testJava2025.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.inditex.testJava2025.dto.PriceResponseDTO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.inditex.testJava2025.controller.swagger.SwaggerConstants.*;

/**
 * Anotación personalizada que combina todas las anotaciones de Swagger
 * necesarias para documentar el endpoint de consulta de precios.
 * Simplifica el código del controlador manteniendo la documentación completa.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
    summary = GET_PRICE_SUMMARY,
    description = GET_PRICE_DESCRIPTION,
    tags = {PRICE_API_TAG}
)
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200", 
        description = RESPONSE_200_DESC, 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = PriceResponseDTO.class),
            examples = @ExampleObject(
                name = EXAMPLE_SUCCESS_NAME,
                summary = EXAMPLE_SUCCESS_SUMMARY,
                value = EXAMPLE_SUCCESS_VALUE
            )
        )
    ),
    @ApiResponse(
        responseCode = "404", 
        description = RESPONSE_404_DESC, 
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = EXAMPLE_NOT_FOUND_NAME,
                summary = EXAMPLE_NOT_FOUND_SUMMARY,
                value = EXAMPLE_NOT_FOUND_VALUE
            )
        )
    ),
    @ApiResponse(
        responseCode = "400", 
        description = RESPONSE_400_DESC, 
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = EXAMPLE_BAD_REQUEST_NAME,
                summary = EXAMPLE_BAD_REQUEST_SUMMARY,
                value = EXAMPLE_BAD_REQUEST_VALUE
            )
        )
    )
})
public @interface GetPriceApiDocumentation {
    // Esta anotación compuesta encapsula toda la documentación
}