package com.inditex.testJava2025.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.inditex.testJava2025.dto.PriceResponseDTO;
import com.inditex.testJava2025.service.PriceService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    @Test
    void shouldReturnPriceWhenValidRequest() throws Exception {
        // Given
        PriceResponseDTO responseDTO = new PriceResponseDTO();
        responseDTO.setProductId(35455L);
        responseDTO.setBrandId(1L);
        responseDTO.setPriceList(1);
        responseDTO.setPrice(new BigDecimal("35.50"));
        responseDTO.setCurrency("EUR");

        when(priceService.getApplicablePrice(any(LocalDateTime.class), eq(35455L), eq(1L)))
                .thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void shouldReturnBadRequestWhenMissingApplicationDate() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/prices")
                .param("productId", "35455")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenMissingProductId() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenMissingBrandId() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidDateFormat() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "invalid-date")
                .param("productId", "35455")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidProductId() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "invalid-product-id")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidBrandId() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "invalid-brand-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAcceptDifferentDateTimeFormats() throws Exception {
        // Given
        PriceResponseDTO responseDTO = new PriceResponseDTO();
        responseDTO.setProductId(35455L);
        responseDTO.setBrandId(1L);
        responseDTO.setPriceList(1);
        responseDTO.setPrice(new BigDecimal("35.50"));
        responseDTO.setCurrency("EUR");

        when(priceService.getApplicablePrice(any(LocalDateTime.class), eq(35455L), eq(1L)))
                .thenReturn(responseDTO);

        // When & Then - Formato con milisegundos
        mockMvc.perform(get("/api/v1/prices")
                .param("applicationDate", "2020-06-14T10:00:00.000")
                .param("productId", "35455")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
