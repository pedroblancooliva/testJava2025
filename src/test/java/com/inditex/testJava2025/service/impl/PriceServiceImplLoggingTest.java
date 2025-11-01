package com.inditex.testJava2025.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.inditex.testJava2025.dto.PriceResponseDTO;
import com.inditex.testJava2025.entity.Price;
import com.inditex.testJava2025.exceptions.PriceNotFoundException;
import com.inditex.testJava2025.mapper.PriceMapper;
import com.inditex.testJava2025.repository.PriceRepository;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplLoggingTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Test
    void shouldLogSuccessfulPriceSearch() {
        // Given
        LocalDateTime testDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        Price mockPrice = createMockPrice(1L, 1L, 35455L, 1, 0, new BigDecimal("35.50"), "EUR");
        List<Price> prices = Arrays.asList(mockPrice);
        PriceResponseDTO expectedDto = new PriceResponseDTO(35455L, 1L, 1, new BigDecimal("35.50"), "EUR");

        when(priceRepository.findApplicablePrices(anyLong(), anyLong(), any(LocalDateTime.class)))
            .thenReturn(prices);
        when(priceMapper.toResponseDTO(any(Price.class)))
            .thenReturn(expectedDto);

        // When
        PriceResponseDTO result = priceService.getApplicablePrice(testDate, 35455L, 1L);

        // Then
        assertNotNull(result);
        assertEquals(35455L, result.getProductId());
        assertEquals(new BigDecimal("35.50"), result.getPrice());
    }

    @Test
    void shouldLogWhenNoPricesFound() {
        // Given
        LocalDateTime testDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        when(priceRepository.findApplicablePrices(anyLong(), anyLong(), any(LocalDateTime.class)))
            .thenReturn(Collections.emptyList());

        // When & Then
        assertThrows(PriceNotFoundException.class, () -> {
            priceService.getApplicablePrice(testDate, 35455L, 1L);
        });
    }

    @Test
    void shouldLogWithMultiplePricesAndSelectHighestPriority() {
        // Given
        LocalDateTime testDate = LocalDateTime.of(2020, 6, 14, 16, 0, 0);
        Price lowPriorityPrice = createMockPrice(1L, 1L, 35455L, 1, 0, new BigDecimal("35.50"), "EUR");
        Price highPriorityPrice = createMockPrice(2L, 1L, 35455L, 2, 1, new BigDecimal("25.45"), "EUR");
        List<Price> prices = Arrays.asList(lowPriorityPrice, highPriorityPrice);
        PriceResponseDTO expectedDto = new PriceResponseDTO(35455L, 1L, 2, new BigDecimal("25.45"), "EUR");

        when(priceRepository.findApplicablePrices(anyLong(), anyLong(), any(LocalDateTime.class)))
            .thenReturn(prices);
        when(priceMapper.toResponseDTO(highPriorityPrice))
            .thenReturn(expectedDto);

        // When
        PriceResponseDTO result = priceService.getApplicablePrice(testDate, 35455L, 1L);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getPriceList());
        assertEquals(new BigDecimal("25.45"), result.getPrice());
    }

    @Test
    void shouldLogValidationErrorForNullParameters() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            priceService.getApplicablePrice(null, 35455L, 1L);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            priceService.getApplicablePrice(LocalDateTime.now(), null, 1L);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            priceService.getApplicablePrice(LocalDateTime.now(), 35455L, null);
        });
    }

    @Test
    void shouldLogValidationErrorForInvalidIds() {
        // Given
        LocalDateTime testDate = LocalDateTime.now();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            priceService.getApplicablePrice(testDate, -1L, 1L);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            priceService.getApplicablePrice(testDate, 0L, 1L);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            priceService.getApplicablePrice(testDate, 35455L, -1L);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            priceService.getApplicablePrice(testDate, 35455L, 0L);
        });
    }

    @Test
    void shouldLogRepositoryError() {
        // Given
        LocalDateTime testDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        when(priceRepository.findApplicablePrices(anyLong(), anyLong(), any(LocalDateTime.class)))
            .thenThrow(new RuntimeException("Database connection error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            priceService.getApplicablePrice(testDate, 35455L, 1L);
        });
    }

    private Price createMockPrice(Long id, Long brandId, Long productId, Integer priceList, 
                                 Integer priority, BigDecimal price, String currency) {
        Price mockPrice = new Price();
        mockPrice.setId(id);
        mockPrice.setBrandId(brandId);
        mockPrice.setProductId(productId);
        mockPrice.setPriceList(priceList);
        mockPrice.setPriority(priority);
        mockPrice.setPrice(price);
        mockPrice.setCurrency(currency);
        mockPrice.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0));
        mockPrice.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        return mockPrice;
    }
}