package com.inditex.testJava2025.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inditex.testJava2025.entity.Price;
import com.inditex.testJava2025.repository.PriceRepository;
import com.inditex.testJava2025.service.PriceService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService{
    
    private PriceRepository priceRepository;
    
    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository) {
			this.priceRepository = priceRepository;
	}

	@Override
    public Optional<Price> getApplicablePrice(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        List<Price> prices = priceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            productId, brandId, applicationDate, applicationDate);

        return prices.stream().max(Comparator.comparingInt(Price::getPriority));
    }
}
