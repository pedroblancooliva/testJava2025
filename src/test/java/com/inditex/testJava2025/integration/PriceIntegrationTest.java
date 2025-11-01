package com.inditex.testJava2025.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.inditex.testJava2025.dto.PriceResponseDTO;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PriceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    void testCase1_10AM_14June2020_Product35455_Brand1() {
        // Given
        String url = createURLWithPort("/api/v1/prices") +
                "?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1";

        // When
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(url, PriceResponseDTO.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        PriceResponseDTO priceResponse = response.getBody();
        assertEquals(35455L, priceResponse.getProductId());
        assertEquals(1L, priceResponse.getBrandId());
        assertEquals(1, priceResponse.getPriceList());
        assertEquals(new BigDecimal("35.50"), priceResponse.getPrice());
        assertEquals("EUR", priceResponse.getCurrency());
    }

    @Test
    void testCase2_4PM_14June2020_Product35455_Brand1() {
        // Given
        String url = createURLWithPort("/api/v1/prices") +
                "?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1";

        // When
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(url, PriceResponseDTO.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        PriceResponseDTO priceResponse = response.getBody();
        assertEquals(35455L, priceResponse.getProductId());
        assertEquals(1L, priceResponse.getBrandId());
        assertEquals(2, priceResponse.getPriceList());
        assertEquals(new BigDecimal("25.45"), priceResponse.getPrice());
        assertEquals("EUR", priceResponse.getCurrency());
    }

    @Test
    void testCase3_9PM_14June2020_Product35455_Brand1() {
        // Given
        String url = createURLWithPort("/api/v1/prices") +
                "?applicationDate=2020-06-14T21:00:00&productId=35455&brandId=1";

        // When
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(url, PriceResponseDTO.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        PriceResponseDTO priceResponse = response.getBody();
        assertEquals(35455L, priceResponse.getProductId());
        assertEquals(1L, priceResponse.getBrandId());
        assertEquals(1, priceResponse.getPriceList());
        assertEquals(new BigDecimal("35.50"), priceResponse.getPrice());
        assertEquals("EUR", priceResponse.getCurrency());
    }

    @Test
    void testCase4_10AM_15June2020_Product35455_Brand1() {
        // Given
        String url = createURLWithPort("/api/v1/prices") +
                "?applicationDate=2020-06-15T10:00:00&productId=35455&brandId=1";

        // When
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(url, PriceResponseDTO.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        PriceResponseDTO priceResponse = response.getBody();
        assertEquals(35455L, priceResponse.getProductId());
        assertEquals(1L, priceResponse.getBrandId());
        assertEquals(3, priceResponse.getPriceList());
        assertEquals(new BigDecimal("30.50"), priceResponse.getPrice());
        assertEquals("EUR", priceResponse.getCurrency());
    }

    @Test
    void testCase5_9PM_16June2020_Product35455_Brand1() {
        // Given
        String url = createURLWithPort("/api/v1/prices") +
                "?applicationDate=2020-06-16T21:00:00&productId=35455&brandId=1";

        // When
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(url, PriceResponseDTO.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        PriceResponseDTO priceResponse = response.getBody();
        assertEquals(35455L, priceResponse.getProductId());
        assertEquals(1L, priceResponse.getBrandId());
        assertEquals(4, priceResponse.getPriceList());
        assertEquals(new BigDecimal("38.95"), priceResponse.getPrice());
        assertEquals("EUR", priceResponse.getCurrency());
    }

    @Test
    void shouldReturnNotFoundWhenNoApplicablePrice() {
        // Given - Fecha fuera del rango de cualquier precio
        String url = createURLWithPort("/api/v1/prices") +
                "?applicationDate=2025-01-01T10:00:00&productId=35455&brandId=1";

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundWhenProductNotExists() {
        // Given
        String url = createURLWithPort("/api/v1/prices") +
                "?applicationDate=2020-06-14T10:00:00&productId=99999&brandId=1";

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundWhenBrandNotExists() {
        // Given
        String url = createURLWithPort("/api/v1/prices") +
                "?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=99";

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenMissingParameters() {
        // Given
        String url = createURLWithPort("/api/v1/prices") +
                "?applicationDate=2020-06-14T10:00:00&productId=35455";

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidDateFormat() {
        // Given
        String url = createURLWithPort("/api/v1/prices") +
                "?applicationDate=invalid-date&productId=35455&brandId=1";

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidProductId() {
        // Given
        String url = createURLWithPort("/api/v1/prices") +
                "?applicationDate=2020-06-14T10:00:00&productId=invalid&brandId=1";

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}