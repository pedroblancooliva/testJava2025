# Sistema de Precios - Inditex Test 2025

Servicio REST de consulta de precios desarrollado con Spring Boot y arquitectura por capas.

## 📋 Descripción del Proyecto

Este sistema permite consultar el precio aplicable de un producto para una marca específica en una fecha determinada. La aplicación implementa lógica de priorización de precios y validación de rangos temporales.

### 🏗️ Estructura del Proyecto

```
src/main/java/com/inditex/testJava2025/
├── controller/                   # Capa de Presentación
│   └── PriceController.java      # Controlador REST para precios
├── service/                      # Capa de Lógica de Negocio
│   ├── PriceService.java         # Interface del servicio
│   └── impl/
│       └── PriceServiceImpl.java # Implementación del servicio
├── repository/                   # Capa de Acceso a Datos
│   └── PriceRepository.java      # Repositorio JPA para precios
├── entity/                       # Entidades de Persistencia
│   └── Price.java                # Entidad JPA Price
├── dto/                          # Objetos de Transferencia
│   └── PriceResponseDTO.java     # DTO de respuesta
├── mapper/                       # Mapeadores de Objetos
│   └── PriceMapper.java          # Mapper Price → DTO (MapStruct)
├── exceptions/                   # Excepciones Personalizadas
│   └── PriceNotFoundException.java # Excepción de precio no encontrado
└── TestJava2025Application.java  # Clase principal Spring Boot

src/main/resources/
├── application.properties        # Configuración de la aplicación
├── data.sql                     # Datos de prueba iniciales
└── log4j2.properties           # Configuración de logging

src/test/java/                   # Suite de Tests
├── controller/
│   ├── PriceControllerTest.java          # Tests unitarios del controlador
│   └── PriceControllerIntegrationTest.java # Tests de integración
├── service/impl/
│   └── PriceServiceImplTest.java         # Tests unitarios del servicio
├── repository/
│   └── PriceRepositoryTest.java          # Tests del repositorio
├── integration/
│   └── PriceIntegrationTest.java         # Tests de integración completos
└── TestJava2025ApplicationTests.java     # Tests de contexto
```

### 🔧 Arquitectura por Capas

#### 🎮 Capa de Presentación (Controller)
- **PriceController**: Expone endpoints REST
- **ErrorResponse**: Manejo centralizado de errores
- **Validación de parámetros**: Validación automática de requests

#### 💼 Capa de Lógica de Negocio (Service)
- **PriceService**: Interface que define contratos del servicio
- **PriceServiceImpl**: Implementa lógica de búsqueda y priorización
- **Reglas de negocio**: Selección por prioridad y validación temporal

#### 🗃️ Capa de Acceso a Datos (Repository)
- **PriceRepository**: Interface JPA con queries personalizadas
- **Query Methods**: Búsqueda por criterios específicos
- **Ordenación**: Por prioridad descendente automática

#### 📦 Capa de Entidades (Entity)
- **Price**: Entidad JPA con mapping a tabla `prices`
- **Anotaciones JPA**: Configuración de persistencia
- **Validaciones**: Constraints de base de datos

#### 🔄 Capa de Mapeo (Mapper)
- **PriceMapper**: Conversión Entity ↔ DTO con MapStruct
- **Mapeo automático**: Generación de código en tiempo de compilación
- **Configuración personalizada**: Manejo de tipos específicos

## 🌟 Características Principales

- **API REST**: Endpoint único para consulta de precios
- **Arquitectura por Capas**: Separación clara de responsabilidades
- **MapStruct**: Mapeo automático entre entidades y DTOs
- **Validación de Datos**: Validación automática de parámetros de entrada
- **Manejo de Errores**: Gestión centralizada de excepciones
- **Base de Datos H2**: Base de datos en memoria para desarrollo y testing
- **Logging**: Sistema de logs configurado con Log4j2
- **Documentación API**: Swagger/OpenAPI integrado
- **Tests Comprehensivos**: Suite completa de tests unitarios e integración
- **Análisis de Cobertura**: JaCoCo configurado para análisis de cobertura

## 🔄 Flujo de Operación

```
HTTP Request → Controller → Service → Repository → Database
                ↓              ↓           ↓
            Validation → Business Logic → Data Access
                ↓              ↓           ↓
           Error Handling → Mapping → Query Execution
                ↓              ↓           ↓
            HTTP Response ← DTO ← Entity
```

## 🚀 Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Java** | 11 | Lenguaje de programación |
| **Spring Boot** | 2.6.3 | Framework principal |
| **Spring Data JPA** | 2.6.3 | Acceso a datos |
| **Spring Web** | 2.6.3 | API REST |
| **H2 Database** | Runtime | Base de datos en memoria |
| **MapStruct** | 1.5.5.Final | Mapeo de objetos |
| **Maven** | 3.x | Gestión de dependencias |
| **Log4j2** | 2.x | Sistema de logging |
| **SpringDoc OpenAPI** | 1.6.6 | Documentación API |
| **JUnit 5** | 5.8.x | Framework de testing |
| **Mockito** | 4.x | Mocking para tests |
| **JaCoCo** | 0.8.8 | Análisis de cobertura |

## 📋 Modelo de Datos

### Entidad Price

```sql
CREATE TABLE prices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    brand_id BIGINT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price_list INTEGER NOT NULL,
    product_id BIGINT NOT NULL,
    priority INTEGER NOT NULL,
    price DECIMAL(19,2) NOT NULL,
    curr VARCHAR(255) NOT NULL
);
```

### Datos de Prueba

```sql
-- Producto 35455, Marca 1 (ZARA)
INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, curr) VALUES
(1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'),
(1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR'),
(1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR'),
(1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR');
```

## 📊 Reglas de Negocio

1. **Búsqueda de Precios**: Encuentra precios aplicables por producto, marca y fecha
2. **Priorización**: En caso de solapamiento, se selecciona el precio con mayor prioridad
3. **Validación Temporal**: La fecha de aplicación debe estar dentro del rango válido
4. **Respuesta Única**: Siempre devuelve un único precio o error si no existe
5. **Manejo de Errores**: Excepciones específicas para diferentes tipos de error

## 🚀 Instalación y Ejecución

### Prerrequisitos

- Java 11 o superior
- Maven 3.6 o superior
- Git

### Clonar el Repositorio

```bash
git clone https://github.com/pedroblancooliva/testJava2025.git
cd testJava2025
```

### Compilar y Ejecutar

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar tests
mvn test

# Ejecutar la aplicación
mvn spring-boot:run
```

### Acceso a la Aplicación

Una vez iniciada la aplicación:

- **API REST**: `http://localhost:8080`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **Base de Datos H2**: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Usuario: `sa`
  - Password: (vacío)

## 🔌 API REST

### Endpoint Principal

```http
GET /api/v1/prices?applicationDate={date}&productId={id}&brandId={id}
```

#### Parámetros

| Parámetro | Tipo | Obligatorio | Descripción | Ejemplo |
|-----------|------|-------------|-------------|---------|
| `applicationDate` | String | ✅ | Fecha y hora de aplicación | `2020-06-14T10:00:00` |
| `productId` | Long | ✅ | Identificador del producto | `35455` |
| `brandId` | Long | ✅ | Identificador de la marca | `1` |

#### Formatos de Fecha Soportados

- ISO 8601: `2020-06-14T10:00:00`
- Con milisegundos: `2020-06-14T10:00:00.000`
- Con zona horaria: `2020-06-14T10:00:00Z`

### Ejemplos de Uso

#### Petición Exitosa

```bash
curl -X GET "http://localhost:8080/api/v1/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

**Respuesta:**

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

#### Error - Precio No Encontrado

```bash
curl -X GET "http://localhost:8080/api/v1/prices?applicationDate=2025-01-01T10:00:00&productId=35455&brandId=1"
```

**Respuesta:**

```json
{
  "error": "PRICE_NOT_FOUND",
  "message": "No se encontró precio aplicable para producto 35455 de marca 1 en fecha 2025-01-01T10:00"
}
```

#### Error - Parámetros Inválidos

```bash
curl -X GET "http://localhost:8080/api/v1/prices?applicationDate=invalid-date&productId=35455&brandId=1"
```

**Respuesta:**

```json
{
  "error": "VALIDATION_ERROR", 
  "message": "Formato de fecha inválido"
}
```

## Pruebas y Cobertura

## 🧪 Pruebas y Cobertura

### Suite de Tests Completa

El proyecto incluye **59 tests** organizados en múltiples niveles:

#### 📋 Tipos de Tests

| Tipo | Archivo | Descripción | Tests |
|------|---------|-------------|-------|
| **Unitarios** | `PriceControllerTest` | Tests del controlador con mocks | 8 tests |
| **Unitarios** | `PriceServiceImplTest` | Tests de la lógica de negocio | 2 tests |
| **Unitarios** | `PriceRepositoryTest` | Tests del repositorio JPA | 6 tests |
| **Integración** | `PriceControllerIntegrationTest` | Tests end-to-end completos | 13 tests |
| **Integración** | `PriceIntegrationTest` | Tests de integración con BD | 11 tests |
| **Contexto** | `TestJava2025ApplicationTests` | Test de carga de contexto | 1 test |

### 🎯 Casos de Test Específicos (Requerimientos)

Los siguientes casos de test validan los requerimientos específicos:

```bash
# Test 1: día 14 a las 10:00 - productId=35455, brandId=1
# Resultado esperado: priceList=1, price=35.50€

# Test 2: día 14 a las 16:00 - productId=35455, brandId=1  
# Resultado esperado: priceList=2, price=25.45€

# Test 3: día 14 a las 21:00 - productId=35455, brandId=1
# Resultado esperado: priceList=1, price=35.50€

# Test 4: día 15 a las 10:00 - productId=35455, brandId=1
# Resultado esperado: priceList=3, price=30.50€

# Test 5: día 15 a las 21:00 - productId=35455, brandId=1
# Resultado esperado: priceList=4, price=38.95€
```

### 🚀 Comandos de Ejecución

#### Ejecutar Todos los Tests
```bash
mvn test
```

#### Ejecutar Tests con Cobertura
```bash
mvn clean test jacoco:report
```

#### Ejecutar Tests de Integración
```bash
mvn verify
```

#### Launcher Automático (Windows)
```bash
./run-coverage.bat
```

### 📊 Análisis de Cobertura con JaCoCo

#### Métricas Actuales de Cobertura

| Componente | Instrucciones | Branches | Líneas | Métodos | Clases |
|------------|---------------|----------|--------|---------|---------|
| **Controller** | **100%** ✅ | n/a | **100%** ✅ | **100%** ✅ | **100%** ✅ |
| **Service** | **100%** ✅ | n/a | **100%** ✅ | **100%** ✅ | **100%** ✅ |
| **Repository** | **100%** ✅ | n/a | **100%** ✅ | **100%** ✅ | **100%** ✅ |
| **Entity** | **99%** ✅ | 62% ⚠️ | **98%** ✅ | **100%** ✅ | **100%** ✅ |
| **Mapper** | **93%** ✅ | 50% ⚠️ | **90%** ✅ | **100%** ✅ | **100%** ✅ |
| **DTO** | **86%** ✅ | 68% ⚠️ | **77%** ⚠️ | **93%** ✅ | **100%** ✅ |
| **Total** | **93%** ✅ | 64% ⚠️ | **89%** ✅ | **94%** ✅ | **100%** ✅ |

#### Reportes Generados

- **HTML**: `target/site/jacoco/index.html` (navegador)
- **CSV**: `target/site/jacoco/jacoco.csv` (análisis)
- **XML**: `target/site/jacoco/jacoco.xml` (CI/CD)

#### Configuración de Umbrales

- **Líneas**: Mínimo 80% ✅
- **Branches**: Mínimo 70% ⚠️ (actual: 64%)

> 📖 **Análisis detallado**: Ver [`JACOCO_COVERAGE_REPORT.md`](JACOCO_COVERAGE_REPORT.md)

### 🔍 Estrategia de Testing

#### Tests Unitarios
- **Isolation**: Cada componente testado en aislamiento
- **Mocking**: Dependencias mockeadas con Mockito
- **Fast**: Ejecución rápida sin contexto Spring

#### Tests de Integración  
- **Real Context**: Contexto completo de Spring Boot
- **Real Database**: H2 en memoria con datos reales
- **End-to-End**: Desde HTTP request hasta respuesta

#### Validaciones Cubiertas
- ✅ Lógica de priorización de precios
- ✅ Validación de rangos temporales
- ✅ Manejo de errores y excepciones
- ✅ Mapeo entre entidades y DTOs
- ✅ Validación de parámetros HTTP
- ✅ Respuestas HTTP correctas
- ✅ Formato de fechas múltiples
- ✅ Casos edge (datos no encontrados)

## 🏗️ Arquitectura y Patrones

### Patrón Layered Architecture

```
┌─────────────────────────────────────┐
│         Presentation Layer          │
│    (Controllers, Error Handlers)    │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│         Business Layer              │
│      (Services, Business Logic)     │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│       Persistence Layer             │
│    (Repositories, Entities)         │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│         Database Layer              │
│           (H2 Database)             │
└─────────────────────────────────────┘
```

### Patrones Implementados

#### 🎯 Repository Pattern
- **Interface**: `PriceRepository` define contratos
- **Implementation**: Spring Data JPA genera implementación
- **Benefits**: Abstracción del acceso a datos

#### 🔄 DTO Pattern  
- **PriceResponseDTO**: Objeto de transferencia
- **Separation**: Desacopla modelo interno de API
- **Validation**: Validación específica por capa

#### 🗺️ Mapper Pattern
- **MapStruct**: Generación automática de código
- **Type-safe**: Conversiones seguras en tiempo de compilación
- **Performance**: Sin reflexión en runtime

#### 🚨 Exception Handling
- **Global Handler**: `@ControllerAdvice` centralizado
- **Custom Exceptions**: `PriceNotFoundException`
- **Structured Responses**: Formato consistente de errores

### Principios SOLID Aplicados

#### Single Responsibility Principle (SRP)
- Cada clase tiene una única responsabilidad
- Separación clara entre capas

#### Open/Closed Principle (OCP)
- Interfaces permiten extensión sin modificación
- Nuevos casos de uso sin cambiar código existente

#### Liskov Substitution Principle (LSP)
- Implementaciones intercambiables de interfaces
- Contratos bien definidos

#### Interface Segregation Principle (ISP)
- Interfaces específicas y focalizadas
- No dependencias innecesarias

#### Dependency Inversion Principle (DIP)
- Dependencias hacia abstracciones
- Inyección de dependencias con Spring

## 🎯 Decisiones de Diseño

### Base de Datos H2
- **Ventajas**: Rápida, en memoria, ideal para desarrollo
- **Configuración**: Automática con Spring Boot
- **Datos**: Carga inicial con `data.sql`

### MapStruct vs Manual Mapping
- **Elección**: MapStruct para performance y seguridad
- **Beneficios**: Generación automática, type-safe
- **Alternativas**: ModelMapper (reflexión), mapeo manual

### Spring Data JPA
- **Query Methods**: Queries automáticas por nombre de método
- **Custom Queries**: `@Query` para lógica específica
- **Pagination**: Soporte nativo para paginación

### Validación de Parámetros
- **Bean Validation**: Anotaciones estándar
- **Spring Validation**: Integración automática
- **Error Handling**: Respuestas estructuradas

## 📈 Rendimiento y Optimizaciones

### Query Optimization
```sql
-- Índice compuesto sugerido para optimización
CREATE INDEX idx_prices_search 
ON prices (brand_id, product_id, start_date, end_date, priority DESC);
```

### Caching Strategy
```java
// Ejemplo de configuración de cache (futuro)
@Cacheable(value = "prices", key = "#brandId + '_' + #productId + '_' + #date")
public PriceResponseDTO getApplicablePrice(...) {
    // implementación
}
```

### Connection Pool
- **HikariCP**: Pool de conexiones por defecto
- **Configuration**: Optimizada para H2
- **Monitoring**: Métricas disponibles con Actuator

## ⚡ Mejoras Futuras

### Funcionalidades Planeadas
- [ ] Sistema de cache con Redis
- [ ] Paginación en endpoints
- [ ] Filtros avanzados de búsqueda  
- [ ] Versionado de API
- [ ] Rate limiting
- [ ] Métricas con Micrometer
- [ ] Health checks con Actuator

### Optimizaciones Técnicas
- [ ] Profiles específicos (dev, test, prod)
- [ ] Base de datos PostgreSQL en producción
- [ ] Containerización con Docker
- [ ] Pipeline CI/CD
- [ ] Logging estructurado
- [ ] Monitoreo y alertas

## 📞 Información de Contacto

**Autor**: Pedro Blanco Oliva  
**Email**: pedroblancooliva@gmail.com  
**GitHub**: [@pedroblancooliva](https://github.com/pedroblancooliva)  
**Proyecto**: Test Técnico Inditex 2025

## 📄 Licencia

Este proyecto es parte de un test técnico para Inditex y es solo para fines educativos y de evaluación.

---

**Última actualización**: Noviembre 2025  
**Versión**: 1.0.0  
**Java**: 11  
**Spring Boot**: 2.6.3 
