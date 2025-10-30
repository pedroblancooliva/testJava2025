package com.inditex.testJava2025.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PriceResponseDTO {
	
		private int productId;
	    private Integer brandId;
	    private int priceList;
	    private LocalDateTime startDate;
	    private LocalDateTime endDate;
	    private BigDecimal price;
	    
}
