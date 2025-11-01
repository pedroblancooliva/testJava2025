package com.inditex.testJava2025.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inditex.testJava2025.dto.PriceResponseDTO;
import com.inditex.testJava2025.entity.Price;
import com.inditex.testJava2025.mapper.PriceMapper;
import com.inditex.testJava2025.repository.PriceRepository;
import com.inditex.testJava2025.service.PriceService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    private static final Logger logger = LogManager.getLogger(PriceServiceImpl.class);

    private PriceRepository priceRepository;
    private PriceMapper priceMapper;

    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
        logger.info("PriceServiceImpl inicializado correctamente con repositorio y mapper");
    }

    /**
     * Returns the price that applies for a given product and brand on a specific
     * date.
     * If there are several valid prices, the one with the highest priority is
     * returned.
     *
     * Devuelve el precio que se deba aplicar para un producto y una marca
     * determinados en una fecha específica,
     * Si hay varios precios válidos, se devuelve el que tiene la prioridad más
     * alta.
     *
     * @param applicationDate the date for which the price should be applied / fecha
     *                        de aplicación
     * @param productId       the ID of the product / ID del producto
     * @param brandId         the ID of the brand / ID de la marca
     * @return an Optional containing the effective Price / un Optional con el
     *         precio efectivo,
     * 
     */
    @Override
    public PriceResponseDTO getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        logger.info("Iniciando búsqueda de precio - Fecha: {}, ProductoID: {}, MarcaID: {}", 
            applicationDate, productId, brandId);
        
        // Validación de parámetros de entrada
        if (applicationDate == null || productId == null || brandId == null) {
            logger.error("Parámetros nulos recibidos - Fecha: {}, ProductoID: {}, MarcaID: {}", 
                applicationDate, productId, brandId);
            throw new IllegalArgumentException("Todos los parámetros son obligatorios");
        }
        
        if (productId <= 0 || brandId <= 0) {
            logger.error("IDs inválidos recibidos - ProductoID: {}, MarcaID: {}", productId, brandId);
            throw new IllegalArgumentException("Los IDs deben ser positivos");
        }
        
        try {
            // Búsqueda en repositorio
            logger.debug("Consultando repositorio para precios aplicables - Marca: {}, Producto: {}, Fecha: {}", 
                brandId, productId, applicationDate);
            
            List<Price> prices = priceRepository.findApplicablePrices(brandId, productId, applicationDate);
            
            logger.debug("Encontrados {} precios candidatos", prices.size());
            
            if (prices.isEmpty()) {
                logger.warn("No se encontraron precios aplicables - ProductoID: {}, MarcaID: {}, Fecha: {}", 
                    productId, brandId, applicationDate);
            } else {
                logger.debug("Precios encontrados: {}", 
                    prices.stream()
                        .map(p -> String.format("ID:%d, Lista:%d, Prioridad:%d, Precio:%.2f", 
                            p.getId(), p.getPriceList(), p.getPriority(), p.getPrice()))
                        .toArray());
            }

            // Se recoge el de mayor prioridad.
            Optional<Price> selectedPrice = prices.stream()
                    .max(Comparator.comparingInt(Price::getPriority));

            // Verificar que se encontró un precio
            Price price = selectedPrice.orElseThrow(() -> {
                String errorMsg = "No se encontró precio aplicable para producto "
                        + productId + " de marca " + brandId
                        + " en fecha " + applicationDate;
                logger.error(errorMsg);
                return new com.inditex.testJava2025.exceptions.PriceNotFoundException(errorMsg);
            });

            logger.info("Precio seleccionado - ID: {}, Lista: {}, Prioridad: {}, Precio: {}, Moneda: {}", 
                price.getId(), price.getPriceList(), price.getPriority(), price.getPrice(), price.getCurrency());

            // Convertir a DTO de respuesta
            PriceResponseDTO response = priceMapper.toResponseDTO(price);
            
            logger.info("Conversión a DTO completada exitosamente - ProductoID: {}, MarcaID: {}", 
                productId, brandId);
            
            return response;
            
        } catch (com.inditex.testJava2025.exceptions.PriceNotFoundException e) {
            // Re-lanzar excepción de negocio sin logging adicional (ya loggeada arriba)
            throw e;
        } catch (Exception e) {
            logger.error("Error inesperado al buscar precio - ProductoID: {}, MarcaID: {}, Error: {}", 
                productId, brandId, e.getMessage(), e);
            throw new RuntimeException("Error interno al consultar precio", e);
        }

    }
}
