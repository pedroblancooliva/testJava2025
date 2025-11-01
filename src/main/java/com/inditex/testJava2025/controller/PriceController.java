package com.inditex.testJava2025.controller;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.testJava2025.controller.swagger.ApplicationDateParam;
import com.inditex.testJava2025.controller.swagger.BrandIdParam;
import com.inditex.testJava2025.controller.swagger.GetPriceApiDocumentation;
import com.inditex.testJava2025.controller.swagger.ProductIdParam;
import com.inditex.testJava2025.dto.PriceResponseDTO;
import com.inditex.testJava2025.service.PriceService;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para consultas de precios Expone el endpoint de consulta de
 * precios aplicables
 */
@RestController
@RequestMapping("/api/v1/prices")
@Tag(name = "Price Service", description = "API para consultar precios aplicables de productos")
public class PriceController {

	private final PriceService priceService;

	private static final Logger logger = LogManager.getLogger(PriceController.class);
	
	public PriceController(PriceService priceService) {
		this.priceService = priceService;
		logger.info("PriceController inicializado correctamente");
	}

	/**
	 * Consulta el precio aplicable para un producto de una marca en una fecha
	 * específica
	 * 
	 * @param applicationDate fecha de aplicación del precio (formato:
	 *                        yyyy-MM-dd'T'HH:mm:ss)
	 * @param productId       identificador del producto
	 * @param brandId         identificador de la marca/cadena
	 * @return precio aplicable con mayor prioridad
	 */
	@GetMapping
	@GetPriceApiDocumentation
	public ResponseEntity<PriceResponseDTO> getApplicablePrice(
			@ApplicationDateParam @RequestParam("applicationDate") LocalDateTime applicationDate,
			@ProductIdParam @RequestParam("productId") Long productId,
			@BrandIdParam @RequestParam("brandId") Long brandId) {

		logger.info("Petición recibida - GET /api/v1/prices?applicationDate={}&productId={}&brandId={}", 
			applicationDate, productId, brandId);
		
		long startTime = System.currentTimeMillis();
		
		try {
			PriceResponseDTO response = priceService.getApplicablePrice(applicationDate, productId, brandId);
			
			long duration = System.currentTimeMillis() - startTime;
			logger.info("Petición procesada exitosamente - ProductoID: {}, MarcaID: {}, Lista: {}, Precio: {}, Tiempo: {}ms", 
				productId, brandId, response.getPriceList(), response.getPrice(), duration);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;
			logger.error("Error en petición - ProductoID: {}, MarcaID: {}, Tiempo: {}ms, Error: {}", 
				productId, brandId, duration, e.getMessage());
			throw e; // GlobalExceptionHandler se encarga del resto
		}

	}
}