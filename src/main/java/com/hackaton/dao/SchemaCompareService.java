package com.hackaton.dao;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

@Service
public class SchemaCompareService {

    private RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z')
            .filteredBy(LETTERS, DIGITS).build();

    private ConcurrentHashMap<String, Integer> tableVersionMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, List<TableSchema>> gatheredSchemas = new ConcurrentHashMap<>();

    public int getAndIncCurrentTableVersion(String tableName) {//todo concurrent access
        Integer version = tableVersionMap.putIfAbsent(tableName, 0);
        if (version == null) {
            return 0;
        }
        version++;
        tableVersionMap.put(tableName, version);

        return version;
    }

    public String generateRandomID() {
        return generator.generate(5);
    }

    public boolean saveSchemas(String analysisId, List<TableSchema> schemas) {
        return gatheredSchemas.putIfAbsent(analysisId, schemas) == null;
    }

    public List<TableSchema> getSchemasForAnalysis(String anlaysisId) {
        List<TableSchema> gathered = gatheredSchemas.get(anlaysisId);
        if (gathered == null) {
            return Collections.emptyList();
        }

        return gathered;
    }
}
