# 👨‍💻 Guía para Desarrolladores

## 🚀 Setup Rápido del Entorno

### Prerrequisitos Verificados
```bash
# Verificar Java 11
java -version

# Verificar Maven
mvn -version

# Verificar Git
git --version
```

### Comandos Esenciales
```bash
# Setup completo
git clone https://github.com/pedroblancooliva/testJava2025.git
cd testJava2025
mvn clean compile
mvn test
mvn spring-boot:run

# Verificación rápida
curl "http://localhost:8080/api/v1/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

## 🔧 Herramientas de Desarrollo

### IDEs Recomendados
- **IntelliJ IDEA**: Configuración automática Spring Boot
- **VS Code**: Con extensión Java Pack
- **Eclipse**: Con Spring Tools Suite

### Extensiones Útiles
```json
// VS Code settings.json
{
    "java.test.config": {
        "vmArgs": ["-ea"]
    },
    "spring-boot.ls.problem.application-properties.enabled": true
}
```

### Maven Profiles
```bash
# Profile de desarrollo
mvn spring-boot:run -Pdev

# Profile de testing
mvn test -Ptest

# Profile con debug
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

## 📝 Convenciones de Código

### Naming Conventions
```java
// Classes: PascalCase
public class PriceService {}

// Methods: camelCase
public PriceResponseDTO getApplicablePrice() {}

// Constants: UPPER_SNAKE_CASE
private static final String DEFAULT_CURRENCY = "EUR";

// Variables: camelCase
private LocalDateTime applicationDate;
```

### Package Structure
```
com.inditex.testJava2025.
├── controller.     # REST controllers
├── service.        # Business logic interfaces
├── service.impl.   # Business logic implementations  
├── repository.     # Data access interfaces
├── entity.         # JPA entities
├── dto.            # Data transfer objects
├── mapper.         # Object mappers
└── exceptions.     # Custom exceptions
```

## 🧪 Testing Guidelines

### Test Naming Convention
```java
// Format: should[ExpectedBehavior]When[StateUnderTest]
@Test
void shouldReturnPriceWhenValidParametersProvided() {}

@Test  
void shouldThrowExceptionWhenPriceNotFound() {}

@Test
void shouldValidateParametersWhenInvalidDataProvided() {}
```

### Test Data Setup
```java
// Use builder pattern for test data
Price testPrice = Price.builder()
    .id(1L)
    .brandId(1L)
    .productId(35455L)
    .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
    .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
    .priceList(1)
    .priority(0)
    .price(new BigDecimal("35.50"))
    .currency("EUR")
    .build();
```

### Coverage Goals
- **Line Coverage**: > 80%
- **Branch Coverage**: > 70%
- **Method Coverage**: > 90%
- **Class Coverage**: 100%

## 🐛 Debugging

### Logging Configuration
```properties
# application-dev.properties
logging.level.com.inditex.testJava2025=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### H2 Console Access
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: (empty)
```

### Useful Queries
```sql
-- Ver todos los precios
SELECT * FROM prices;

-- Buscar precio específico
SELECT * FROM prices 
WHERE brand_id = 1 AND product_id = 35455 
AND '2020-06-14 10:00:00' BETWEEN start_date AND end_date
ORDER BY priority DESC;

-- Verificar solapamientos
SELECT p1.*, p2.* FROM prices p1, prices p2 
WHERE p1.id != p2.id 
AND p1.brand_id = p2.brand_id 
AND p1.product_id = p2.product_id
AND p1.start_date <= p2.end_date 
AND p1.end_date >= p2.start_date;
```

## 🔄 Workflow de Desarrollo

### Git Flow
```bash
# Crear nueva feature
git checkout -b feature/nueva-funcionalidad

# Commit con mensaje descriptivo
git commit -m "feat: add new price validation logic"

# Push y crear PR
git push origin feature/nueva-funcionalidad
```

### Commit Messages Convention
```
feat: nueva funcionalidad
fix: corrección de bug
docs: documentación
style: formato, sin cambios de código
refactor: refactoring sin cambios funcionales
test: agregar o modificar tests
chore: tareas de mantenimiento
```

### Pre-commit Checklist
- [ ] ✅ Tests pasan: `mvn test`
- [ ] ✅ Cobertura OK: `mvn jacoco:report`
- [ ] ✅ Compile clean: `mvn clean compile`
- [ ] ✅ No warnings: revisar logs
- [ ] ✅ Code style: formato consistente

## 📊 Performance Tips

### JVM Tuning
```bash
# Para desarrollo local
export JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC"

# Para testing
export MAVEN_OPTS="-Xms256m -Xmx512m"
```

### Query Optimization
```java
// Usar proyecciones para datos específicos
@Query("SELECT new com.inditex.testJava2025.dto.PriceResponseDTO(" +
       "p.productId, p.brandId, p.priceList, p.startDate, p.endDate, p.price, p.currency) " +
       "FROM Price p WHERE ...")
List<PriceResponseDTO> findPricesProjection(...);
```

### Cache Configuration
```java
// Futuras mejoras con cache
@Cacheable(value = "prices", unless = "#result == null")
@CacheEvict(value = "prices", allEntries = true)
@CachePut(value = "prices", key = "#price.id")
```

## 🛠️ Troubleshooting

### Problemas Comunes

#### 1. Port Already in Use
```bash
# Matar proceso en puerto 8080
netstat -ano | findstr :8080
taskkill /PID [PID_NUMBER] /F

# O cambiar puerto
mvn spring-boot:run -Dserver.port=8081
```

#### 2. Tests Failing
```bash
# Limpiar y recompilar
mvn clean test

# Test específico
mvn test -Dtest=PriceControllerTest

# Con debug
mvn test -Dmaven.surefire.debug
```

#### 3. H2 Database Issues
```bash
# Reiniciar con BD limpia
mvn clean spring-boot:run

# Verificar data.sql
cat src/main/resources/data.sql
```

#### 4. MapStruct Compilation
```bash
# Forzar regeneración de mappers
mvn clean compile

# Verificar generated sources
ls target/generated-sources/annotations/
```

## 📚 Referencias Útiles

### Documentation Links
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/2.6.3/reference/htmlsingle/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/2.6.x/reference/html/)
- [MapStruct Documentation](https://mapstruct.org/documentation/stable/reference/html/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

### Useful Commands Reference
```bash
# Maven
mvn dependency:tree          # Ver dependencias
mvn dependency:analyze       # Analizar dependencias
mvn help:effective-pom       # Ver POM efectivo
mvn site                     # Generar site con reportes

# Spring Boot
mvn spring-boot:build-info   # Info de build
mvn spring-boot:run -Dspring.profiles.active=dev

# Testing
mvn test -Dtest="**/*Integration*"  # Solo tests de integración
mvn test -DfailIfNoTests=false      # No fallar si no hay tests
```

---
📝 **Nota**: Esta guía se actualiza continuamente. Para dudas específicas, revisar el código fuente o crear un issue en el repositorio.