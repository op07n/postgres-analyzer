package com.hackaton.controllers;

import com.hackaton.data.JSONReader;
import com.hackaton.data.Tables;
import com.hackaton.response.AnalysisResponseRoot;
import com.hackaton.response.OperationStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AnalyzerController {

    private final JSONReader<Tables> jsonReader;

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
        Tables tableNames = jsonReader.readJSON(body);
        log.info("Got request to analyze tables: {}", tableNames);
       //todo something
        return AnalysisResponseRoot.success();
    }
}
