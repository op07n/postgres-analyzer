package com.hackaton.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    public Optional<TableSchema> streamRows(String tableName) {
        Query query = entityManager.createNativeQuery("SELECT * FROM " + tableName);
        List<Object[]> result = query.getResultList();

       // List<Object[]> result = rowDao.listRows();//tableName);

        if (result == null || result.isEmpty()) {
            return Optional.empty();
        }

        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            Object[] r = (Object[]) result.get(i);
            BigInteger id = (BigInteger) r[0];

            Row row = new Row(id);
            rows.add(row);
        }

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new TableSchema(tableName, rows));
    }
}
