package com.hackaton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackaton.controllers.AnalyzerController;
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
//@RunWith(SpringRunner.class)
//@TestPropertySource(locations="classpath:application.properties")
//@SpringBootTest
public class TableParserTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private AnalyzerController analyzerController;

    @Test
    public void testSerializeTables() throws Exception {
        Tables tables = new Tables(Arrays.asList("Table1", "Table2"));
        String json = MAPPER.writeValueAsString(tables);
        log.info("{}", json);
        JSONReader<Tables> jsonReader = new JSONReader<>(Tables.class);
        Tables result = jsonReader.readJSON(json);
        Assert.assertNotNull(result);
        Assert.assertEquals("Table1", result.getValues().get(0));
    }
}

