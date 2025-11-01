package com.inditex.testJava2025.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceResponseDTOBranchTest {

    @Test
    void shouldTestEqualsMethod() {
        // Given
        PriceResponseDTO dto1 = new PriceResponseDTO(35455L, 1L, 1, new BigDecimal("35.50"), "EUR");
        PriceResponseDTO dto2 = new PriceResponseDTO(35455L, 1L, 1, new BigDecimal("35.50"), "EUR");
        PriceResponseDTO dto3 = new PriceResponseDTO(35456L, 1L, 1, new BigDecimal("35.50"), "EUR");

        // Test reflexive
        assertEquals(dto1, dto1);
        
        // Test symmetric  
        assertEquals(dto1, dto2);
        assertEquals(dto2, dto1);
        
        // Test different objects
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, "not a dto");
    }

    @Test
    void shouldTestHashCodeMethod() {
        // Given
        PriceResponseDTO dto1 = new PriceResponseDTO(35455L, 1L, 1, new BigDecimal("35.50"), "EUR");
        PriceResponseDTO dto2 = new PriceResponseDTO(35455L, 1L, 1, new BigDecimal("35.50"), "EUR");

        // Then
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void shouldTestToStringMethod() {
        // Given
        PriceResponseDTO dto = new PriceResponseDTO(35455L, 1L, 1, new BigDecimal("35.50"), "EUR");

        // When
        String toString = dto.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("PriceResponseDTO"));
        assertTrue(toString.contains("35455"));
        assertTrue(toString.contains("35.50"));
    }

    @Test
    void shouldTestEmptyConstructor() {
        // Given
        PriceResponseDTO dto = new PriceResponseDTO();

        // When
        dto.setProductId(35455L);
        dto.setBrandId(1L);
        dto.setPriceList(1);
        dto.setPrice(new BigDecimal("35.50"));
        dto.setCurrency("EUR");

        // Then
        assertEquals(35455L, dto.getProductId());
        assertEquals(1L, dto.getBrandId());
        assertEquals(1, dto.getPriceList());
        assertEquals(new BigDecimal("35.50"), dto.getPrice());
        assertEquals("EUR", dto.getCurrency());
    }

    @Test
    void shouldTestNullValues() {
        // Given
        PriceResponseDTO dto1 = new PriceResponseDTO(null, null, null, null, null);
        PriceResponseDTO dto2 = new PriceResponseDTO(null, null, null, null, null);

        // Then
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}