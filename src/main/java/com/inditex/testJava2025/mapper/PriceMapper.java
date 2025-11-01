package com.inditex.testJava2025.mapper;

import org.mapstruct.Mapper;

import com.inditex.testJava2025.dto.PriceResponseDTO;
import com.inditex.testJava2025.entity.Price;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    /**
     * Convierte una entidad Price a un DTO de respuesta
     */
    public PriceResponseDTO toResponseDTO(Price price);
    
}