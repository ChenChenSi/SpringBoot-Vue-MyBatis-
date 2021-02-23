package com.chen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(value = {"com.chen.mapper"})
public class EmpVueApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpVueApplication.class, args);
    }

}
