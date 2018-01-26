package com.hackaton.controllers;

import com.hackaton.dao.ColumnDaoService;
import com.hackaton.dao.TableSchema;
import com.hackaton.data.JSONReader;
import com.hackaton.data.Tables;
import com.hackaton.response.AnalysisResponseRoot;
import com.hackaton.response.OperationStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class AnalyzerController {

    private final JSONReader<Tables> jsonReader;

    @Autowired
    private ColumnDaoService columnDaoService;

    public AnalyzerController() {
        this.jsonReader = new JSONReader<>(Tables.class);
    }

    @RequestMapping(value = "/api/v1/analyze", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public AnalysisResponseRoot analyze(@RequestBody String body) {
        try {
            return processTables(body);
        } catch (Exception e) {
            log.error("Can't parse input messages", e);
            return AnalysisResponseRoot.error(OperationStatus.PARSE_EXCEPTION, e.getMessage());
        }
    }

    private AnalysisResponseRoot processTables(@RequestBody String body) throws Exception {
        Tables tables = jsonReader.readJSON(body);
        List<String> tableNames = tables.getValues();
        log.info("Got request to analyze tables: {}", tableNames);
       //todo something
        for(String tableName: tableNames) {
            Optional<TableSchema> tableSchemaOptional = columnDaoService.streamColumns(tableName);
            if (!tableSchemaOptional.isPresent()) {
                log.error("Failed to fetch schema for table: {}", tableName);
               return AnalysisResponseRoot.error(OperationStatus.INTERNAL_ERROR, "Failed to fetch schema for table: " + tableName);
            }
            log.info("table {} schema: {}", tableName, tableSchemaOptional.get());
        }

        return AnalysisResponseRoot.success();
    }
}
