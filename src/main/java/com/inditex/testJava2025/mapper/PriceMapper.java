package com.inditex.testJava2025.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.inditex.testJava2025.dto.PriceResponseDTO;
import com.inditex.testJava2025.entity.Price;

@Mapper(componentModel = "spring") 
public interface PriceMapper {
    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);

    /**
     * Convierte una entidad Price a un DTO de respuesta
     */
    public PriceResponseDTO toResponseDTO(Price price);
    
}