package com.inditex.testJava2025.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inditex.testJava2025.entity.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        Integer productId, Integer brandId, LocalDateTime date1, LocalDateTime date2);
}
