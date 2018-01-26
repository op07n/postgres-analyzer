package com.hackaton.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ColumnDaoService {

    @Autowired
    private ColumnDao columnDao;

    @Transactional(readOnly = true)
    public Optional<TableSchema> streamColumns(String tableName) {
        List<Object[]> result = columnDao.listColumns(tableName);

        if (result == null || result.isEmpty()) {
            return Optional.empty();
        }

        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            Object[] r = (Object[]) result.get(i);
            String columnName = (String) r[0];
            String dataType = (String) r[1];

            Column column = new Column(columnName, dataType);
            columns.add(column);
        }

        if (columns.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new TableSchema(tableName, columns));
    }
}
