package com.inditex.testJava2025.service;

import java.time.LocalDateTime;

import com.inditex.testJava2025.dto.PriceResponseDTO;

public interface PriceService {

	PriceResponseDTO getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);

}
