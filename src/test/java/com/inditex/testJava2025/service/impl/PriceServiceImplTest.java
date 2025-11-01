package com.inditex.testJava2025.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.inditex.testJava2025.dto.PriceResponseDTO;
import com.inditex.testJava2025.entity.Price;
import com.inditex.testJava2025.exceptions.PriceNotFoundException;
import com.inditex.testJava2025.mapper.PriceMapper;
import com.inditex.testJava2025.repository.PriceRepository;
import com.inditex.testJava2025.service.PriceService;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PriceServiceImplTest {

    @MockBean
    private PriceRepository priceRepository;

    @MockBean
    private PriceMapper priceMapper;

    @Autowired
    private PriceService priceService;

    private LocalDateTime applicationDate;
    private Long productId;
    private Long brandId;
    private Price price1;

    @BeforeEach
    void setUp() {
        applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        productId = 35455L;
        brandId = 1L;

        price1 = new Price(
            1L,
            1L,
            LocalDateTime.of(2020, 6, 14, 0, 0),
            LocalDateTime.of(2020, 12, 31, 23, 59, 59),
            1,
            35455L,
            0,
            new BigDecimal("35.50"),
            "EUR"
        );
    }

    @Test
    void testSimpleCase() {
        // Given
        List<Price> prices = Collections.singletonList(price1);
        PriceResponseDTO expectedResponse = new PriceResponseDTO();
        expectedResponse.setProductId(35455L);
        expectedResponse.setBrandId(1L);
        expectedResponse.setPriceList(1);
        expectedResponse.setPrice(new BigDecimal("35.50"));
        expectedResponse.setCurrency("EUR");
        
        when(priceRepository.findApplicablePrices(brandId, productId, applicationDate))
            .thenReturn(prices);
        when(priceMapper.toResponseDTO(price1)).thenReturn(expectedResponse);

        // When
        PriceResponseDTO result = priceService.getApplicablePrice(applicationDate, productId, brandId);

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals(35455L, result.getProductId());
    }

    @Test
    void shouldThrowExceptionWhenNoPricesFound() {
        // Given
        when(priceRepository.findApplicablePrices(brandId, productId, applicationDate))
            .thenReturn(Collections.emptyList());

        // When & Then
        PriceNotFoundException exception = assertThrows(PriceNotFoundException.class, () -> {
            priceService.getApplicablePrice(applicationDate, productId, brandId);
        });
        
        assertTrue(exception.getMessage().contains("No se encontró precio aplicable"));
    }
}