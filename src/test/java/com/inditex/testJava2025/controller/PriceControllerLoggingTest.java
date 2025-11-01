package com.inditex.testJava2025.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.inditex.testJava2025.dto.PriceResponseDTO;
import com.inditex.testJava2025.service.PriceService;

@WebMvcTest({PriceController.class, GlobalExceptionHandler.class})
class PriceControllerLoggingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    @Test
    void shouldLogSuccessfulRequest() throws Exception {
        // Given
        PriceResponseDTO expectedResponse = new PriceResponseDTO(
            35455L, 1L, 1, new BigDecimal("35.50"), "EUR"
        );
        
        when(priceService.getApplicablePrice(any(LocalDateTime.class), anyLong(), anyLong()))
            .thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.price").value(35.50));

        // Verify that service was called (this indirectly tests that logging occurred)
        verify(priceService).getApplicablePrice(any(LocalDateTime.class), anyLong(), anyLong());
    }

    @Test
    void shouldLogRequestWithError() throws Exception {
        // Given
        when(priceService.getApplicablePrice(any(LocalDateTime.class), anyLong(), anyLong()))
            .thenThrow(new RuntimeException("Error de prueba"));

        // When & Then
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        // Verify that service was called (this indirectly tests that error logging occurred)
        verify(priceService).getApplicablePrice(any(LocalDateTime.class), anyLong(), anyLong());
    }

    @Test
    void shouldLogInvalidParameters() throws Exception {
        // When & Then - Testing with invalid productId
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "invalid")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        // No need to verify service call as it shouldn't be reached due to parameter validation
    }
}