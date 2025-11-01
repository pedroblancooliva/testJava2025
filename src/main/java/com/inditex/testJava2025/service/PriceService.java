package com.inditex.testJava2025.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.inditex.testJava2025.dto.PriceResponseDTO;
import com.inditex.testJava2025.entity.Price;

public interface PriceService {

	Optional<PriceResponseDTO> getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);

}
