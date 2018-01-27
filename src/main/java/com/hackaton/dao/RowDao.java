package com.hackaton.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RowDao extends JpaRepository<InformationSchemaColumns, Integer> {
    //TODO pass table name dynamicly
    @Query(value = "SELECT * FROM messages", nativeQuery = true)
    List<Object[]> listRows();//@Param("tableName") String tableName);
}