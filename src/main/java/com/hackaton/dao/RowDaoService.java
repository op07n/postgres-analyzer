package com.hackaton.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RowDaoService {

    @Autowired
    JDBCService jdbcService;

    @Transactional(readOnly = true)
    public boolean testConnection() {
        int value = jdbcService.getJdbcTemplate().queryForObject("select 1;", Integer.class);
        return value == 1;
    }

    @Transactional(readOnly = true)
    public Optional<TableSchema> streamRows(String tableName, TableSchema schema) {
        int columnsNum = schema.getColumns().size();
        List<Row> rows = jdbcService.getJdbcTemplate().query("SELECT * FROM " + tableName, new RowMapper<Row>() {

            @Override
            public Row mapRow(ResultSet resultSet, int rowId) throws SQLException {
                List<Object> values = new ArrayList<>();
                ResultSetMetaData metaData = resultSet.getMetaData();

                for (int i = 0; i < columnsNum; i++) {
                   Object value = resultSet.getObject(i);
                   String type = metaData.getColumnTypeName(i).toUpperCase();
                   switch (type) {
                       case "BIGINT": {
                           BigInteger id = (BigInteger) value;
                           values.add(id);
                           break;
                       }
                       case "VARCHAR": {
                           String str = (String) value;
                           values.add(str);
                           break;
                       }
                       case "TIMESTAMP":
                           Timestamp time = (Timestamp) value;
                           values.add(time);
                           break;
                   }
                }

                Row row = new Row(values);
                return row;
            }
        });

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new TableSchema(tableName, schema.getColumns(), rows));
    }
}
