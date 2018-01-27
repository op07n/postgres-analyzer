package com.hackaton.controllers;

import com.hackaton.dao.ColumnDaoService;
import com.hackaton.SchemaCompareService;
import com.hackaton.dao.RowDaoService;
import com.hackaton.dao.TableSchema;
import com.hackaton.data.JSONReader;
import com.hackaton.data.Tables;
import com.hackaton.response.AnalysisResponseRoot;
import com.hackaton.response.OperationStatus;
import com.hackaton.response.TableSchemaAnalysisResult;
import io.atlassian.fugue.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class AnalyzerController {

    private final JSONReader<Tables> jsonReader;

    @Autowired
    private ColumnDaoService columnDaoService;

    @Autowired
    private RowDaoService rowDaoService;

    @Autowired
    private SchemaCompareService schemaCompareService;

    public AnalyzerController() {
        this.jsonReader = new JSONReader<>(Tables.class);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/api/v1/gatherDataForAnalysis", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public AnalysisResponseRoot gatherDataForAnalysis(@RequestBody String body) {
        try {
            return gatherTablesSchemas(body);
        } catch (Exception e) {
            log.error("Can't parse input messages", e);
            return AnalysisResponseRoot.error(OperationStatus.PARSE_EXCEPTION, e.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/api/v1/analyze", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public AnalysisResponseRoot performAnalysis(@RequestParam(value = "analysisId") String analysisId) {
        try {
            return performTableSchemaAnalysis(analysisId);
        } catch (Exception e) {
            log.error("Can't perform table schema analysis for data gathered for analysisId {}", analysisId, e);
            return AnalysisResponseRoot.error(OperationStatus.INTERNAL_ERROR, e.getMessage());
        }
    }

    private AnalysisResponseRoot performTableSchemaAnalysis(String oldAnalysisId) {
        log.info("perform table schema analysis for data gathered for analysisId {}.", oldAnalysisId);
        String newAnalysisId = schemaCompareService.generateRandomID();
        List<TableSchema> oldTableSchemas = schemaCompareService.getSchemasForAnalysis(oldAnalysisId);
        List<String> tableNames = oldTableSchemas.stream().map(TableSchema::getTableName).collect(Collectors.toList());

        Either<AnalysisResponseRoot, List<TableSchema>> result = gatherSchema(tableNames);
        if (result.isLeft()) {
            return result.left().get();
        }
        List<TableSchema> newTableSchemas = result.right().get();
        schemaCompareService.saveSchemas(newAnalysisId, newTableSchemas);
        Map<String, TableSchema> newTableSchemaMap = newTableSchemas.stream().collect(Collectors.toMap(TableSchema::getTableName, s -> s ));

        List<TableSchemaAnalysisResult> analysisResults = new ArrayList<>();

        for (TableSchema oldTableSchema : oldTableSchemas) {
            TableSchema newTableSchema = newTableSchemaMap.get(oldTableSchema.getTableName());
            TableSchemaAnalysisResult schemaAnalysisResult = schemaCompareService.performSchemaAnalysis(oldTableSchema, newTableSchema);
            log.info("Analysis result for table {}: {}", oldTableSchema.getTableName(), schemaAnalysisResult);
            analysisResults.add(schemaAnalysisResult);
        }

        return AnalysisResponseRoot.success(newAnalysisId, analysisResults);
    }

    private AnalysisResponseRoot gatherTablesSchemas(String body) throws Exception {
        Tables tables = jsonReader.readJSON(body);
        List<String> tableNames = tables.getValues();
        log.info("Got request to gather tables {} schemas.", tableNames);
        String analysisId = schemaCompareService.generateRandomID();

        Either<AnalysisResponseRoot, List<TableSchema>> result = gatherSchema(tableNames);
        if (result.isLeft()) {
            return result.left().get();
        }
        List<TableSchema> tableSchemas = result.right().get();
        schemaCompareService.saveSchemas(analysisId, tableSchemas);

        return AnalysisResponseRoot.success(analysisId);
    }

    private Either<AnalysisResponseRoot, List<TableSchema>> gatherSchema(List<String> tableNames) {
        List<TableSchema> tableSchemas = new ArrayList<>(tableNames.size());

        for(String tableName: tableNames) {
            int tableVersion = schemaCompareService.getAndIncCurrentTableVersion(tableName);
            Optional<TableSchema> tableSchemaOptional = columnDaoService.streamColumns(tableVersion, tableName);
            TableSchema schema = tableSchemaOptional.get();
            if (!tableSchemaOptional.isPresent()) {
                log.error("Failed to fetch schema for table: {}", tableName);
                return Either.left(AnalysisResponseRoot.error(OperationStatus.INTERNAL_ERROR, "Failed to fetch schema for table: " + tableName));
            }
            Optional<TableSchema> tableSchemaOptionalRows = rowDaoService.streamRows(tableName, schema);
            if (!tableSchemaOptionalRows.isPresent()) {
                log.error("Failed to fetch rows for table: {}", tableName);
                return Either.left(AnalysisResponseRoot.error(OperationStatus.INTERNAL_ERROR, "Failed to fetch rows for table: " + tableName));
            }
            log.info("table {} schema: {}", tableName, schema);
            log.info("table {} schema rows: {}", tableName, tableSchemaOptionalRows.get());
            tableSchemas.add(schema);
        }
        return Either.right(tableSchemas);
    }

}
