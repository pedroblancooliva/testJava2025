package com.inditex.testJava2025.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class PriceTest {

    private Price price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @BeforeEach
    void setUp() {
        startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        endDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

        price = new Price(
                1L,
                1L,
                startDate,
                endDate,
                1,
                35455L,
                0,
                new BigDecimal("35.50"),
                "EUR");
    }

    @Test
    void shouldCreatePriceWithAllFields() {
        // Then
        assertEquals(1L, price.getId());
        assertEquals(1, price.getBrandId());
        assertEquals(startDate, price.getStartDate());
        assertEquals(endDate, price.getEndDate());
        assertEquals(1, price.getPriceList());
        assertEquals(35455, price.getProductId());
        assertEquals(0, price.getPriority());
        assertEquals(new BigDecimal("35.50"), price.getPrice());
        assertEquals("EUR", price.getCurrency());
    }

    @Test
    void shouldCreateEmptyPrice() {
        // When
        Price emptyPrice = new Price();

        // Then
        assertNotNull(emptyPrice);
        assertNull(emptyPrice.getId());
    }

    @Test
    void shouldSetAndGetAllFields() {
        // Given
        Price newPrice = new Price();
        LocalDateTime newStartDate = LocalDateTime.of(2021, 1, 1, 0, 0);
        LocalDateTime newEndDate = LocalDateTime.of(2021, 12, 31, 23, 59);

        // When
        newPrice.setId(2L);
        newPrice.setBrandId(2L);
        newPrice.setStartDate(newStartDate);
        newPrice.setEndDate(newEndDate);
        newPrice.setPriceList(2);
        newPrice.setProductId(12345L);
        newPrice.setPriority(1);
        newPrice.setPrice(new BigDecimal("45.99"));
        newPrice.setCurrency("USD");

        // Then
        assertEquals(2L, newPrice.getId());
        assertEquals(2, newPrice.getBrandId());
        assertEquals(newStartDate, newPrice.getStartDate());
        assertEquals(newEndDate, newPrice.getEndDate());
        assertEquals(2, newPrice.getPriceList());
        assertEquals(12345, newPrice.getProductId());
        assertEquals(1, newPrice.getPriority());
        assertEquals(new BigDecimal("45.99"), newPrice.getPrice());
        assertEquals("USD", newPrice.getCurrency());
    }

    @Test
    void shouldBeEqualWhenSameValues() {
        // Given
        Price anotherPrice = new Price(
                1L,
                1L,
                startDate,
                endDate,
                1,
                35455L,
                0,
                new BigDecimal("35.50"),
                "EUR");

        // When & Then
        assertEquals(price, anotherPrice);
        assertEquals(price.hashCode(), anotherPrice.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenDifferentValues() {
        // Given
        Price differentPrice = new Price(
                2L,
                1L,
                startDate,
                endDate,
                1,
                35455L,
                0,
                new BigDecimal("35.50"),
                "EUR");

        // When & Then
        assertNotEquals(price, differentPrice);
    }

    @Test
    void shouldNotBeEqualToNull() {
        // When & Then
        assertNotEquals(price, null);
    }

    @Test
    void shouldNotBeEqualToDifferentClass() {
        // When & Then
        assertNotEquals(price, "Not a Price object");
    }

    @Test
    void shouldHaveToStringRepresentation() {
        // When
        String result = price.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("Price"));
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("brandId=1"));
        assertTrue(result.contains("productId=35455"));
    }
}