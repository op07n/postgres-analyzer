package com.hackaton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackaton.controllers.AnalyzerController;
import com.hackaton.data.ConnectionConfig;
import com.hackaton.data.JSONReader;
import com.hackaton.data.Tables;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@Slf4j
public class ParserTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testSerializeTables() throws Exception {
        Tables tables = new Tables(Arrays.asList("Table1", "Table2"));
        String json = MAPPER.writeValueAsString(tables);
        log.info("{}", json);
        JSONReader<Tables> jsonReader = new JSONReader<>(Tables.class);
        Tables result = jsonReader.readJSON("[\"Table1\",\"Table2\"]");
        Assert.assertNotNull(result);
        Assert.assertEquals("Table1", result.getValues().get(0));
    }

    @Test
    public void testSerializeConnectionConfig() throws Exception {
        ConnectionConfig config = new ConnectionConfig("jdbc:postgresql://localhost:5433/analyzer", "postgres", "postgres", "public");
        String json = MAPPER.writeValueAsString(config);
        log.info("{}", json);
        JSONReader<ConnectionConfig> jsonReader = new JSONReader<>(ConnectionConfig.class);
        ConnectionConfig result = jsonReader.readJSON(json);
        Assert.assertNotNull(result);
        Assert.assertEquals("jdbc:postgresql://localhost:5433/analyzer", result.getDbUrl());
    }
}

