package com.inditex.testJava2025.controller.swagger;

/**
 * Constantes para documentación de API Swagger/OpenAPI.
 * Centraliza todas las descripciones, ejemplos y mensajes para mantener
 * el código de controladores limpio y readable.
 */
public final class SwaggerConstants {

    private SwaggerConstants() {
        // Utility class
    }

    // Tag de API
    public static final String PRICE_API_TAG = "Price Service";
    public static final String PRICE_API_DESCRIPTION = "API para consultar precios aplicables de productos";

    // Operaciones
    public static final String GET_PRICE_SUMMARY = "Consultar precio aplicable";
    public static final String GET_PRICE_DESCRIPTION = "Obtiene el precio aplicable para un producto de una marca en una fecha específica. " +
            "Si existen múltiples tarifas aplicables, devuelve la de mayor prioridad.";

    // Parámetros de entrada
    public static final String PARAM_APPLICATION_DATE_DESC = "Fecha de aplicación del precio (formato: yyyy-MM-ddTHH:mm:ss)";
    public static final String PARAM_APPLICATION_DATE_EXAMPLE = "2020-06-14T10:00:00";
    
    public static final String PARAM_PRODUCT_ID_DESC = "Identificador único del producto";
    public static final String PARAM_PRODUCT_ID_EXAMPLE = "35455";
    
    public static final String PARAM_BRAND_ID_DESC = "Identificador de la marca (cadena)";
    public static final String PARAM_BRAND_ID_EXAMPLE = "1";
    
    // Mensajes de validación
    public static final String VALIDATION_APPLICATION_DATE_REQUIRED = "La fecha de aplicación es obligatoria";
    public static final String VALIDATION_PRODUCT_ID_REQUIRED = "El ID del producto es obligatorio";
    public static final String VALIDATION_PRODUCT_ID_POSITIVE = "El ID del producto debe ser positivo";
    public static final String VALIDATION_BRAND_ID_REQUIRED = "El ID de la marca es obligatorio";
    public static final String VALIDATION_BRAND_ID_POSITIVE = "El ID de la marca debe ser positivo";

    // Respuestas
    public static final String RESPONSE_200_DESC = "Precio encontrado exitosamente";
    public static final String RESPONSE_404_DESC = "No se encontró precio aplicable para los parámetros especificados";
    public static final String RESPONSE_400_DESC = "Parámetros de entrada inválidos";

    // Ejemplos de respuesta
    public static final String EXAMPLE_SUCCESS_NAME = "Precio encontrado";
    public static final String EXAMPLE_SUCCESS_SUMMARY = "Respuesta exitosa con precio aplicable";
    public static final String EXAMPLE_SUCCESS_VALUE = "{\n" +
            "  \"productId\": 35455,\n" +
            "  \"brandId\": 1,\n" +
            "  \"priceList\": 1,\n" +
            "  \"price\": 35.50,\n" +
            "  \"currency\": \"EUR\"\n" +
            "}";

    public static final String EXAMPLE_NOT_FOUND_NAME = "Precio no encontrado";
    public static final String EXAMPLE_NOT_FOUND_SUMMARY = "Error cuando no existe precio aplicable";
    public static final String EXAMPLE_NOT_FOUND_VALUE = "{\n" +
            "  \"status\": 404,\n" +
            "  \"error\": \"PRICE_NOT_FOUND\",\n" +
            "  \"message\": \"No se encontró precio aplicable para los parámetros especificados\",\n" +
            "  \"timestamp\": \"2025-11-01T16:30:00\"\n" +
            "}";

    public static final String EXAMPLE_BAD_REQUEST_NAME = "Parámetros inválidos";
    public static final String EXAMPLE_BAD_REQUEST_SUMMARY = "Error de validación de parámetros";
    public static final String EXAMPLE_BAD_REQUEST_VALUE = "{\n" +
            "  \"status\": 400,\n" +
            "  \"error\": \"VALIDATION_ERROR\",\n" +
            "  \"message\": \"Los parámetros proporcionados no son válidos\",\n" +
            "  \"timestamp\": \"2025-11-01T16:30:00\"\n" +
            "}";
}