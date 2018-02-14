package com.hackaton.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ColumnDaoService {

    @Autowired
    private JDBCService jdbcService;

    @Transactional(readOnly = true)
    public Optional<List<String>> streamTables(String schemaName) {
        List<String> tableNames  = this.jdbcService.getJdbcTemplate().queryForList(
                "SELECT tablename FROM pg_catalog.pg_tables where schemaname = ?",
                new Object[] {schemaName},
                String.class);

        if (tableNames.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(tableNames);
    }

    @Transactional(readOnly = true)
    public Optional<TableSchema> streamColumns(int version, String tableName) {
        List<Column> columns = this.jdbcService.getJdbcTemplate()
                .query("select column_name, data_type, character_maximum_length from INFORMATION_SCHEMA.COLUMNS where table_name = '" + tableName + "'", new RowMapper<Column>() {
                    @Override
                    public Column mapRow(ResultSet resultSet, int i) throws SQLException {
                        String columnName = resultSet.getString(1);
                        String dataType = resultSet.getString(2);
                        Column column = new Column(columnName, dataType);
                        return column;
                    }
                });

        if (columns.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new TableSchema(version, tableName, columns));
    }
}
