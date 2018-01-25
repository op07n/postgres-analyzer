# Build project (skip tests - optional)
mvn clean install -DskipTests=true

# Start postgres analyzer
```bash
mvn spring-boot:run -Dserver.port=8081 -Dinstance.conf=file:src/main/resources/application.properties -Dlog4j.configuration=file:conf/log4j.properties
```
# Create postgres db 'analyzer'
```
spring.datasource.url=jdbc:postgresql://localhost:5432/analyzer
spring.datasource.username=postgres
spring.datasource.password=postgres
```