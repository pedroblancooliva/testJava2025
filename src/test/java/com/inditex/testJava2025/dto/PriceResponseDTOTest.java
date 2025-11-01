package com.inditex.testJava2025.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

class PriceResponseDTOTest {

    private PriceResponseDTO priceResponseDTO;

    @BeforeEach
    void setUp() {
        priceResponseDTO = new PriceResponseDTO();
        priceResponseDTO.setProductId(35455L);
        priceResponseDTO.setBrandId(1L);
        priceResponseDTO.setPriceList(1);
        priceResponseDTO.setPrice(new BigDecimal("35.50"));
        priceResponseDTO.setCurrency("EUR");
    }

    @Test
    void shouldSetAndGetProductId() {
        // Given
        Long productId = 12345L;

        // When
        priceResponseDTO.setProductId(productId);

        // Then
        assertEquals(productId, priceResponseDTO.getProductId());
    }

    @Test
    void shouldSetAndGetBrandId() {
        // Given
        Long brandId = 2L;

        // When
        priceResponseDTO.setBrandId(brandId);

        // Then
        assertEquals(brandId, priceResponseDTO.getBrandId());
    }

    @Test
    void shouldSetAndGetPriceList() {
        // Given
        Integer priceList = 3;

        // When
        priceResponseDTO.setPriceList(priceList);

        // Then
        assertEquals(priceList, priceResponseDTO.getPriceList());
    }

    @Test
    void shouldSetAndGetPrice() {
        // Given
        BigDecimal price = new BigDecimal("99.99");

        // When
        priceResponseDTO.setPrice(price);

        // Then
        assertEquals(price, priceResponseDTO.getPrice());
    }

    @Test
    void shouldSetAndGetCurrency() {
        // Given
        String currency = "USD";

        // When
        priceResponseDTO.setCurrency(currency);

        // Then
        assertEquals(currency, priceResponseDTO.getCurrency());
    }

    @Test
    void shouldBeEqualWhenSameValues() {
        // Given
        PriceResponseDTO anotherDTO = new PriceResponseDTO();
        anotherDTO.setProductId(35455L);
        anotherDTO.setBrandId(1L);
        anotherDTO.setPriceList(1);
        anotherDTO.setPrice(new BigDecimal("35.50"));
        anotherDTO.setCurrency("EUR");

        // When & Then
        assertEquals(priceResponseDTO, anotherDTO);
        assertEquals(priceResponseDTO.hashCode(), anotherDTO.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenDifferentValues() {
        // Given
        PriceResponseDTO differentDTO = new PriceResponseDTO();
        differentDTO.setProductId(99999L);
        differentDTO.setBrandId(1L);
        differentDTO.setPriceList(1);
        differentDTO.setPrice(new BigDecimal("35.50"));
        differentDTO.setCurrency("EUR");

        // When & Then
        assertNotEquals(priceResponseDTO, differentDTO);
    }

    @Test
    void shouldNotBeEqualToNull() {
        // When & Then
        assertNotEquals(priceResponseDTO, null);
    }

    @Test
    void shouldNotBeEqualToDifferentClass() {
        // When & Then
        assertNotEquals(priceResponseDTO, "Not a DTO object");
    }

    @Test
    void shouldHaveToStringRepresentation() {
        // When
        String result = priceResponseDTO.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("PriceResponseDTO"));
        assertTrue(result.contains("productId=35455"));
        assertTrue(result.contains("brandId=1"));
        assertTrue(result.contains("price=35.50"));
        assertTrue(result.contains("currency=EUR"));
    }

    @Test
    void shouldCreateEmptyDTO() {
        // When
        PriceResponseDTO emptyDTO = new PriceResponseDTO();

        // Then
        assertNotNull(emptyDTO);
        assertNull(emptyDTO.getProductId());
        assertNull(emptyDTO.getBrandId());
        assertNull(emptyDTO.getPriceList());
        assertNull(emptyDTO.getPrice());
        assertNull(emptyDTO.getCurrency());
    }
}