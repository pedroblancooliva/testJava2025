package com.inditex.testJava2025.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.inditex.testJava2025.exceptions.PriceNotFoundException;

/**
 * Manejador global de excepciones para toda la aplicación.
 * Centraliza el manejo de errores y proporciona respuestas consistentes.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
    
    /**
     * Manejo de excepción cuando no se encuentra un precio aplicable
     */
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePriceNotFound(PriceNotFoundException ex) {
        logger.warn("Precio no encontrado: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(), 
            "PRICE_NOT_FOUND", 
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    /**
     * Manejo de errores de validación de argumentos
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(IllegalArgumentException ex) {
        logger.warn("Error de validación: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(), 
            "VALIDATION_ERROR", 
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    /**
     * Manejo de errores de tipo de argumento incorrecto
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("El parámetro '%s' debe ser de tipo %s", 
            ex.getName(), 
            ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "válido"
        );
        logger.warn("Error de tipo de parámetro: {}", message);
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(), 
            "INVALID_PARAMETER_TYPE", 
            message
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    /**
     * Manejo de errores de validación de Bean Validation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = "Error de validación en los parámetros de entrada";
        if (ex.getBindingResult().hasFieldErrors()) {
            message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        }
        
        logger.warn("Error de validación Bean Validation: {}", message);
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(), 
            "VALIDATION_ERROR", 
            message
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    /**
     * Manejo de errores genéricos no capturados
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Error interno del servidor: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            "INTERNAL_SERVER_ERROR", 
            "Ha ocurrido un error interno. Por favor, inténtelo más tarde."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}