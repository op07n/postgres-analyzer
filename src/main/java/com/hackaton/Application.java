package com.hackaton;


import lombok.extern.slf4j.Slf4j;
//import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Slf4j
@ComponentScan( basePackages = { "com.hackaton" } )
@SpringBootApplication
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

    /*@Bean
    @Primary
    @Autowired
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(@Value("${spring.datasource.url}") String dbUrl,
                                 @Value("${spring.datasource.username}") String username,
                                 @Value("${spring.datasource.password}") String password,
                                 @Value("${spring.datasource.driver-class-name}") String driverClass) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        dataSourceBuilder.driverClassName(driverClass);
        DataSource dataSource = dataSourceBuilder.build();

        return dataSource;
    }*/

    public static void main( String[] args ) {
        System.out.println("******* Postgres analyzer application started *******");
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
