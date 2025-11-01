package com.inditex.testJava2025.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.inditex.testJava2025.exceptions.PriceNotFoundException;
import com.inditex.testJava2025.service.PriceService;

import java.time.LocalDateTime;

@WebMvcTest({PriceController.class, GlobalExceptionHandler.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void shouldHandlePriceNotFoundException() throws Exception {
        // Given
        when(priceService.getApplicablePrice(any(LocalDateTime.class), anyLong(), anyLong()))
            .thenThrow(new PriceNotFoundException("No se encontró precio aplicable para producto 35455 de marca 1 en fecha 2020-06-14T10:00"));

        // When & Then
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("PRICE_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("No se encontró precio aplicable para producto 35455 de marca 1 en fecha 2020-06-14T10:00"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldHandleIllegalArgumentException() throws Exception {
        // Given
        when(priceService.getApplicablePrice(any(LocalDateTime.class), anyLong(), anyLong()))
            .thenThrow(new IllegalArgumentException("El ID del producto debe ser positivo"));

        // When & Then
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("El ID del producto debe ser positivo"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldHandleMethodArgumentTypeMismatchException() throws Exception {
        // When & Then - pasar un string donde se espera un Long
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "invalid")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("INVALID_PARAMETER_TYPE"))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("productId")))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldHandleInvalidDateFormat() throws Exception {
        // When & Then - fecha con formato inválido
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "invalid-date")
                .param("productId", "35455")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldHandleGenericException() {
        // Given
        Exception genericException = new RuntimeException("Error inesperado");

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(genericException);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(500, errorResponse.getStatus());
        assertEquals("INTERNAL_SERVER_ERROR", errorResponse.getError());
        assertEquals("Ha ocurrido un error interno. Por favor, inténtelo más tarde.", errorResponse.getMessage());
        assertNotNull(errorResponse.getTimestamp());
    }

    @Test
    void shouldTestErrorResponseToString() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        ErrorResponse errorResponse = new ErrorResponse(404, "TEST_ERROR", "Test message", now);

        // When
        String result = errorResponse.toString();

        // Then
        assertTrue(result.contains("404"));
        assertTrue(result.contains("TEST_ERROR"));
        assertTrue(result.contains("Test message"));
        assertTrue(result.contains(now.toString()));
    }

    @Test
    void shouldTestErrorResponseSetters() {
        // Given
        ErrorResponse errorResponse = new ErrorResponse(400, "INITIAL", "Initial message");
        LocalDateTime newTimestamp = LocalDateTime.now().plusMinutes(1);

        // When
        errorResponse.setStatus(500);
        errorResponse.setError("UPDATED");
        errorResponse.setMessage("Updated message");
        errorResponse.setTimestamp(newTimestamp);

        // Then
        assertEquals(500, errorResponse.getStatus());
        assertEquals("UPDATED", errorResponse.getError());
        assertEquals("Updated message", errorResponse.getMessage());
        assertEquals(newTimestamp, errorResponse.getTimestamp());
    }
}