package com.inditex.testJava2025.controller;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@RestController
@RequestMapping("/api/prices")
public class PriceController {
	
    private PriceService priceService;

    private static final Logger logger = LogManager.getLogger(PriceController.class);
    
    @Autowired
    public PriceController(PriceService priceService) {
		super();
		this.priceService = priceService;
	}


	@Tag(name="Get Price")
	@Operation(summary = "Returns the price that applies to a given product and brand on the specified date, based on business rules and priority.")
	@GetMapping(value = "/v1/getApplicablePrice")    
	@ResponseBody
    public ResponseEntity<?> getApplicablePrice(
			@RequestParam("applicationDate")
			@Parameter(description = "Fecha de aplicación del precio (formato: yyyy-MM-ddTHH:mm:ss)", example = "2020-06-14T10:00:00", required = true)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull(message = "La fecha de aplicación es obligatoria") 
			String applicationDate,
			
			@RequestParam("productId")
			@Parameter(description = "Identificador único del producto", example = "35455", required = true)
			@NotNull(message = "El ID del producto es obligatorio") 
			@Positive(message = "El ID del producto debe ser positivo") 
			Long productId,
			
			@RequestParam("brandId") 
			@Parameter(description = "Identificador de la marca (cadena)", example = "1", required = true) 
			@NotNull(message = "El ID de la marca es obligatorio")
			@Positive(message = "El ID de la marca debe ser positivo") Long brandId) {

		logger.debug("Petición recibida: applicationDate={}, productId={}, brandId={}", applicationDate, productId,
				brandId);

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime date = LocalDateTime.parse(applicationDate, inputFormatter);

		return priceService.getApplicablePrice(date, productId, brandId).map(price -> {
			Map<String, Object> response = Map.of("productId", price.getProductId(), "brandId", price.getBrandId(),
					"priceList", price.getPriceList(), "startDate", price.getStartDate(), "endDate", price.getEndDate(),
					"price", price.getPrice(), "currency", price.getCurrency());
			
			logger.debug("Respuesta generada: {}", response);
			return ResponseEntity.ok(response);
		}).orElseGet(() -> {
			logger.debug("No applicable price was found for the parameters received.");
			return ResponseEntity.notFound().build();
		});

    }
}
