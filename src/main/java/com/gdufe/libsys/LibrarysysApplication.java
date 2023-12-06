package com.gdufe.libsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gdufe.libsys.mapper")
public class LibrarysysApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibrarysysApplication.class, args);
    }

}
