package com.hackaton.dao;


import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "INFORMATION_SCHEMA.COLUMNS")
public class InformationSchemaColumns {

    @Id
    @Column(name = "table_catalog")
    private String tableCatalog;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "data_type")
    private String dataType;
}
