package com.inditex.testJava2025.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * DTO para respuestas de error estándar de la API.
 * Proporciona un formato consistente para todos los errores de la aplicación.
 */
@Schema(description = "Respuesta estándar de error de la API")
public class ErrorResponse {
    
    @Schema(description = "Código de estado HTTP", example = "404")
    private int status;
    
    @Schema(description = "Código de error específico de la aplicación", example = "PRICE_NOT_FOUND")
    private String error;
    
    @Schema(description = "Mensaje descriptivo del error", example = "No se encontró precio aplicable para los parámetros especificados")
    private String message;
    
    @Schema(description = "Timestamp del error")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    
    /**
     * Constructor con timestamp automático
     */
    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    
    /**
     * Constructor completo
     */
    public ErrorResponse(int status, String error, String message, LocalDateTime timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }
    
    // Getters y Setters
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return String.format("ErrorResponse{status=%d, error='%s', message='%s', timestamp='%s'}", 
            status, error, message, timestamp);
    }
}