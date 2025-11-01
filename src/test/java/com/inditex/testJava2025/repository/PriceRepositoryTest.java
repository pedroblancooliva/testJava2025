package com.inditex.testJava2025.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.inditex.testJava2025.entity.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.sql.init.mode=never")
class PriceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PriceRepository priceRepository;

    @Test
    void shouldFindApplicablePricesWithinDateRange() {
        // Given
        Price price1 = new Price(
                null,
                1L,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                1,
                35455L,
                0,
                new BigDecimal("35.50"),
                "EUR");

        Price price2 = new Price(
                null,
                1L,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                2,
                35455L,
                1,
                new BigDecimal("25.45"),
                "EUR");

        entityManager.persistAndFlush(price1);
        entityManager.persistAndFlush(price2);

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        // When
        List<Price> result = priceRepository.findApplicablePrices(brandId, productId, applicationDate);

        // Then
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getPriceList() == 1));
        assertTrue(result.stream().anyMatch(p -> p.getPriceList() == 2));
    }

    @Test
    void shouldNotFindPricesOutsideDateRange() {
        // Given
        Price price = new Price(
                null,
                1L,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                1,
                35455L,
                0,
                new BigDecimal("35.50"),
                "EUR");

        entityManager.persistAndFlush(price);

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 19, 0); // Fuera del rango
        Long brandId = 1L;
        Long productId = 35455L;

        // When
        List<Price> result = priceRepository.findApplicablePrices(brandId, productId, applicationDate);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldNotFindPricesForDifferentBrand() {
        // Given
        Price price = new Price(
                null,
                1L,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                1,
                35455L,
                0,
                new BigDecimal("35.50"),
                "EUR");

        entityManager.persistAndFlush(price);

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long brandId = 2L; // Marca diferente
        Long productId = 35455L;

        // When
        List<Price> result = priceRepository.findApplicablePrices(brandId, productId, applicationDate);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldNotFindPricesForDifferentProduct() {
        // Given
        Price price = new Price(
                null,
                1L,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                1,
                35455L,
                0,
                new BigDecimal("35.50"),
                "EUR");

        entityManager.persistAndFlush(price);

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long brandId = 1L;
        Long productId = 99999L; // Producto diferente

        // When
        List<Price> result = priceRepository.findApplicablePrices(brandId, productId, applicationDate);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindPricesOrderedByPriorityDescending() {
        // Given
        Price lowPriority = new Price(
                null,
                1L,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                1,
                35455L,
                0, // Baja prioridad
                new BigDecimal("35.50"),
                "EUR");

        Price highPriority = new Price(
                null,
                1L,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                2,
                35455L,
                1, // Alta prioridad
                new BigDecimal("25.45"),
                "EUR");

        entityManager.persistAndFlush(lowPriority);
        entityManager.persistAndFlush(highPriority);

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        // When
        List<Price> result = priceRepository.findApplicablePrices(brandId, productId, applicationDate);

        // Then
        assertEquals(2, result.size());
        // Verificar que están ordenados por prioridad descendente
        assertEquals(1, result.get(0).getPriority()); // Mayor prioridad primero
        assertEquals(0, result.get(1).getPriority()); // Menor prioridad después
    }

    @Test
    void shouldHandleExactStartAndEndDates() {
        // Given
        Price price = new Price(
                null,
                1L,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                1,
                35455L,
                0,
                new BigDecimal("35.50"),
                "EUR");

        entityManager.persistAndFlush(price);
        Long brandId = 1L;
        Long productId = 35455L;

        // When - Test con fecha de inicio exacta
        List<Price> resultStart = priceRepository.findApplicablePrices(
                brandId, productId, LocalDateTime.of(2020, 6, 14, 15, 0));

        // When - Test con fecha de fin exacta
        List<Price> resultEnd = priceRepository.findApplicablePrices(
                brandId, productId, LocalDateTime.of(2020, 6, 14, 18, 30));

        // Then
        assertEquals(1, resultStart.size());
        assertEquals(1, resultEnd.size());
    }
}