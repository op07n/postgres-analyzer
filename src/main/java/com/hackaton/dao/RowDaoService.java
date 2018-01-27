package com.hackaton.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigInteger;

@Service
@Transactional
public class RowDaoService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private RowDao rowDao;

    @Transactional(readOnly = true)
    public Optional<TableSchema> streamRows(String tableName, TableSchema schema) {
        Query query = entityManager.createNativeQuery("SELECT * FROM " + tableName);
        List<Object[]> result = query.getResultList();

        if (result == null || result.isEmpty()) {
            return Optional.empty();
        }

        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            List<Object> values = new ArrayList<Object>();
            Object[] r = (Object[]) result.get(i);

            int columnsNum = schema.getColumns().size();
            for(int j = 0; j < columnsNum; j++) {
                if (r[j] == null) {
                    values.add(null);
                    continue;
                }
                switch (r[j].getClass().getSimpleName()) {
                    case "BigInteger":
                        BigInteger id = (BigInteger) r[j];
                        values.add(id);
                        break;
                    case "String":
                        String str = (String) r[j];
                        values.add(str);
                        break;
                    case "Timestamp":
                        Timestamp time = (Timestamp) r[j];
                        values.add(time);
                        break;
                }
            }
            Row row = new Row(values);
            rows.add(row);
        }

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new TableSchema(tableName, rows));
    }
}
