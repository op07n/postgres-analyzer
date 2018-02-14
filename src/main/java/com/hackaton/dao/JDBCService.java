package com.hackaton.dao;

import com.hackaton.data.ConnectionConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
public class JDBCService  {

    private DataSource dataSource;

    @Getter
    private ConnectionConfig connectionConfig;

    @Getter
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void updateConnection(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(connectionConfig.getDbUrl());
        //ds.setSchema(connectionConfig.getSchemaName());
        ds.setUsername(connectionConfig.getUsername());
        ds.setPassword(connectionConfig.getPassword());

        this.dataSource = ds;
        this.jdbcTemplate.setDataSource(dataSource);
    }
}
