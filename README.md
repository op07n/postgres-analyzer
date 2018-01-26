# postgres-dpa-analyzer
# Create postgres db 'analyzer'
```
spring.datasource.url=jdbc:postgresql://localhost:5432/analyzer
spring.datasource.username=postgres
spring.datasource.password=postgres
```

# Build project (skip tests - optional)
mvn clean install -DskipTests=true

# Start postgres analyzer, optionally add for debug
```bash
-Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```
# Note slashes backward for windows, normal for unix
```bash
mvn spring-boot:run -Dserver.port=8088 -Dinstance.conf=src\main\resources\application.properties -Dlog4j.configuration=file:conf\log4j.properties 
```

# Send analyse request
```bash
$ curl -H "Content-Type: application/json" -X POST -d "[\"tableName1\",\"tableName2\"]" http://localhost:8088/api/v1/analyze
```
# response
```
{"status":"SUCCESS","statusCode":0,"successful":true}
```