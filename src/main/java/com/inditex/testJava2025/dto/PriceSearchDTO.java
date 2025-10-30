package com.inditex.testJava2025.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PriceSearchDTO {
	
	private Integer productId;
	private Integer brandId;
	private LocalDateTime dateApplication;
	

}
