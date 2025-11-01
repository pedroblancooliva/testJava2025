package com.inditex.testJava2025.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.inditex.testJava2025.entity.Price;

public interface PriceService {

	Optional<Price> getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);

}
