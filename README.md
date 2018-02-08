# postgres-dpa-analyzer
# Create postgres db 'analyzer'
```
spring.datasource.url=jdbc:postgresql://localhost:5433/analyzer
spring.datasource.username=postgres
spring.datasource.password=postgres
```

# Build project (skip tests - optional)
```
mvn clean install -DskipTests=true
```

# Start postgres analyzer, optionally add for debug
```bash
-Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```
## Note slashes backward for windows, normal for unix
## Windows:
```bash
mvn spring-boot:run -Dserver.port=8088 -Dinstance.conf=src\main\resources\application.properties -Dlog4j.configuration=file:conf\log4j.properties
```
## Unix:
```bash
mvn spring-boot:run -Dserver.port=8088 -Dinstance.conf=src/main/resources/application.properties -Dlog4j.configuration=file:conf/log4j.properties
```
## Get table names:
```bash
curl -H "Content-Type: application/json" -X GET  http://localhost:8088/api/v1/getTables?schema=public
```
# response
```bash
{"status":"SUCCESS","statusCode":0,"data":["schema_version","messages"],"successful":true}
```
# How to use Postgres DPA Analyzer
# Step 0. Set db configuration, and check connection
```bash
curl -H "Content-Type: application/json" -X POST -d "{\"dbUrl\":\"jdbc:postgresql://localhost:5432/messages\",\"username\":\"postgres\",\"password\":\"postgres\",\"schemaName\":\"public\"}" http://localhost:8088/api/v1/connectToDB
```
# Responses
```
{
  "status": "INTERNAL_ERROR",
  "error": "Could not get JDBC Connection; nested exception is org.postgresql.util.PSQLException: Connection refused. Check that the hostname and port are correct and that the postmaster is accepting TCP\/IP connections.",
  "statusCode": 99,
  "successful": false
}
```
```
{"status":"SUCCESS","statusCode":0,"successful":true}
```
# Step 1. Send gather data for analysis request (Saves information about current tables schema)
```bash
$ curl -H "Content-Type: application/json" -X POST -d "[\"messages\",\"tableName2\"]" http://localhost:8088/api/v1/gatherDataForAnalysis
```
# response
```
{"status":"SUCCESS","analysisId":"IpKvs","statusCode":0,"successful":true}
```
# Step 2. Do smth with tables: perform upgrade or smth
# Step 3. Send perform schema analysis request (performs tables schemas diff with data saved on previous step)
```bash
$ curl -H "Content-Type: application/json" -X POST  http://localhost:8088/api/v1/analyze?analysisId=AiGeJ
```
# response
```
{
  "status": "SUCCESS",
  "analysisId": "j1KIj",
  "statusCode": 0,
  "data": [
    {
      "oldVersion": 0,
      "newVersion": 1,
      "tableName": "messages",
      "schemaUpdateStatus": "UPDATED",
      "columnChanges": [
        {
          "name": "created",
          "action": "TYPE_CHANGED",
          "oldType": "text",
          "newType": "character varying"
        },
        {
          "name": "client_received",
          "action": "DELETED",
          "oldType": "text"
        }
      ]
    }
  ],
  "successful": true
}
```
