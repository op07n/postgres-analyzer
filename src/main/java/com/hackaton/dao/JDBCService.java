package com.hackaton.dao;

import com.hackaton.data.ConnectionConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

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
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:postgresql://localhost:5433/someDB");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("postgres");
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        this.dataSource = dataSourceBuilder.build();
    }


    public void updateConnection(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.hibernate.dialect.PostgreSQLDialect");
        ds.setUrl(connectionConfig.getDbUrl());
        ds.setUsername(connectionConfig.getUsername());
        ds.setPassword(connectionConfig.getPassword());

        this.dataSource = ds;
        this.jdbcTemplate.setDataSource(dataSource);
    }
}
