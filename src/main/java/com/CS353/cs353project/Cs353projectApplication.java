package com.CS353.cs353project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Cs353projectApplication {

    public static void main(String[] args) {
        SpringApplication.run(Cs353projectApplication.class, args);
    }

}
