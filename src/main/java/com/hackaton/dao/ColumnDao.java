package com.hackaton.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnDao extends JpaRepository<InformationSchemaColumns, Long> {

    @Query(value = "select column_name, data_type, character_maximum_length from INFORMATION_SCHEMA.COLUMNS where table_name=:tableName", nativeQuery = true)
    List<Object[]> listColumns(@Param("tableName") String tableName);

    @Query(value = "SELECT tablename FROM pg_catalog.pg_tables where schemaname = :schemaName", nativeQuery = true)
    List<Object> listTables(@Param("schemaName") String schemaName);
}
