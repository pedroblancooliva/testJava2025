package com.inditex.testJava2025.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceEntityBranchTest {

    @Test
    void shouldTestEqualsMethod() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Price price1 = new Price(1L, 1L, now, now.plusDays(1), 1, 35455L, 0, new BigDecimal("35.50"), "EUR");
        Price price2 = new Price(1L, 1L, now, now.plusDays(1), 1, 35455L, 0, new BigDecimal("35.50"), "EUR");
        Price price3 = new Price(2L, 1L, now, now.plusDays(1), 1, 35455L, 0, new BigDecimal("35.50"), "EUR");

        // Test reflexive
        assertEquals(price1, price1);
        
        // Test symmetric  
        assertEquals(price1, price2);
        assertEquals(price2, price1);
        
        // Test different objects
        assertNotEquals(price1, price3);
        assertNotEquals(price1, null);
        assertNotEquals(price1, "not a price");
    }

    @Test
    void shouldTestHashCodeMethod() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Price price1 = new Price(1L, 1L, now, now.plusDays(1), 1, 35455L, 0, new BigDecimal("35.50"), "EUR");
        Price price2 = new Price(1L, 1L, now, now.plusDays(1), 1, 35455L, 0, new BigDecimal("35.50"), "EUR");

        // Then
        assertEquals(price1.hashCode(), price2.hashCode());
    }

    @Test
    void shouldTestToStringMethod() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Price price = new Price(1L, 1L, now, now.plusDays(1), 1, 35455L, 0, new BigDecimal("35.50"), "EUR");

        // When
        String toString = price.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("Price"));
        assertTrue(toString.contains("35.50"));
        assertTrue(toString.contains("35455"));
    }

    @Test
    void shouldTestAllGettersAndSetters() {
        // Given
        Price price = new Price();
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);

        // When
        price.setId(1L);
        price.setBrandId(1L);
        price.setStartDate(startDate);
        price.setEndDate(endDate);
        price.setPriceList(1);
        price.setProductId(35455L);
        price.setPriority(0);
        price.setPrice(new BigDecimal("35.50"));
        price.setCurrency("EUR");

        // Then
        assertEquals(1L, price.getId());
        assertEquals(1L, price.getBrandId());
        assertEquals(startDate, price.getStartDate());
        assertEquals(endDate, price.getEndDate());
        assertEquals(1, price.getPriceList());
        assertEquals(35455L, price.getProductId());
        assertEquals(0, price.getPriority());
        assertEquals(new BigDecimal("35.50"), price.getPrice());
        assertEquals("EUR", price.getCurrency());
    }
}