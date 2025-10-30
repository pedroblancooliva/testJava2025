package com.inditex.testJava2025.controller;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.testJava2025.service.PriceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
	
    private PriceService priceService;
    
    @Autowired
    public PriceController(PriceService priceService) {
		super();
		this.priceService = priceService;
	}






	@Tag(name="Get Price")
	@Operation(summary = "Return the price of a product on the specified date")
	@GetMapping(value = "/v1/getPrice")    
	@ResponseBody
    public ResponseEntity<?> getPrice(
    		
        @RequestParam @Parameter(name =  "applicationDate", description  = "Application date ", example = "2025-10-30-13.23.59", required = true)
        			  @DateTimeFormat(pattern = "yyyy-MM-dd-hh.mm.ss") String applicationDate ,
        @RequestParam Integer productId,
        @RequestParam Integer brandId) {

        LocalDateTime date = LocalDateTime.parse(applicationDate, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss"));

        return priceService.getApplicablePrice(date, productId, brandId)
            .map(price -> ResponseEntity.ok(Map.of(
                "productId", price.getProductId(),
                "brandId", price.getBrandId(),
                "priceList", price.getPriceList(),
                "startDate", price.getStartDate(),
                "endDate", price.getEndDate(),
                "price", price.getPrice(),
                "currency", price.getCurrency()
            )))
            .orElse(ResponseEntity.notFound().build());
    }
}
