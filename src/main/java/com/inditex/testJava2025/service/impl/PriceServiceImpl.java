package com.inditex.testJava2025.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inditex.testJava2025.dto.PriceResponseDTO;
import com.inditex.testJava2025.entity.Price;
import com.inditex.testJava2025.mapper.PriceMapper;
import com.inditex.testJava2025.repository.PriceRepository;
import com.inditex.testJava2025.service.PriceService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    private PriceRepository priceRepository;
    private PriceMapper priceMapper;

    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    /**
     * Returns the price that applies for a given product and brand on a specific
     * date.
     * If there are several valid prices, the one with the highest priority is
     * returned.
     *
     * Devuelve el precio que se deba aplicar para un producto y una marca
     * determinados en una fecha específica,
     * Si hay varios precios válidos, se devuelve el que tiene la prioridad más
     * alta.
     *
     * @param applicationDate the date for which the price should be applied / fecha
     *                        de aplicación
     * @param productId       the ID of the product / ID del producto
     * @param brandId         the ID of the brand / ID de la marca
     * @return an Optional containing the effective Price / un Optional con el
     *         precio efectivo,
     * 
     */
    @Override
    public PriceResponseDTO getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        List<Price> prices = priceRepository.findApplicablePrices(brandId, productId, applicationDate);

        // Se recoge el de mayor prioridad.
        Optional<Price> selectedPrice = prices.stream()
                .max(Comparator.comparingInt(Price::getPriority));

        // Verificar que se encontró un precio
        Price price = selectedPrice.orElseThrow(() -> new com.inditex.testJava2025.exceptions.PriceNotFoundException(
                "No se encontró precio aplicable para producto "
                        + productId + " de marca " + brandId
                        + " en fecha " + applicationDate));

        // Convertir a DTO de respuesta
        return priceMapper.toResponseDTO(price);

    }
}
