package com.hackaton;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan( basePackages = { "com.hackaton.controllers" } )
@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    public static void main( String[] args ) {
        System.out.println("******* Postgres analyzer application started *******");
        SpringApplication.run(Application.class, args);
    }
}
