# Sistema de Precios - Arquitectura DDD

Servicio de consulta de precios desarrollado con Spring Boot siguiendo los principios de Domain Driven Design.

## Descripción del Proyecto

Este sistema permite consultar el precio aplicable de un producto para una marca específica en una fecha determinada. La arquitectura está diseñada para separar claramente las responsabilidades de negocio de los aspectos técnicos.

### Estructura del Proyecto

```
src/main/java/com/inditex/testJava2025/
├── domain/                       # Lógica de Negocio
│   ├── model/                    # Entidades del Dominio
│   ├── valueobject/             # Objetos de Valor
│   ├── service/                 # Servicios de Dominio
│   └── repository/              # Contratos de Repositorio
├── application/                 # Casos de Uso
│   ├── usecase/                 # Casos de Uso
│   └── dto/                     # DTOs de Aplicación
├── infrastructure/              # Detalles Técnicos
│   ├── persistence/             # Persistencia de Datos
│   │   ├── entity/              # Entidades JPA
│   │   └── repository/          # Implementaciones de Repositorio
│   ├── mapper/                  # Mapeadores
│   └── config/                  # Configuración
└── presentation/                # API REST
    ├── controller/              # Controladores
    └── dto/                     # DTOs de Respuesta
```

### Capas de la Arquitectura

#### Capa de Dominio
Contiene toda la lógica de negocio pura, independiente de cualquier framework o tecnología:
- **Entities**: `Price` con las reglas de negocio principales
- **Value Objects**: `ProductId`, `BrandId`, `Money`, `DateRange`, `Priority`
- **Domain Services**: `PriceDomainService` para lógica compleja
- **Repository Interfaces**: Contratos que define el dominio

#### Capa de Aplicación
Orquesta los casos de uso de la aplicación:
- **Use Cases**: `FindApplicablePriceUseCase` coordina las operaciones
- **DTOs**: Transferencia de datos entre capas

#### Capa de Infraestructura
Implementa los aspectos técnicos:
- **Persistence**: Entidades JPA y repositorios
- **Mappers**: Conversión entre modelos de dominio e infraestructura
- **Configuration**: Inyección de dependencias

#### Capa de Presentación
Expone la funcionalidad a través de API REST:
- **Controllers**: Endpoints HTTP
- **DTOs**: Modelos de request/response

## Características Principales

- **Arquitectura Limpia**: Separación clara entre lógica de negocio y aspectos técnicos
- **Diseño Dirigido por el Dominio**: Modelo rico que encapsula las reglas de negocio
- **Objetos de Valor Inmutables**: Garantizan la consistencia de los datos
- **Patrón Repository**: Abstracción del acceso a datos
- **Inversión de Dependencias**: El dominio no depende de la infraestructura

## Flujo de Datos

```
Petición HTTP → Controlador → Caso de Uso → Servicio de Dominio → Repositorio → Entidad
```

## Tecnologías Utilizadas

- Java 11
- Spring Boot 2.6.3
- Spring Data JPA
- Base de datos H2
- Maven
- Log4j2
- OpenAPI/Swagger

## Reglas de Negocio

1. Buscar el precio aplicable para un producto, marca y fecha específicos
2. En caso de múltiples coincidencias, devolver el de mayor prioridad
3. Validar rangos de fechas y restricciones de negocio
4. Manejar operaciones monetarias con validación de moneda

## Ejecución

Para ejecutar la aplicación:

```bash
mvn spring-boot:run
```

## Documentación de la API

La documentación interactiva está disponible en: `http://localhost:8080/swagger-ui.html`

### Ejemplo de Consulta

```
GET /api/prices/v1/getApplicablePrice?applicationDate=14/06/2020 10:00:00&productId=35455&brandId=1
```

## Pruebas y Cobertura

### Ejecución de Tests

Para ejecutar las pruebas unitarias:
```bash
mvn test
```

Para ejecutar todas las pruebas (unitarias e integración):
```bash
mvn verify
```

### Análisis de Cobertura con JaCoCo

Se ha implementado **JaCoCo** para análisis detallado de cobertura de código:

#### Ejecutar Tests con Cobertura
```bash
mvn clean test jacoco:report
```

#### Ejecutar Launcher de Cobertura (Windows)
```bash
run-coverage.bat
```

#### Reportes Generados
- **HTML**: `target/site/jacoco/index.html` (abrir en navegador)
- **CSV**: `target/site/jacoco/jacoco.csv`
- **XML**: `target/site/jacoco/jacoco.xml`

#### Métricas Actuales
- **Cobertura total de líneas**: 89%
- **Cobertura de instrucciones**: 93%
- **Cobertura de métodos**: 94%
- **Cobertura de clases**: 100%

Ver [`JACOCO_COVERAGE_REPORT.md`](JACOCO_COVERAGE_REPORT.md) para análisis detallado.

### Suite de Tests

#### Tests Unitarios
- **PriceRepositoryTest**: Tests de la capa de persistencia
- **PriceServiceImplTest**: Tests de la lógica de servicio
- **PriceControllerTest**: Tests del controlador (con mocks)

#### Tests de Integración
- **PriceControllerIntegrationTest**: Tests end-to-end completos
- **PriceIntegrationTest**: Tests de integración con base de datos

#### Casos de Test Específicos
1. **Test a las 10:00 del día 14**: productId=35455, brandId=1 → Precio 35.50€
2. **Test a las 16:00 del día 14**: productId=35455, brandId=1 → Precio 25.45€  
3. **Test a las 10:00 del día 14**: productId=35455, brandId=1 → Precio 35.50€
4. **Test a las 21:00 del día 14**: productId=35455, brandId=1 → Precio 35.50€
5. **Test a las 10:00 del día 15**: productId=35455, brandId=1 → Precio 38.95€

## Ventajas de esta Arquitectura

- **Mantenibilidad**: Separación clara de responsabilidades
- **Testabilidad**: Fácil testing unitario de la lógica de negocio
- **Flexibilidad**: Cambios en infraestructura sin afectar el negocio
- **Escalabilidad**: Estructura preparada para futuras mejoras
- **Enfoque en el Dominio**: La lógica de negocio está protegida y bien expresada 
